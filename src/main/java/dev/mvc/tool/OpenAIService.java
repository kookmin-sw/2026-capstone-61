package dev.mvc.tool;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import dev.mvc.breed.BreedProcInter;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import dev.mvc.ai_actionlog.AiActionLogProcInter;
import dev.mvc.ai_actionlog.AiActionLogVO;
import dev.mvc.ai_config.AiConfigProcInter;
import dev.mvc.ai_config.AiConfigVO;
import dev.mvc.chat.ChatProcInter;
import dev.mvc.chat.ChatVO;
import dev.mvc.chat_memory.ChatMemoryProcInter;
import dev.mvc.chat_memory.ChatMemoryVO;
import dev.mvc.dog.DogProcInter;
import dev.mvc.dog.DogVO;
import dev.mvc.match_post.MatchPostVO;

/**
 * OpenAI 서비스
 */
@Service
public class OpenAIService {

  /** OpenAI API KEY */
  @Value("${openai.api.key}")
  private String API_KEY;

  /** OpenAI URL */
  private final String API_URL =
      "https://api.openai.com/v1/chat/completions";
  @Autowired
  @Qualifier("dev.mvc.breed.BreedProc")
  private BreedProcInter breedProc;
  @Autowired
  private AiActionLogProcInter aiActionLogProc;

  @Autowired
  private AiConfigProcInter aiConfigProc;

  @Autowired
  private ChatMemoryProcInter chatMemoryProc;

  @Autowired
  private ChatProcInter chatProc;

  @Autowired
  @Qualifier("dev.mvc.dog.DogProc")
  private DogProcInter dogProc;

  // =========================================================
  // 💬 일반 챗봇
  // =========================================================

  /**
   * 일반 챗봇 응답 생성
   */
  public String chat(
      Integer memberno,
      Integer dogno,
      String question) throws Exception {

    // =========================================================
    // 🧠 시스템 프롬프트 조회
    // =========================================================

    AiConfigVO promptVO =
        this.aiConfigProc.readByKey(
            "CHAT_SYSTEM_PROMPT");

    String systemPrompt =
        promptVO.getConfig_val();

    // =========================================================
    // 🧠 동적 Context 생성
    // =========================================================

    String context =
        this.buildDynamicContext(
            memberno,
            question);

    // =========================================================
    // 💬 최종 Prompt 생성
    // =========================================================

    String finalPrompt =
        context +
        "\n\n사용자 질문:\n" +
        question;

    // =========================================================
    // 🚀 GPT 요청
    // =========================================================
    System.out.println("========== MEMBERNO ==========");
    System.out.println(memberno);

    System.out.println("========== QUESTION ==========");
    System.out.println(question);

    System.out.println("========== CONTEXT ==========");
    System.out.println(context);
    return this.requestGPT(
        systemPrompt,
        finalPrompt,
        memberno,
        dogno,
        "CHAT");
  }

  // =========================================================
  // 🧠 동적 Context 생성
  // =========================================================

  /**
   * 질문 기반 동적 Context 생성
   */
  public String buildDynamicContext(
      Integer memberno,
      String question) {

    StringBuilder sb =
        new StringBuilder();

    String q =
        question.toLowerCase();

    // =====================================================
    // 🐶 내 반려견 정보
    // =====================================================

    if (
    	    q.contains("강아지") ||
    	    q.contains("반려견") ||
    	    q.contains("멍멍이") ||
    	    q.contains("우리 애") ||
    	    q.contains("우리집 개")
    	) {

      List<DogVO> dogList =
          this.dogProc.list_by_memberno(memberno);

      if (dogList != null &&
          dogList.size() > 0) {

        sb.append("현재 사용자의 반려견 정보\n");

        for (DogVO dog : dogList) {

          sb.append("이름: ")
            .append(dog.getName())
            .append("\n");

          sb.append("견종: ")
            .append(dog.getBreed())
            .append("\n");

          sb.append("나이: ")
            .append(dog.getAge())
            .append("\n");

          sb.append("성별: ")
            .append(dog.getGender())
            .append("\n");

          sb.append("DBTI: ")
            .append(dog.getDbti_type())
            .append("\n\n");
        }
      }
    }

    // =====================================================
    // 📊 등록된 강아지 수
    // =====================================================

    if (q.contains("품종 수") ||
        q.contains("등록된 품종 수") ||
        q.contains("총 품종")) {

      int cnt =
    	this.breedProc.count_all();

      sb.append("현재 등록된 품종 수는 ")
      .append(cnt)
      .append("개입니다.\n\n");
    }

    // =====================================================
    // 💬 최근 대화
    // =====================================================

    if (memberno != null) {

      List<ChatVO> recentList =
          this.chatProc.recentList(memberno);

      if (recentList != null &&
          recentList.size() > 0) {

        sb.append("최근 대화\n");

        for (ChatVO vo : recentList) {

          sb.append("Q: ")
            .append(vo.getQuestion())
            .append("\n");

          sb.append("A: ")
            .append(vo.getAnswer())
            .append("\n\n");
        }
      }
    }

    // =====================================================
    // 🧠 기억 정보
    // =====================================================

    if (memberno != null) {

      List<ChatMemoryVO> memoryList =
          this.chatMemoryProc
              .list_by_memberno(memberno);

      if (memoryList != null &&
          memoryList.size() > 0) {

        sb.append("사용자 기억 정보\n");

        for (ChatMemoryVO vo : memoryList) {

          sb.append("- ")
            .append(vo.getMemory_text())
            .append("\n");
        }
      }
    }

    return sb.toString();
  }

