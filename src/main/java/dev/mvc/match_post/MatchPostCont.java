package dev.mvc.match_post;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import dev.mvc.tool.OpenAIService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import dev.mvc.match_comment.MatchCommentProcInter;
import dev.mvc.match_comment.MatchCommentVO;
import dev.mvc.member.MemberProcInter;
import dev.mvc.chat.ChatProcInter;
import dev.mvc.chat.ChatVO;
import dev.mvc.dog.DogProcInter;
import dev.mvc.dog.DogVO;
import dev.mvc.dog_dbti.DogDbtiProcInter;
import dev.mvc.dog_dbti.DogDbtiVO;

@Controller
@RequestMapping("/match_post")
public class MatchPostCont {

  @Autowired
  @Qualifier("dev.mvc.match_post.MatchPostProc")
  private MatchPostProcInter matchPostProc;

  @Autowired
  @Qualifier("dev.mvc.dog.DogProc")
  private DogProcInter dogProc;
  @Autowired
  @Qualifier("dev.mvc.member.MemberProc")
  private MemberProcInter memberProc;
  @Autowired
  private ChatProcInter chatProc;
  @Autowired
  private OpenAIService openAIService;
  @Autowired
  @Qualifier("dev.mvc.dog_dbti.DogDbtiProc")
  private DogDbtiProcInter dogDbtiProc;

  @Autowired
  private MatchCommentProcInter matchCommentProc;
  
  public MatchPostCont() {
    System.out.println("-> MatchPostCont created.");
  }

  /**
   * 전체 목록 조회
   */
  @GetMapping("/list_all")
  public String list_all(
      HttpSession session,
      Model model,
      @RequestParam(value = "cat_no", defaultValue = "0") int cat_no,
      @RequestParam(value = "sort", defaultValue = "latest") String sort,
      @RequestParam(value = "manner_filter", defaultValue = "N") String manner_filter,
      @RequestParam(value = "dmti_filter", defaultValue = "") String dmti_filter,
      @RequestParam(value = "status_filter", defaultValue = "ALL") String status_filter,
      @RequestParam(value = "word", defaultValue = "") String word,
      @RequestParam(value = "my_lat", defaultValue = "0.0") double my_lat,
      @RequestParam(value = "my_lng", defaultValue = "0.0") double my_lng) {

    HashMap<String, Object> map = new HashMap<>();

    // 카테고리 번호
    map.put("cat_no", cat_no);

    // 정렬 기준
    map.put("sort", sort);

    // 매너 필터
    map.put("manner_filter", manner_filter);

    // DMTI 필터
    map.put("dmti_filter", dmti_filter);

    // 상태 필터
    map.put("status_filter", status_filter);

    // 검색어
    map.put("word", word);

    // 현재 사용자 위치
    map.put("my_lat", my_lat);
    map.put("my_lng", my_lng);

    Integer memberno =
        (Integer) session.getAttribute("memberno");

    String my_dbti = "";

    DogVO myDog = null;

    // 로그인 사용자 강아지 정보 조회
    if (memberno != null) {

      List<DogVO> dogList =
          this.dogProc.list_by_memberno(memberno);

      if (dogList != null && !dogList.isEmpty()) {

        myDog = dogList.get(0);

        my_dbti = myDog.getDbti_type();

      }

    }

    map.put("my_dbti", my_dbti);

    // 목록 조회
    ArrayList<MatchPostVO> list =
        this.matchPostProc.list_by_cat_no(map);

    model.addAttribute("list", list);
    model.addAttribute("myDog", myDog);

    // 현재 필터 상태 유지
    model.addAttribute("sort", sort);
    model.addAttribute("status_filter", status_filter);
    model.addAttribute("manner_filter", manner_filter);
    model.addAttribute("dmti_filter", dmti_filter);
    model.addAttribute("word", word);

    // 세션 저장
    session.setAttribute("my_dbti", my_dbti);

    return "match_post/list_all";
  }

