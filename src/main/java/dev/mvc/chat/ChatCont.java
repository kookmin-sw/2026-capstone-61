package dev.mvc.chat;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import dev.mvc.ai_actionlog.AiActionLogProcInter;
import dev.mvc.ai_actionlog.AiActionLogVO;
import dev.mvc.ai_config.AiConfigProcInter;
import dev.mvc.ai_config.AiConfigVO;
import dev.mvc.chat_memory.ChatMemoryProcInter;
import dev.mvc.chat_memory.ChatMemoryVO;
import dev.mvc.dog.DogProcInter;
import dev.mvc.dog.DogVO;
/**
 * AI 통합 컨트롤러
 */
@RestController
@RequestMapping("/chat")
public class ChatCont {

  @Autowired
  private ChatProcInter chatProc;

  @Autowired
  private ChatMemoryProcInter chatMemoryProc;

  @Autowired
  private AiConfigProcInter aiConfigProc;

  @Autowired
  private AiActionLogProcInter aiActionLogProc;
  @Autowired
  @Qualifier("dev.mvc.dog.DogProc")
  private DogProcInter dogProc;
  // =========================================================
  // 💬 CHAT
  // =========================================================

  /**
   * 채팅 등록
   * @param chatVO
   * @return
   */
  @PostMapping("/create")
  public int create(@RequestBody ChatVO chatVO) {
    return this.chatProc.create(chatVO);
  }

  /**
   * 채팅 상세 조회
   * @param chatno
   * @return
   */
  @GetMapping("/read/{chatno}")
  public ChatVO read(@PathVariable("chatno") int chatno) {
    return this.chatProc.read(chatno);
  }

  /**
   * 최근 대화 조회
   * @param memberno
   * @return
   */
  @GetMapping("/recent/{memberno}")
  public List<ChatVO> recentList(
      @PathVariable("memberno") int memberno) {

    return this.chatProc.recentList(memberno);
  }

  /**
   * 질문 유형별 조회
   * @param question_type
   * @return
   */
  @GetMapping("/type/{question_type}")
  public List<ChatVO> list_by_type(
      @PathVariable("question_type") String question_type) {

    return this.chatProc.list_by_type(question_type);
  }

  /**
   * 전체 채팅 목록
   * @return
   */
  @GetMapping("/list_all")
  public List<ChatVO> list_all() {
    return this.chatProc.list_all();
  }

  /**
   * 채팅 수정
   * @param chatVO
   * @return
   */
  @PutMapping("/update")
  public int update(@RequestBody ChatVO chatVO) {
    return this.chatProc.update(chatVO);
  }

  /**
   * 채팅 삭제
   * @param chatno
   * @return
   */
  @DeleteMapping("/delete/{chatno}")
  public int delete(@PathVariable("chatno") int chatno) {
    return this.chatProc.delete(chatno);
  }

  // =========================================================
  // 🧠 MEMORY
  // =========================================================

  /**
   * 기억 저장
   * @param chatMemoryVO
   * @return
   */
  @PostMapping("/memory/create")
  public int memory_create(
      @RequestBody ChatMemoryVO chatMemoryVO) {

    return this.chatMemoryProc.create(chatMemoryVO);
  }

  /**
   * 기억 상세 조회
   * @param memoryno
   * @return
   */
  @GetMapping("/memory/read/{memoryno}")
  public ChatMemoryVO memory_read(
      @PathVariable("memoryno") int memoryno) {

    return this.chatMemoryProc.read(memoryno);
  }

  /**
   * 회원 기억 조회
   * @param memberno
   * @return
   */
  @GetMapping("/memory/member/{memberno}")
  public List<ChatMemoryVO> memory_list_by_memberno(
      @PathVariable("memberno") int memberno) {

    return this.chatMemoryProc.list_by_memberno(memberno);
  }

  /**
   * 강아지 기억 조회
   * @param dogno
   * @return
   */
  @GetMapping("/memory/dog/{dogno}")
  public List<ChatMemoryVO> memory_list_by_dogno(
      @PathVariable("dogno") int dogno) {

    return this.chatMemoryProc.list_by_dogno(dogno);
  }

  /**
   * 기억 수정
   * @param chatMemoryVO
   * @return
   */
  @PutMapping("/memory/update")
  public int memory_update(
      @RequestBody ChatMemoryVO chatMemoryVO) {

    return this.chatMemoryProc.update(chatMemoryVO);
  }

  /**
   * 기억 삭제
   * @param memoryno
   * @return
   */
  @DeleteMapping("/memory/delete/{memoryno}")
  public int memory_delete(
      @PathVariable("memoryno") int memoryno) {

    return this.chatMemoryProc.delete(memoryno);
  }

  // =========================================================
  // 🧠 AI CONFIG
  // =========================================================