  // =========================================================
  // 🐾 AI 매칭 추천
  // =========================================================

  /**
   * AI 매칭 추천
   */
  public String matchRecommend(
      int memberno,
      int dogno,
      List<MatchPostVO> matchPosts) throws Exception {

    // =========================================================
    // 🐶 내 강아지 조회
    // =========================================================

    DogVO myDog =
        this.dogProc.read(dogno);

    // =========================================================
    // 🧠 시스템 프롬프트 조회
    // =========================================================

    AiConfigVO promptVO =
        this.aiConfigProc.readByKey(
            "MATCH_SYSTEM_PROMPT");

    String systemPrompt =
        promptVO.getConfig_val();

    // =========================================================
    // 🧠 Context 생성
    // =========================================================

    StringBuilder context =
        new StringBuilder();

    // ---------------------------------------------------------
    // 🐶 강아지 정보
    // ---------------------------------------------------------

    if (myDog != null) {

      context.append("현재 반려견 정보\n");

      context.append("이름: ")
             .append(myDog.getName())
             .append("\n");

      context.append("견종: ")
             .append(myDog.getBreed())
             .append("\n");

      context.append("나이: ")
             .append(myDog.getAge())
             .append("\n");

      context.append("DBTI: ")
             .append(myDog.getDbti_type())
             .append("\n\n");
    }

    // ---------------------------------------------------------
    // 💬 최근 대화
    // ---------------------------------------------------------

    List<ChatVO> recentList =
        this.chatProc.recentList(memberno);

    if (recentList != null &&
        recentList.size() > 0) {

      context.append("최근 대화\n");

      for (ChatVO vo : recentList) {

        context.append("Q: ")
               .append(vo.getQuestion())
               .append("\n");

        context.append("A: ")
               .append(vo.getAnswer())
               .append("\n\n");
      }
    }

    // ---------------------------------------------------------
    // 🧠 기억 정보
    // ---------------------------------------------------------

    List<ChatMemoryVO> memoryList =
        this.chatMemoryProc
            .list_by_memberno(memberno);

    if (memoryList != null &&
        memoryList.size() > 0) {

      context.append("사용자 기억 정보\n");

      for (ChatMemoryVO vo : memoryList) {

        context.append("- ")
               .append(vo.getMemory_text())
               .append("\n");
      }

      context.append("\n");
    }

    // ---------------------------------------------------------
    // 📋 추천 후보 목록
    // ---------------------------------------------------------

    context.append("추천 후보 목록\n");

    for (MatchPostVO post : matchPosts) {

      context.append("- matchno: ")
             .append(post.getMatchno())

             .append(" | 제목: ")
             .append(post.getTitle())

             .append(" | DMTI: ")
             .append(post.getDmti())

             .append(" | 매너점수: ")
             .append(post.getManner_score())

             .append("\n");
    }

    // ---------------------------------------------------------
    // 📦 JSON 형식 강제
    // ---------------------------------------------------------

    context.append("""

반드시 아래 JSON 형식으로만 응답해.

{
  "summary":"추천 요약",
  "recommendations":[
    {
      "matchno":1,
      "reason":"추천 이유"
    }
  ]
}

JSON 외 텍스트 금지.
""");

    // =========================================================
    // 🚀 GPT 요청
    // =========================================================

    return this.requestGPT(
        systemPrompt,
        context.toString(),
        memberno,
        dogno,
        "MATCH");
  }

  // =========================================================
  // 🤖 GPT 요청
  // =========================================================