  /**
   * 등록 폼
   */
  @GetMapping("/create")
  public String create(
      HttpSession session,
      Model model) {

    Integer memberno =
        (Integer) session.getAttribute("memberno");

    // 로그인 체크
    if (memberno == null) {
      return "redirect:/member/login";
    }

    model.addAttribute("cat_no", 1);

    DogVO myDog = null;

    // 내 강아지 정보 조회
    List<DogVO> dogList =
        this.dogProc.list_by_memberno(memberno);

    if (dogList != null && !dogList.isEmpty()) {
      myDog = dogList.get(0);
    }

    model.addAttribute("myDog", myDog);

    return "match_post/create";
  }

  /**
   * 게시글 등록 처리
   */
  @PostMapping("/create")
  public String create_proc(
      HttpServletRequest request,
      HttpSession session,
      MatchPostVO matchPostVO,
      RedirectAttributes ra) {

    Integer memberno =
        (Integer) session.getAttribute("memberno");

    // 로그인 체크
    if (memberno == null) {
      return "redirect:/member/login";
    }

    matchPostVO.setMemberno(memberno);
    matchPostVO.setCurrent_member(1);
    // 업로드 파일 목록
    List<MultipartFile> mfList =
        matchPostVO.getFiles();

    // 저장 파일명 목록
    List<String> fileNames =
        new ArrayList<>();

    // 업로드 폴더 경로
    String upDir =
    	    Match_post.getUploadDir();
    File dir = new File(upDir);

    // 폴더 생성
    if (!dir.exists()) {
      dir.mkdirs();
    }

    // 파일 업로드 처리
    if (mfList != null && !mfList.isEmpty()) {

      for (MultipartFile mf : mfList) {

        if (!mf.isEmpty()) {

          try {

            // 원본 파일명
            String originalName =
                mf.getOriginalFilename();

            // 확장자 추출
            String ext =
                originalName.substring(
                    originalName.lastIndexOf("."));

            // UUID 파일명 생성
            String saveName =
                UUID.randomUUID().toString() + ext;

            // 파일 저장
            mf.transferTo(
                new File(upDir + saveName));

            // 저장 파일명 추가
            fileNames.add(saveName);

          } catch (Exception e) {

            e.printStackTrace();

          }

        }

      }

    }

    // 여러 파일명을 CSV 형태로 저장
    matchPostVO.setDog_img(
        String.join(",", fileNames));

    // DB 등록
    int cnt =
        this.matchPostProc.create(matchPostVO);

    // 성공
    if (cnt == 1) {
      return "redirect:/match_post/list_all";
    }

    // 실패
    ra.addFlashAttribute("code", "create_fail");

    return "redirect:/match_post/msg";
  }

  /**
   * 내 강아지 정보 조회
   */
  @GetMapping(
      value = "/get_dog_info",
      produces = "application/json;charset=UTF-8")
  @ResponseBody
  public String get_dog_info(HttpSession session) {

    JSONObject json =
        new JSONObject();

    Integer memberno =
        (Integer) session.getAttribute("memberno");

    // 로그인 체크
    if (memberno == null) {

      json.put("error", "login_required");

      return json.toString();
    }

    List<DogVO> list =
        this.dogProc.list_by_memberno(memberno);

    // 강아지 존재
    if (list != null && !list.isEmpty()) {

      DogVO dogVO = list.get(0);

      json.put("name", dogVO.getName());
      json.put("breed", dogVO.getBreed());
      json.put("age", dogVO.getAge());
      json.put("dmti", dogVO.getDbti_type());

    } else {

      json.put("error", "no_dog_found");

    }

    return json.toString();
  }

