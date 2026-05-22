package dev.mvc.breed_image;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import dev.mvc.breed.Breed;
import dev.mvc.breed.BreedProcInter;
import dev.mvc.breed.BreedVO;
import dev.mvc.category.CategoryProcInter;
import dev.mvc.category.CategoryVO;
import dev.mvc.member.MemberProcInter;
import dev.mvc.tool.OpenAIService;
import dev.mvc.tool.Upload;

@Controller
@RequestMapping(value = "/breed")
public class Breed_imageCont {

  // =========================================================
  // OpenAI 서비스
  // =========================================================

  @Autowired
  @Qualifier("openAIService")
  private OpenAIService openAIService;

  @Autowired
  @Qualifier("dev.mvc.member.MemberProc")
  private MemberProcInter memberProc;

  @Autowired
  @Qualifier("dev.mvc.breed.BreedProc")
  private BreedProcInter breedProc;

  @Autowired
  @Qualifier("dev.mvc.breed_image.Breed_imageProc")
  private Breed_imageProcInter breed_imageProc;

  @Autowired
  @Qualifier("dev.mvc.category.CategoryProc")
  private CategoryProcInter categoryProc;

  public Breed_imageCont() {
    System.out.println("-> Breed_imageCont created.");
  }

  /**
   * 1. 등록 화면 (GET 방식)
   */
  @GetMapping(value = "/create")
  public String create_view(HttpSession session, Model model, 
                            @RequestParam(name="cat_no", defaultValue="0") int cat_no) {

    if (this.memberProc.isMemberAdmin(session)) {
      CategoryVO categoryVO = this.categoryProc.read(cat_no);
      model.addAttribute("categoryVO", categoryVO);
      model.addAttribute("breedVO", new BreedVO());
      return "breed/create"; 
    } else {
      return "redirect:/member/login_cookie_need";
    }
  }