  /**
   * GPT API 요청
   */
  public String requestGPT(
      String systemPrompt,
      String userPrompt,
      Integer memberno,
      Integer dogno,
      String actionType) throws Exception {

    // =========================================================
    // ⏱ 시작 시간
    // =========================================================

    long startTime =
        System.currentTimeMillis();

    // =========================================================
    // 🌐 OpenAI URL 연결
    // =========================================================

    URL url =
        new URL(API_URL);

    HttpURLConnection conn =
        (HttpURLConnection) url.openConnection();

    // =========================================================
    // 📡 HTTP 설정
    // =========================================================

    conn.setRequestMethod("POST");

    conn.setConnectTimeout(10000);

    conn.setReadTimeout(30000);

    conn.setRequestProperty(
        "Authorization",
        "Bearer " + API_KEY);

    conn.setRequestProperty(
        "Content-Type",
        "application/json");

    conn.setDoOutput(true);

    // =========================================================
    // 📦 요청 JSON 생성
    // =========================================================

    JSONObject body =
        new JSONObject();

    body.put("model", "gpt-4.1-mini");

    body.put("max_tokens", 1200);

    JSONArray messages =
        new JSONArray();

    // ---------------------------------------------------------
    // system 메시지
    // ---------------------------------------------------------

    JSONObject systemMsg =
        new JSONObject();

    systemMsg.put("role", "system");

    systemMsg.put(
        "content",
        systemPrompt);

    messages.put(systemMsg);

    // ---------------------------------------------------------
    // user 메시지
    // ---------------------------------------------------------

    JSONObject userMsg =
        new JSONObject();

    userMsg.put("role", "user");

    userMsg.put(
        "content",
        userPrompt);

    messages.put(userMsg);

    body.put("messages", messages);

    // =========================================================
    // 🚀 요청 전송
    // =========================================================

    OutputStream os =
        conn.getOutputStream();

    os.write(
        body.toString().getBytes("UTF-8"));

    os.flush();

    os.close();

    // =========================================================
    // 📥 응답 코드
    // =========================================================

    int responseCode =
        conn.getResponseCode();

    BufferedReader br;

    // 정상 응답
    if (responseCode == 200) {

      br =
          new BufferedReader(
              new InputStreamReader(
                  conn.getInputStream(),
                  "UTF-8"));

    } else {

      br =
          new BufferedReader(
              new InputStreamReader(
                  conn.getErrorStream(),
                  "UTF-8"));
    }

    // =========================================================
    // 📦 응답 문자열 저장
    // =========================================================

    String line;

    StringBuilder response =
        new StringBuilder();

    while ((line = br.readLine()) != null) {

      response.append(line);
    }

    br.close();

    // =========================================================
    // ❌ 실패 로그 저장
    // =========================================================

    if (responseCode != 200) {

      AiActionLogVO logVO =
          new AiActionLogVO();

      logVO.setMemberno(memberno);

      logVO.setDogno(dogno);

      logVO.setChatno(null);

      logVO.setActionType(actionType);

      logVO.setInputData(userPrompt);

      logVO.setOutputData(
          response.toString());

      logVO.setAiModel("gpt-4.1-mini");

      logVO.setSuccessYn("N");

      logVO.setErrorMessage(
          response.toString());

      logVO.setResponseTime(
          (int)(
              System.currentTimeMillis()
              - startTime));

      logVO.setTokenUsage(0);

      this.aiActionLogProc.create(logVO);

      System.out.println(
          "OpenAI Error: " +
          response.toString());

      throw new RuntimeException(
          "OpenAI API 호출 실패");
    }

    // =========================================================
    // 📦 응답 JSON 파싱
    // =========================================================

    JSONObject jsonResponse =
        new JSONObject(
            response.toString());

    JSONArray choices =
        jsonResponse.getJSONArray(
            "choices");

    JSONObject firstChoice =
        choices.getJSONObject(0);

    JSONObject message =
        firstChoice.getJSONObject(
            "message");

    String answer =
        message.getString("content");

    // =========================================================
    // ✅ 성공 로그 저장
    // =========================================================

    AiActionLogVO logVO =
        new AiActionLogVO();

    logVO.setMemberno(memberno);

    logVO.setDogno(dogno);

    logVO.setChatno(null);

    logVO.setActionType(actionType);

    logVO.setInputData(userPrompt);

    logVO.setOutputData(answer);

    logVO.setAiModel("gpt-4.1-mini");

    logVO.setSuccessYn("Y");

    logVO.setErrorMessage(null);

    logVO.setResponseTime(
        (int)(
            System.currentTimeMillis()
            - startTime));

    logVO.setTokenUsage(0);

    this.aiActionLogProc.create(logVO);

    // =========================================================
    // 💬 GPT 응답 반환
    // =========================================================

    return answer;
  }

}