  /**
   * 게시글 상세 조회
   */
  @GetMapping("/read")
  public String read(
      HttpSession session,
      int matchno,
      Model model) {

    MatchPostVO matchPostVO =
        this.matchPostProc.read(matchno);

    // 게시글 없음
    if (matchPostVO == null) {
      return "redirect:/match_post/list_all";
    }

    String[] images = {};

    // 이미지 분리
    if (matchPostVO.getDog_img() != null &&
        !matchPostVO.getDog_img().isEmpty()) {

      images =
          matchPostVO.getDog_img().split(",");
    }

    model.addAttribute(
        "matchPostVO",
        matchPostVO);

    model.addAttribute(
        "images",
        images);

    /* =========================================================
       댓글 목록 조회
    ========================================================= */

    ArrayList<MatchCommentVO> commentList =
        this.matchCommentProc
            .list_by_matchno(matchno);

    model.addAttribute(
        "commentList",
        commentList);

    Integer memberno =
        (Integer) session.getAttribute("memberno");

    // 내 강아지 목록 조회
    if (memberno != null) {
        List<DogVO> dogList = this.dogProc.list_by_memberno(memberno);
        model.addAttribute("dogList", dogList);
        
        // 📢 [핵심 추가] 내 매너 점수(온도)를 조회해서 Model에 저장
        dev.mvc.member.MemberVO memberVO = this.memberProc.read(memberno);
        if (memberVO != null) {
          // HTML의 th:data-my-score="${manner_score}"에 매핑됩니다.
          model.addAttribute("manner_score", memberVO.getManner_temp());
        } else {
          model.addAttribute("manner_score", 0.0);
        }
      } else {
        // 로그인 안 한 비회원일 경우 기본값 설정
        model.addAttribute("manner_score", 0.0);
      }

      return "match_post/read";
    }

  /**
   * 수정 폼
   */
  @GetMapping("/update")
  public String update(
      HttpSession session,
      int matchno,
      Model model) {

    Integer memberno =
        (Integer) session.getAttribute("memberno");

    MatchPostVO matchPostVO =
        this.matchPostProc.read(matchno);

    // 권한 체크
    if (memberno == null ||
        matchPostVO == null ||
        memberno != matchPostVO.getMemberno()) {

      return "redirect:/member/login";
    }

    model.addAttribute("matchPostVO", matchPostVO);

    return "match_post/update";
  }

  /**
   * 수정 처리
   */
  @PostMapping(
      value = "/update_proc",
      produces = "application/json;charset=UTF-8")
  @ResponseBody
  public String update_proc(
      HttpSession session,
      MatchPostVO matchPostVO) {

    JSONObject json =
        new JSONObject();

    Integer memberno =
        (Integer) session.getAttribute("memberno");

    MatchPostVO currentVO =
        this.matchPostProc.read(
            matchPostVO.getMatchno());

    // 권한 체크
    if (memberno == null ||
        currentVO == null ||
        memberno != currentVO.getMemberno()) {

      json.put("res", "login_required");

      return json.toString();
    }

    int cnt =
        this.matchPostProc.update(matchPostVO);

    json.put(
        "res",
        cnt == 1 ? "success" : "fail");

    return json.toString();
  }

  /**
   * 게시글 삭제
   */
  @GetMapping("/delete")
  public String delete(
      HttpSession session,
      int matchno,
      RedirectAttributes ra) {

    Integer memberno =
        (Integer) session.getAttribute("memberno");

    MatchPostVO matchPostVO =
        this.matchPostProc.read(matchno);

    // 권한 체크
    if (memberno == null ||
        matchPostVO == null ||
        memberno != matchPostVO.getMemberno()) {

      return "redirect:/member/login";
    }

    int cnt =
        this.matchPostProc.delete(matchno);

    // 삭제 성공
    if (cnt == 1) {
      return "redirect:/match_post/list_all";
    }

    // 삭제 실패
    ra.addFlashAttribute("code", "delete_fail");

    return "redirect:/match_post/msg";
  }