  /**
   * Prompt 등록
   * @param aiConfigVO
   * @return
   */
  @PostMapping("/config/create")
  public int config_create(
      @RequestBody AiConfigVO aiConfigVO) {

    return this.aiConfigProc.create(aiConfigVO);
  }

  /**
   * KEY 기준 조회
   * @param config_key
   * @return
   */
  @GetMapping("/config/key/{config_key}")
  public AiConfigVO config_readByKey(
      @PathVariable("config_key") String config_key) {

    return this.aiConfigProc.readByKey(config_key);
  }

  /**
   * 전체 Prompt 목록
   * @return
   */
  @GetMapping("/config/list_all")
  public List<AiConfigVO> config_list_all() {
    return this.aiConfigProc.list_all();
  }

  /**
   * Prompt 수정
   * @param aiConfigVO
   * @return
   */
  @PutMapping("/config/update")
  public int config_update(
      @RequestBody AiConfigVO aiConfigVO) {

    return this.aiConfigProc.update(aiConfigVO);
  }

  /**
   * Prompt 삭제
   * @param configno
   * @return
   */
  @DeleteMapping("/config/delete/{configno}")
  public int config_delete(
      @PathVariable("configno") int configno) {

    return this.aiConfigProc.delete(configno);
  }

  // =========================================================
  // 📜 AI ACTION LOG
  // =========================================================

  /**
   * 로그 상세 조회
   * @param logno
   * @return
   */
  @GetMapping("/log/read/{logno}")
  public AiActionLogVO log_read(
      @PathVariable("logno") int logno) {

    return this.aiActionLogProc.read(logno);
  }

  /**
   * 전체 로그 조회
   * @return
   */
  @GetMapping("/log/list_all")
  public List<AiActionLogVO> log_list_all() {
    return this.aiActionLogProc.list_all();
  }

  /**
   * 회원별 로그 조회
   * @param memberno
   * @return
   */
  @GetMapping("/log/member/{memberno}")
  public List<AiActionLogVO> log_list_by_memberno(
      @PathVariable("memberno") int memberno) {

    return this.aiActionLogProc.list_by_memberno(memberno);
  }

  /**
   * 로그 삭제
   * @param logno
   * @return
   */
  @DeleteMapping("/log/delete/{logno}")
  public int log_delete(
      @PathVariable("logno") int logno) {

    return this.aiActionLogProc.delete(logno);
  }
//=========================================================
//🤖 AI 질문
//=========================================================

@Autowired
@Qualifier("openAIService")
private dev.mvc.tool.OpenAIService openAIService;

/**
* AI 질문 처리
*/
@PostMapping("/ask")
public java.util.Map<String, Object> ask(
   @RequestBody java.util.Map<String, String> body,
   jakarta.servlet.http.HttpSession session) {

 java.util.Map<String, Object> response =
     new java.util.HashMap<>();

 try {

   // -----------------------------------------------------
   // 로그인 체크
   // -----------------------------------------------------

   Integer memberno =
       (Integer)session.getAttribute("memberno");

   if (memberno == null) {

     response.put(
         "status",
         "LOGIN_REQUIRED");

     response.put(
         "answer",
         "로그인이 필요한 기능이에요🐾");

     return response;
   }

   // -----------------------------------------------------
   // 질문
   // -----------------------------------------------------

   String question =
       body.get("question");

   // -----------------------------------------------------
   // 대표 강아지 조회
   // -----------------------------------------------------

   Integer dogno = null;

   try {

	   List<DogVO> dogList =
			    this.dogProc.list_by_memberno(memberno);

     if (dogList != null &&
         dogList.size() > 0) {

       dogno =
           dogList.get(0).getDogno();
     }

   } catch(Exception e) {

     e.printStackTrace();
   }

   // -----------------------------------------------------
   // GPT 호출
   // -----------------------------------------------------

   String answer =
       this.openAIService.chat(
           memberno,
           dogno,
           question);

   // -----------------------------------------------------
   // 채팅 저장
   // -----------------------------------------------------

   ChatVO chatVO =
       new ChatVO();

   chatVO.setMemberno(memberno);

   if (dogno != null) {
     chatVO.setDogno(dogno);
   }

   chatVO.setQuestion(question);

   chatVO.setAnswer(answer);

   chatVO.setQuestion_type("CHAT");

   chatVO.setAi_model("gpt-4.1-mini");

   this.chatProc.create(chatVO);

   // -----------------------------------------------------
   // 응답
   // -----------------------------------------------------

   response.put(
       "status",
       "SUCCESS");

   response.put(
       "answer",
       answer);

 } catch(Exception e) {

   e.printStackTrace();

   response.put(
       "status",
       "ERROR");

   response.put(
       "answer",
       "AI 응답 중 오류가 발생했어요🥺");
 }

 return response;
}

}