  /**
   * AI 자동 작성을 위한 데이터 생성 (에러 방지 강화 버전)
   */
  @GetMapping(value = "/ai_draft", produces = "application/json;charset=UTF-8")
  @ResponseBody
  public Map<String, Object> ai_draft(@RequestParam(name="breed_name") String breed_name) {
      Map<String, Object> response = new HashMap<>();
      
      try {
          // AI에게 상세한 스토리텔링과 규격 데이터를 동시에 요구
          String prompt = breed_name + " 품종에 대해 전문가가 작성한 아주 상세한 백과사전 데이터를 생성해줘.\n\n" +
                          "**[필수 포함 내용 - 매우 길게 작성할 것]**\n" +
                          "1. [역사와 기원]: 이 개의 조상, 탄생 배경, 과거의 역할(사냥/목축 등) 상세 기술\n" +
                          "2. [흥미로운 스토리]: 전설, 영화 출연, 유명한 실화 등 비하인드 스토리\n" +
                          "3. [세부 종류]: 품종 내 세부 라인(영국/미국/캐나다 등)의 외형 및 성격 차이\n" +
                          "4. [양육 및 건강]: 활동량, 털빠짐, 유전병(관절/심장), 훈련 팁, 추천 환경\n\n" +
                          "**[답변 형식 - 아래 키워드로 구분해서 써줘]**\n" +
                          "제목: (성격을 반영한 제목)\n" +
                          "원산지: (국가)\n" +
                          "몸무게: (숫자)\n" +
                          "체고: (숫자)\n" +
                          "크기: (소형견/중형견/대형견 중 하나)\n" +
                          "상세내용: (위의 1~4번 내용을 항목별로 줄바꿈해서 아주 길게 작성)";
          
          String aiResponse =
        		    this.openAIService.requestGPT(
        		        "너는 반려견 품종 전문가다.",
        		        prompt,
        		        null,
        		        null,
        		        "BREED_AI");
          
          // AI가 준 긴 텍스트에서 데이터 쪼개기
          String title = "정보", origin = "-", kg = "-", height = "-", size = "중형견", content = aiResponse;
          
          try {
              String[] lines = aiResponse.split("\n");
              for (String line : lines) {
                  if (line.startsWith("제목:")) title = line.replace("제목:", "").trim();
                  else if (line.startsWith("원산지:")) origin = line.replace("원산지:", "").trim();
                  else if (line.startsWith("몸무게:")) kg = line.replace("몸무게:", "").trim();
                  else if (line.startsWith("체고:")) height = line.replace("체고:", "").trim();
                  else if (line.startsWith("크기:")) size = line.replace("크기:", "").trim();
              }
              // 상세내용 부분만 따로 추출 (상세내용: 이후의 모든 글)
              if (aiResponse.contains("상세내용:")) {
                  content = aiResponse.substring(aiResponse.indexOf("상세내용:") + 5).trim();
              }
          } catch (Exception e) { }

          // Map에 담으면 줄바꿈이 많아도 절대 안 깨짐!
          response.put("title", title);
          response.put("origin", origin);
          response.put("kg", kg);
          response.put("height", height);
          response.put("size_type", size);
          response.put("content", content);

      } catch (Exception e) {
          response.put("content", "AI 응답 중 오류가 발생했습니다.");
      }
      
      return response;
  }
  /**
   * 전체 이미지 갤러리 (검색 기능 포함)
   * http://localhost:9091/breed/gallery
   */
  @GetMapping(value = "/gallery")
  public String gallery(Model model, 
                        @RequestParam(name = "word", defaultValue = "") String word) {
      
      // 검색어가 있으면 해당 검색어로, 없으면 전체 조회
      ArrayList<Breed_imageVO> list = this.breed_imageProc.list_all(word);
      
      model.addAttribute("list", list);
      model.addAttribute("word", word); // 검색창에 입력한 단어 유지
      
      return "breed/gallery"; 
  }
  /**
   * 3. 등록 처리 (POST 방식)
   * 테이블 분리 구조: BREED(텍스트) 저장 후 BREED_IMAGE(사진) 저장
   */
  @PostMapping(value = "/create")
  public String create_process(HttpSession session, 
                               BreedVO breedVO, 
                               @RequestParam("file1MF") List<MultipartFile> file1MF, // name 속성 일치
                               RedirectAttributes ra) {

    if (this.memberProc.isMemberAdmin(session)) {
      int memberno = (int) session.getAttribute("memberno");
      breedVO.setMemberno(memberno);

      // [STEP 1] BREED 테이블에 텍스트 정보 저장
      int cnt = this.breedProc.create(breedVO); 

      if (cnt == 1) {
        int breedno = breedVO.getBreedno(); 

        // [STEP 2] 다중 파일 업로드 처리 (BREED_IMAGE 테이블)
        if (file1MF != null && !file1MF.isEmpty()) {
          String upDir = Breed.getUploadDir();

          File dir = new File(upDir);
          if (!dir.exists()) dir.mkdirs();

          for (MultipartFile file : file1MF) {
            if (file != null && file.getSize() > 0) { 
              String original_name = file.getOriginalFilename();
              long f_size = file.getSize();
              
              // 실제 폴더에 파일 저장
              String saved_name = Upload.saveFileSpring_RND(file, upDir, "DOG"); 
              
              // [STEP 3] 이미지 전용 테이블 저장
              Breed_imageVO imageVO = new Breed_imageVO();
              imageVO.setBreedno(breedno);
              imageVO.setOriginal_name(original_name);
              imageVO.setSaved_name(saved_name);
              imageVO.setFile_path("/storage/"); 
              imageVO.setThumb_path("-"); // ORA-01400 에러 방지용 필수값
              imageVO.setF_size(f_size);
              
              this.breed_imageProc.create(imageVO);
            }
          }
        }
        
        ra.addAttribute("cat_no", breedVO.getCat_no());
        return "redirect:/breed/list_by_cat_no";
        
      } else {
        ra.addFlashAttribute("code", "create_fail");
        return "redirect:/breed/msg";
      }
    } else {
      return "redirect:/member/login_cookie_need";
    }
  }
}