  /**
   * AI 추천
   */
  @PostMapping(
      value = "/recommend_ai",
      produces = "application/json;charset=UTF-8")
  @ResponseBody
  public String recommendAi(HttpSession session) {

    // =========================================================
    // 🌈 JSON 객체 생성
    // =========================================================

    JSONObject json =
        new JSONObject();

    // =========================================================
    // 👤 로그인 회원 번호 조회
    // =========================================================

    Integer memberno =
        (Integer) session.getAttribute("memberno");

    // =========================================================
    // ❌ 로그인 안됨
    // =========================================================

    if (memberno == null) {

      json.put("error", "login_required");

      return json.toString();
    }

    // =========================================================
    // 🐶 내 강아지 조회
    // =========================================================

    List<DogVO> dogs =
        this.dogProc.list_by_memberno(memberno);

    // =========================================================
    // ❌ 강아지 없음
    // =========================================================

    if (dogs == null || dogs.isEmpty()) {

      json.put("error", "no_dog");

      return json.toString();
    }

    // =========================================================
    // 🐾 대표 강아지
    // =========================================================

    DogVO myDog =
        dogs.get(0);

    // =========================================================
    // 📋 매칭 게시글 조회 조건
    // =========================================================

    HashMap<String, Object> map =
        new HashMap<>();



    // 검색어
    map.put("word", "");

    // 모집중 상태만
    map.put("status_filter", 1);

    // 최대 개수 제한
    //map.put("limit_num", 5);

    // =========================================================
    // 📋 매칭 게시글 조회
    // =========================================================

    ArrayList<MatchPostVO> matchPosts =
        this.matchPostProc.list_by_cat_no(map);

    // =========================================================
    // ❌ 게시글 없음
    // =========================================================

    if (matchPosts == null ||
        matchPosts.isEmpty()) {

      json.put("res", "no_posts");

      json.put(
          "aiAnswer",
          "현재 추천 가능한 산책 친구가 없어요🐾");

      return json.toString();
    }

    try {

      // =======================================================
      // 🤖 OpenAIService 호출
      // =======================================================

      String aiResult =
          this.openAIService.matchRecommend(
              memberno,
              myDog.getDogno(),
              matchPosts);

      // =======================================================
      // 📦 GPT JSON 파싱
      // =======================================================

      JSONObject aiJson =
          new JSONObject(aiResult);

      // =======================================================
      // 💬 AI 요약
      // =======================================================

      String summary =
          aiJson.getString("summary");

      json.put("res", "success");

      json.put("aiAnswer", summary);

      json.put("myDogName", myDog.getName());

      // =======================================================
      // 🐾 추천 배열
      // =======================================================

      JSONArray recommendArray =
          aiJson.getJSONArray("recommendations");

      JSONArray recommendedPosts =
          new JSONArray();

      // =======================================================
      // 🔄 추천 반복
      // =======================================================

      for (int i = 0;
           i < recommendArray.length();
           i++) {

        JSONObject item =
            recommendArray.getJSONObject(i);

        // 추천 게시글 번호
        int matchno =
            item.getInt("matchno");

        // 추천 이유
        String reason =
            item.getString("reason");

        // =====================================================
        // 📋 게시글 상세 조회
        // =====================================================

        MatchPostVO postVO =
            this.matchPostProc.read(matchno);

        // 게시글 없음 방어
        if (postVO == null) {
          continue;
        }

        // =====================================================
        // 🖼 대표 이미지
        // =====================================================

        String firstImg =
            "default_dog.jpg";

        if (postVO.getDog_img() != null &&
            !postVO.getDog_img().isEmpty()) {

          firstImg =
              postVO.getDog_img()
                    .split(",")[0];
        }

        // =====================================================
        // 📦 JSON 객체 생성
        // =====================================================

        JSONObject postJson =
            new JSONObject();

        postJson.put(
            "matchno",
            postVO.getMatchno());

        postJson.put(
            "title",
            postVO.getTitle());

        postJson.put(
            "dmti",
            postVO.getDmti());

        postJson.put(
            "manner_score",
            postVO.getManner_score());

        postJson.put(
            "dog_img",
            firstImg);

        postJson.put(
            "reason",
            reason);

        // =====================================================
        // 📦 배열 추가
        // =====================================================

        recommendedPosts.put(postJson);
      }

      // =======================================================
      // 📦 추천 리스트 반환
      // =======================================================

      json.put(
          "recommendedPosts",
          recommendedPosts);

    } catch (Exception e) {

      e.printStackTrace();

      json.put("res", "timeout_error");

      json.put(
          "aiAnswer",
          "AI 추천 분석 중 오류가 발생했어요🥺");
    }

    return json.toString();
  }

  /**
   * 메시지 페이지
   */
  @GetMapping("/msg")
  public String msg() {
    return "match_post/msg";
  }

}