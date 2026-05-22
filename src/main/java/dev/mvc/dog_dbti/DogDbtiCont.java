package dev.mvc.dog_dbti;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value; 
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

// REST 통신 및 JSON 파싱용 Import
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import dev.mvc.dog.DogProcInter; 
import dev.mvc.dog.DogVO;

@Controller
@RequestMapping(value = "/dog_dbti")
public class DogDbtiCont {

    @Autowired
    @Qualifier("dev.mvc.dog_dbti.DogDbtiProc")
    private DogDbtiProcInter dogDbtiProc;

    @Autowired
    @Qualifier("dev.mvc.dog.DogProc") 
    private DogProcInter dogProc;

    // application.properties에서 API 키를 읽어옵니다.
    @Value("${openai.api.key}")
    private String apiKey;

    public DogDbtiCont() {
        System.out.println("-> DogDbtiCont Created");
    }

    /**
     * DBTI 설문지 및 강아지 선택 통합 화면 (GET)
     */
    @GetMapping(value = "/create.do")
    public ModelAndView create_form(HttpSession session) {
        ModelAndView mav = new ModelAndView();

        Integer memberno = (Integer) session.getAttribute("memberno");
        if (memberno == null) {
            mav.addObject("msg", "로그인 후 이용 가능한 서비스입니다.");
            mav.addObject("url", "/member/login"); // redirect가 아닌 일반 URL 전달용
            mav.setViewName("dog_dbti/msg"); // 앞의 / 제거
            return mav;
        }

        List<DogVO> dogList = this.dogProc.list_by_memberno(memberno);
        mav.addObject("dogList", dogList); 
        mav.setViewName("dog_dbti/create"); // 앞의 / 제거
        
        return mav;
    }

    /**
     * DBTI 테스트 결과 저장 처리 (POST)
     */
    @PostMapping(value = "/create.do")
    public ModelAndView create_proc(DogDbtiVO dogDbtiVO) {
        ModelAndView mav = new ModelAndView();
        
        int cnt = this.dogDbtiProc.create_result(dogDbtiVO);
        
        if (cnt == 1) {
            // 주소 이동(redirect)은 맨 앞에 /가 반드시 있어야 함
            mav.setViewName("redirect:/dog_dbti/read.do?dogno=" + dogDbtiVO.getDogno());
        } else {
            mav.addObject("code", "create_fail");
            mav.addObject("cnt", cnt);
            mav.setViewName("dog_dbti/msg"); // 앞의 / 제거
        }
        return mav;
    }
    
    /**
     * DBTI 결과 상세 조회 및 AI 분석 (GET)
     */
    @GetMapping(value = "/read.do")
    public ModelAndView read(@RequestParam(name="dogno") int dogno) {
        ModelAndView mav = new ModelAndView();
        
        // 1. DBTI 결과 조회
        DogDbtiVO dogDbtiVO = this.dogDbtiProc.read(dogno);
        
        if (dogDbtiVO == null) {
            mav.addObject("msg", "검사 결과가 존재하지 않습니다.");
            mav.addObject("url", "/dog_dbti/create.do");
            mav.setViewName("dog_dbti/msg"); // 앞의 / 제거
            return mav;
        }

        // 2. 강아지 기본 정보 조회
        DogVO dogVO = this.dogProc.read(dogno);
        
        // 3. 실제 OpenAI API 호출하여 맞춤형 멘트 생성
        String chatGptFeedback = callOpenAI(dogVO, dogDbtiVO);
        
        mav.addObject("dogDbtiVO", dogDbtiVO);
        mav.addObject("dogVO", dogVO);                   
        mav.addObject("chatGptFeedback", chatGptFeedback); 
        mav.setViewName("dog_dbti/read"); // 앞의 / 제거
        
        return mav;
    }

    /**
     * 특정 검사 기록 삭제
     */
    @PostMapping(value = "/delete.do")
    public String delete(int dbtino, int dogno) {
        this.dogDbtiProc.delete(dbtino);
        // redirect는 URL이므로 앞에 / 유지
        return "redirect:/dog_dbti/read.do?dogno=" + dogno;
    }

    /**
     * 🤖 실제 OpenAI ChatGPT API 통신 메서드
     */
    private String callOpenAI(DogVO dog, DogDbtiVO dbti) {
        String url = "https://api.openai.com/v1/chat/completions";

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(apiKey); 
        headers.set("Content-Type", "application/json");

        // 1. AI 프롬프트 작성
        String prompt = String.format(
            "너는 전문 반려견 행동 훈련사야. 내 강아지 이름은 '%s', 견종은 '%s', 나이는 %d살이야. " +
            "이번에 성향 검사를 했는데 '%s' 타입이 나왔어. 상세 수치는 외향성 %d%%, 사교성 %d%%, 애정표현 %d%%, 복종/계획성 %d%%야. " +
            "이 정보를 바탕으로 우리 강아지만의 성격 특징과 케어 방법을 따뜻한 말투로 3~4문장 조언해줘. " +
            "중요 키워드는 <strong> 태그로 감싸고, 문단 구분은 <br> 태그를 써줘.",
            dog.getName(), dog.getBreed(), dog.getAge(), dbti.getDbtiType(),
            dbti.getEPer(), dbti.getSPer(), dbti.getAPer(), dbti.getOPer()
        );

        // 2. JSON 바디 조립
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", "gpt-3.5-turbo"); 
        
        List<Map<String, String>> messages = new ArrayList<>();
        Map<String, String> userMessage = new HashMap<>();
        userMessage.put("role", "user");
        userMessage.put("content", prompt);
        messages.add(userMessage);
        
        requestBody.put("messages", messages);
        requestBody.put("temperature", 0.7);

        try {
            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(response.getBody());
            return rootNode.path("choices").get(0).path("message").path("content").asText();

        } catch (Exception e) {
            e.printStackTrace();
            return "AI 분석 서버와 연결에 실패했습니다. 나중에 다시 시도해 주세요. 🥲";
        }
    }
}