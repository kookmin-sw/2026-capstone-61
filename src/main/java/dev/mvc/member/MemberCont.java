package dev.mvc.member;

import java.util.ArrayList;
import java.util.HashMap;
import dev.mvc.match_review.MatchReviewProcInter;
import org.json.JSONObject;
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

import dev.mvc.category.CategoryProcInter;
import dev.mvc.category.CategoryVOMenu;
import dev.mvc.mlogin.MloginProcInter;
import dev.mvc.mlogin.MloginVO;
import dev.mvc.tool.Security;
import dev.mvc.tool.Tool;
import dev.mvc.tool.Upload;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@RequestMapping("/member")
@Controller
public class MemberCont {
	@Autowired
	@Qualifier("dev.mvc.match_review.MatchReviewProc")
	private MatchReviewProcInter matchReviewProc;
  @Autowired
  @Qualifier("dev.mvc.member.MemberProc")
  private MemberProcInter memberProc;

  @Autowired
  @Qualifier("dev.mvc.category.CategoryProc")
  private CategoryProcInter categoryProc;

  @Autowired
  @Qualifier("dev.mvc.mlogin.MloginProc")
  private MloginProcInter mloginProc;

  @Autowired
  private Security security;

  public MemberCont() {
    System.out.println("-> MemberCont created.");
  }

  /**
   * 아이디 중복 검사
   */
  @GetMapping(value = "/checkID")
  @ResponseBody
  public String checkID(String id) {
    int cnt = this.memberProc.checkID(id);
    JSONObject obj = new JSONObject();
    obj.put("cnt", cnt);
    return obj.toString();
  }

  /**
   * 닉네임 중복 검사
   */
  @GetMapping(value = "/checkNickname")
  @ResponseBody
  public String checkNickname(String nickname) {
    int cnt = this.memberProc.checkNickname(nickname);
    JSONObject obj = new JSONObject();
    obj.put("cnt", cnt);
    return obj.toString();
  }

  /**
   * 아이디 찾기 폼
   */
  @GetMapping(value = "/find_id_form")
  public String find_id_form(Model model) {
    return "member/find_id";
  }

  /**
   * 아이디 찾기 처리
   */
  @PostMapping(value = "/find_id")
  public String find_id(Model model, MemberVO memberVO) {
    ArrayList<MemberVO> memberList = this.memberProc.find_id(memberVO);
    if (memberList != null && !memberList.isEmpty()) {
      model.addAttribute("code", "find_id_success");
      model.addAttribute("memberList", memberList);
    } else {
      model.addAttribute("code", "find_id_fail");
      model.addAttribute("cnt", 0);
    }
    model.addAttribute("log", 1);
    return "member/msg";
  }
  
  /**
   * 비밀번호 찾기 폼
   */
  @GetMapping(value = "/find_passwd_form")
  public String find_passwd_form(Model model) {

    return "member/find_passwd";
  }
  /**
   * 비밀번호 찾기 처리
   */
  @PostMapping(value = "/find_passwd")
  public String find_passwd(
      Model model,
      MemberVO memberVO) {

    MemberVO member =
        this.memberProc.find_passwd(memberVO);

    // 회원 없음
    if (member == null) {

      model.addAttribute(
          "code",
          "find_passwd_fail");

      model.addAttribute(
          "cnt",
          0);

      return "member/msg";
    }

    // 성공
    model.addAttribute(
        "code",
        "find_passwd_success");

    model.addAttribute(
        "cnt",
        1);

    model.addAttribute(
        "memberno",
        member.getMemberno());

    return "member/msg";
  }
  /**
   * 비밀번호 재설정 폼
   */
  @GetMapping(value = "/reset_passwd_form")
  public String reset_passwd_form(
      int memberno,
      Model model) {

    model.addAttribute(
        "memberno",
        memberno);

    return "member/reset_passwd";
  }
  /**
   * 비밀번호 재설정 처리
   */
  @PostMapping(value = "/reset_passwd")
  public String reset_passwd(
      MemberVO memberVO,
      Model model) {

    int cnt =
        this.memberProc.update_passwd(memberVO);

    if (cnt == 1) {

      model.addAttribute(
          "code",
          "passwd_change_success");

      model.addAttribute(
          "cnt",
          1);

    } else {

      model.addAttribute(
          "code",
          "passwd_change_fail");

      model.addAttribute(
          "cnt",
          0);
    }

    return "member/msg";
  }
  /**
   * 회원 가입 폼
   */
  @GetMapping(value = "/create")
  public String create_form(Model model, MemberVO memberVO) {
    return "member/create";
  }

  /**
   * 회원 가입 처리
   */
  @PostMapping(value = "/create")
  public String create_proc(
      Model model,
      MemberVO memberVO) {

    int checkID_cnt =
        this.memberProc.checkID(
            memberVO.getId());
    if (checkID_cnt > 0) {
      model.addAttribute(
          "code",
          "duplicate_fail");
      model.addAttribute("cnt", 0);
      return "member/msg";
    }

    int checkNickname_cnt =
        this.memberProc.checkNickname(
            memberVO.getNickname());
    if (checkNickname_cnt > 0) {
      model.addAttribute(
          "code",
          "duplicate_nickname_fail");
      model.addAttribute("cnt", 0);
      return "member/msg";
    }

    String passwd_encoded =
        this.security.aesEncode(
            memberVO.getPasswd());
    memberVO.setPasswd(passwd_encoded);

    // 기본 회원 등급
    memberVO.setGrade(1);
    int cnt =
        this.memberProc.create(memberVO);
    if (cnt == 1) {
      model.addAttribute(
          "code",
          "create_success");
      model.addAttribute(
          "mname",
          memberVO.getMname());
      model.addAttribute(
          "id",
          memberVO.getId());
    } else {
      model.addAttribute(
          "code",
          "create_fail");
    }
    model.addAttribute("cnt", cnt);
    return "member/msg";
  }

  /**
   * 프로필 상세 조회
   */
  @GetMapping(value = "/profile")
  public String profile(
      Model model,
      HttpSession session,
      int memberno) {

    if (!this.memberProc.isMember(session)
        && !this.memberProc.isMemberAdmin(session)) {

      return "redirect:/member/login";
    }

    MemberVO memberVO =
        this.memberProc.read(memberno);

    // 회원 없음
    if (memberVO == null) {

      return "redirect:/member/list";
    }

    model.addAttribute("memberVO", memberVO);

    return "member/profile";
  }
  /**
   * 회원정보 조회
   */
  @GetMapping(value = "/read")
  public String read(
      @RequestParam(value = "memberno", required = false)
      Integer memberno,
      Model model) {

    if (memberno == null) {
      return "redirect:/member/list";
    }

    MemberVO memberVO =
        this.memberProc.read(memberno);

    if (memberVO == null) {
      return "redirect:/member/list";
    }
 // =====================================================
 // ⭐ 평균 보호자 평점
 // =====================================================

 Double avg_owner_score =
     this.matchReviewProc.avg_owner_score(memberno);

 // =====================================================
 // 🐶 평균 강아지 평점
 // =====================================================

 Double avg_dog_score =
     this.matchReviewProc.avg_dog_score(memberno);

 // null 방지
 if (avg_owner_score == null) {
   avg_owner_score = 0.0;
 }

 if (avg_dog_score == null) {
   avg_dog_score = 0.0;
 }

 model.addAttribute("memberVO", memberVO);

 model.addAttribute(
     "avg_owner_score",
     String.format("%.1f", avg_owner_score));

 model.addAttribute(
     "avg_dog_score",
     String.format("%.1f", avg_dog_score));

 return "member/read";
  }

  /**
   * 관리자 전용 회원 목록 (검색 + 페이징)
   */
  @GetMapping(value = "/list")
  public String list(HttpSession session, Model model,
                     @RequestParam(name = "word", defaultValue = "") String word,
                     @RequestParam(name = "now_page", defaultValue = "1") int now_page) {
	  // 관리자만 접근 가능
	if (!this.memberProc.isMemberAdmin(session)) {
	   return "redirect:/member/login";
	}

    word = Tool.checkNull(word).trim();
    HashMap<String, Object> map = new HashMap<>();
    ArrayList<MemberVO> list;
    int search_count;

    if (word.contains("__")) { 
      String[] parts = word.split("__");
      String gradeWord = parts[1];
      map.put("word", gradeWord);
      map.put("now_page", now_page);
      list = this.memberProc.grade_list_by_search_paging(map);
      search_count = this.memberProc.grade_list_by_search_count(map);
    } else { 
      map.put("word", word);
      map.put("now_page", now_page);
      list = this.memberProc.list_by_search_paging(map);
      search_count = this.memberProc.list_by_search_count(map);
    }

    String paging = this.memberProc.pagingBox(now_page, word, "/member/list", 
                                              search_count, Member.RECORD_PER_PAGE, Member.PAGE_PER_BLOCK);

    model.addAttribute("list", list);
    model.addAttribute("word", word);
    model.addAttribute("paging", paging);
    model.addAttribute("now_page", now_page);
    model.addAttribute("search_count", search_count);
    model.addAttribute("no", search_count - ((now_page - 1) * Member.RECORD_PER_PAGE));

    return "member/list";
  }

  /**
   * 로그인 폼
   * [보안 패치] 비밀번호 쿠키 노출 로직 제거
   */
  @GetMapping(value = "/login")
  public String login_form(Model model, HttpServletRequest request) {
    Cookie[] cookies = request.getCookies();
    String ck_id = "";
    String ck_id_save = "N"; 

    if (cookies != null) {
      for (Cookie cookie : cookies) {
        if (cookie.getName().equals("ck_id")) ck_id = cookie.getValue();
        else if (cookie.getName().equals("ck_id_save")) ck_id_save = cookie.getValue();
      }
    }
    
    model.addAttribute("ck_id", ck_id);
    model.addAttribute("ck_id_save", ck_id_save);

    return "member/login";
  }

  /**
   * 로그인 처리
   * [보안 패치] 비밀번호 쿠키 저장 파라미터 및 로직 제거
   */
  @PostMapping(value = "/login")
  public String login_proc(
      HttpSession session,
      HttpServletRequest request,
      HttpServletResponse response,
      Model model,
      MloginVO mloginVO,
      String id,
      String passwd,
      @RequestParam(value = "id_save", defaultValue = "N") String id_save) {

    // 접속 IP
    String ip = request.getRemoteAddr();

    // 로그인 정보 저장용 Map
    HashMap<String, Object> map = new HashMap<>();
    map.put("id", id);
    map.put("passwd", this.security.aesEncode(passwd));
    System.out.println(
    	    "로그인 암호화: " +
    	    this.security.aesEncode(passwd)
    	);
    // 로그인 처리
    int cnt = this.memberProc.login(map);

    // 로그인 성공
    if (cnt == 1) {
      // 회원 정보 조회
      MemberVO memberVO = this.memberProc.readById(id);

      /* =========================================================
       * 세션 저장
       * ========================================================= */
      session.setAttribute("memberno", memberVO.getMemberno());
      session.setAttribute("id", memberVO.getId());
      session.setAttribute("mname", memberVO.getMname());
      session.setAttribute("nickname", memberVO.getNickname());

      session.setAttribute("tel", memberVO.getTel());
      session.setAttribute("zipcode", memberVO.getZipcode());
      session.setAttribute("address1", memberVO.getAddress1());
      session.setAttribute("address2", memberVO.getAddress2());

      // 프로필 이미지
      session.setAttribute("profile", memberVO.getProfile());
      session.setAttribute("thumbs", memberVO.getThumbs());

      // 매너 온도
      session.setAttribute("manner_temp", memberVO.getManner_temp());

      // 매칭 성공 횟수
      session.setAttribute("match_cnt", memberVO.getMatch_cnt());
      
      // 자기소개
      session.setAttribute("intro", memberVO.getIntro());
      
      // 위치 정보
      session.setAttribute("latitude", memberVO.getLatitude());
      session.setAttribute("longitude", memberVO.getLongitude());
      session.setAttribute("location_update", memberVO.getLocation_update());
      
      // 회원 권한
      String gradeStr = switch (memberVO.getGrade()) {
        case 0 -> "admin";
        case 2 -> "black";
        case 3 -> "exit";
        default -> "member";
      };
      session.setAttribute("grade", gradeStr);

      /* =========================================================
       * 아이디 저장 쿠키
       * ========================================================= */
      Cookie ck_id = new Cookie("ck_id", id);
      if (id_save.equals("Y")) {
        ck_id.setMaxAge(60 * 60 * 24 * 30); // 30일 저장
      } else {
        ck_id.setMaxAge(0); // 쿠키 삭제
      }
      ck_id.setPath("/");
      response.addCookie(ck_id);

      /* =========================================================
       * 아이디 저장 여부 쿠키
       * ========================================================= */
      Cookie ck_id_save = new Cookie("ck_id_save", id_save);
      ck_id_save.setMaxAge(60 * 60 * 24 * 30);
      ck_id_save.setPath("/");
      response.addCookie(ck_id_save);

      /* =========================================================
       * 로그인 기록 저장
       * ========================================================= */
      mloginVO.setMemberno(memberVO.getMemberno());
      mloginVO.setIp(ip);
      this.mloginProc.mlogin_insert(mloginVO);

      return "redirect:/";

    } else {
      model.addAttribute("code", "login_fail");
      model.addAttribute("cnt", 0);
      return "member/msg";
    }
  }

  /**
   * 로그아웃
   */
  @GetMapping(value = "/logout")
  public String logout(HttpSession session) {
    session.invalidate();
    return "redirect:/";
  }

  /**
   * 회원 정보 수정 폼
   */
  @GetMapping(value = "/update_form")
  public String update_form(
      HttpSession session,
      Model model,
      @RequestParam(
          value = "memberno",
          required = false)
      Integer memberno) {

    int targetNo = 0;
    if (this.memberProc.isMemberAdmin(session)) {

      // 관리자일 경우 memberno 필수
      if (memberno == null) {

        return "redirect:/member/list";
      }

      targetNo = memberno;

    } else if (session.getAttribute("memberno") != null) {

      // 본인 번호 사용
      targetNo =
          (int) session.getAttribute("memberno");

    } else {

      return "redirect:/member/login";
    }

    MemberVO memberVO =
        this.memberProc.read(targetNo);

    // 회원이 존재하지 않는 경우
    if (memberVO == null) {

      return "redirect:/member/list";
    }
    model.addAttribute("memberVO", memberVO);
    return "member/update_form";
  }
  /* =========================================================
   * 회원 정보 수정 처리
   * ========================================================= */
  @PostMapping(value = "/update_proc")
  public String update_proc(
      HttpSession session,
      Model model,
      MemberVO memberVO) {

    Integer loginMemberno = (Integer) session.getAttribute("memberno");
    String grade = (String) session.getAttribute("grade");

    // 1. 로그인 체크
    if (loginMemberno == null) {
      return "redirect:/member/login";
    }

    // 2. 권한 체크 (본인도 아니고 관리자도 아니면 차단)
    if (!Integer.valueOf(memberVO.getMemberno()).equals(loginMemberno) 
        && !"admin".equals(grade)) {
      return "redirect:/member/login";
    }

    // 3. 기존 회원 정보 조회
    MemberVO originVO = this.memberProc.read(memberVO.getMemberno());
    if (originVO == null) {
      return "redirect:/member/list";
    }

    // 4. 비밀번호 검증 (관리자가 아닐 때만 실행)
    // 일반 회원은 반드시 본인의 현재 비밀번호(old_passwd)를 입력해야 함
    if (!"admin".equals(grade)) {
      String encryptedOld = this.security.aesEncode(memberVO.getOld_passwd());
      if (!originVO.getPasswd().equals(encryptedOld)) {
        model.addAttribute("code", "passwd_fail");
        model.addAttribute("cnt", 0);
        return "member/msg";
      }
    }

    // 5. 새 비밀번호 처리
    // 새 비밀번호 창이 비어있지 않으면 암호화해서 저장, 비어있으면 기존 비번 유지
    if (memberVO.getPasswd() != null && !memberVO.getPasswd().trim().isEmpty()) {
      memberVO.setPasswd(this.security.aesEncode(memberVO.getPasswd()));
    } else {
      memberVO.setPasswd(originVO.getPasswd()); // 기존 비밀번호 유지
    }

    // 6. DB 업데이트
    int cnt = this.memberProc.update(memberVO);
    
    if (cnt == 1) {
      // 본인이 본인 정보를 수정한 경우에만 세션 갱신
      if (Integer.valueOf(memberVO.getMemberno()).equals(loginMemberno)) {
        session.setAttribute("mname", memberVO.getMname());
        session.setAttribute("nickname", memberVO.getNickname());
        session.setAttribute("tel", memberVO.getTel());
        session.setAttribute("intro", memberVO.getIntro());
        session.setAttribute("zipcode", Tool.checkNull(memberVO.getZipcode()));
        session.setAttribute("address1", Tool.checkNull(memberVO.getAddress1()));
        session.setAttribute("address2", Tool.checkNull(memberVO.getAddress2()));
      }
      
      model.addAttribute("code", "update_success");
      model.addAttribute("mname", memberVO.getMname());
      model.addAttribute("id", memberVO.getId());
    } else {
      model.addAttribute("code", "update_fail");
    }

    model.addAttribute("cnt", cnt);
    return "member/msg";
  }
  /* =========================================================
   * 위치 정보 저장
   * ========================================================= */
  @PostMapping(value = "/update_location")
  @ResponseBody
  public String update_location(
      HttpSession session,
      @RequestParam("latitude") double latitude,
      @RequestParam("longitude") double longitude) {

    JSONObject obj = new JSONObject();
    try {
      // 로그인 회원 번호 조회
    	Integer memberno =
    		    (Integer) session.getAttribute("memberno");
    		/* 로그인 체크 */
    		if (memberno == null) {
    		  obj.put("cnt", 0);
    		  return obj.toString();
    		}
    		/* 좌표 검증 */
    		if (latitude < -90 || latitude > 90
    		    || longitude < -180 || longitude > 180) {
    		  obj.put("cnt", 0);
    		  return obj.toString();
    		}
      MemberVO memberVO = new MemberVO();
      memberVO.setMemberno(memberno);
      memberVO.setLatitude(latitude);
      memberVO.setLongitude(longitude);

      int cnt =
          this.memberProc.update_location(memberVO);

      MemberVO updatedVO =
          this.memberProc.read(memberno);

      session.setAttribute(
          "latitude",
          updatedVO.getLatitude());

      session.setAttribute(
          "longitude",
          updatedVO.getLongitude());

      session.setAttribute(
          "location_update",
          updatedVO.getLocation_update());

      obj.put("cnt", cnt);

    } catch (Exception e) {

    	  System.out.println(
    	      "위치 저장 실패: " + e.getMessage());

    	  obj.put("cnt", 0);
    }

    return obj.toString();
  }

  /* =========================================================
   * 매너 온도 증가
   * ========================================================= */
  @PostMapping(value = "/increase_manner_temp")
  @ResponseBody
  public String increase_manner_temp(
      HttpSession session,
      int memberno) {

    JSONObject obj = new JSONObject();

    Integer loginMemberno =
        (Integer) session.getAttribute("memberno");

    // 로그인 체크
    if (loginMemberno == null) {

      obj.put("cnt", 0);

      return obj.toString();
    }
    // 자기 자신 추천 방지
    if (loginMemberno.equals(memberno)) {
      obj.put("cnt", 0);
      return obj.toString();
    }
    int cnt =
        this.memberProc
            .increase_manner_temp(memberno);
    obj.put("cnt", cnt);
    return obj.toString();
  }

  /* =========================================================
   * 매너 온도 감소
   * ========================================================= */
  @PostMapping(value = "/decrease_manner_temp")
  @ResponseBody
  public String decrease_manner_temp(
      HttpSession session,
      int memberno) {

    JSONObject obj = new JSONObject();
    Integer loginMemberno =
        (Integer) session.getAttribute("memberno");
    // 로그인 체크
    if (loginMemberno == null) {
      obj.put("cnt", 0);
      return obj.toString();
    }
    // 자기 자신 비추천 방지
    if (loginMemberno.equals(memberno)) {
      obj.put("cnt", 0);
      return obj.toString();
    }
    int cnt =
        this.memberProc
            .decrease_manner_temp(memberno);
    obj.put("cnt", cnt);
    return obj.toString();
  }
  /* =========================================================
   * 매칭 성공 횟수 증가
   * ========================================================= */
  @PostMapping(value = "/increase_match_cnt")
  @ResponseBody
  public String increase_match_cnt(
      HttpSession session,
      int memberno) {

    JSONObject obj = new JSONObject();
    Integer loginMemberno =
        (Integer) session.getAttribute("memberno");

    // 로그인 체크
    if (loginMemberno == null) {
      obj.put("cnt", 0);
      return obj.toString();
    }
    // 자기 자신 증가 방지
    if (loginMemberno.equals(memberno)) {
      obj.put("cnt", 0);
      return obj.toString();
    }
    int cnt =
        this.memberProc
            .increase_match_cnt(memberno);
    obj.put("cnt", cnt);
    return obj.toString();
  }

  /**
   * 프로필 수정 폼
   */
  @GetMapping(value = "/update_profile_form")
  public String update_profile_form(
      HttpSession session,
      Model model,
      Integer memberno) {

    // 로그인 회원 번호
    Integer loginMemberno =
        (Integer) session.getAttribute("memberno");

    // 권한
    String grade =
        (String) session.getAttribute("grade");

    // 로그인 안 된 경우
    if (loginMemberno == null) {
      return "redirect:/member/login";
    }

    // 본인 또는 관리자만 접근 가능
    if (memberno == null) {
    	  return "redirect:/member/list";
    	}

    	// 본인 또는 관리자만 접근 가능
    	if (!memberno.equals(loginMemberno)
    	    && !"admin".equals(grade)) {

    	  return "redirect:/member/login";
    	}

    MemberVO memberVO =
        memberProc.read(memberno);

    if (memberVO == null) {
      return "redirect:/member/list";
    }

    ArrayList<CategoryVOMenu> menu =
        this.categoryProc.menu();

    model.addAttribute("menu", menu);
    model.addAttribute("memberVO", memberVO);

    return "member/update_profile";
  }

  /**
   * 프로필 수정 처리 (파일 업로드)
   */
  @PostMapping(value = "/update_profile")
  public String update_profile_proc(
      HttpSession session,
      RedirectAttributes ra,
      @RequestParam("memberno") Integer memberno,
      @RequestParam(required = false) String word,
      @RequestParam(defaultValue = "1") int now_page,
      @RequestParam("files1MF") MultipartFile files1MF) {

    Integer loginMemberno =
        (Integer) session.getAttribute("memberno");

    String grade =
        (String) session.getAttribute("grade");

    // 로그인 안 된 경우
    if (loginMemberno == null) {

      return "redirect:/member/login";
    }

    // 본인 또는 관리자만 허용
    if (!memberno.equals(loginMemberno)
        && !"admin".equals(grade)) {

      return "redirect:/member/login";
    }

    if (files1MF.isEmpty()) {

      ra.addFlashAttribute("code", "file_empty_fail");

      return "redirect:/member/read?memberno=" + memberno;
    }

    MemberVO memberVO_old =
    	    memberProc.read(memberno);

    	if (memberVO_old == null) {

    	  return "redirect:/member/list";
    	}

    	String upDir =
    	    Member.getUploadDir();


    if (memberVO_old.getProfilesaved() != null
        && !memberVO_old.getProfilesaved().isEmpty()) {

      Tool.deleteFile(
          upDir,
          memberVO_old.getProfilesaved());

      Tool.deleteFile(
          upDir,
          memberVO_old.getThumbs());
    }


    String profile =
        files1MF.getOriginalFilename();

    long sizes =
        files1MF.getSize();

    String profilesaved = "";
    String thumbs = "";

    if (sizes > 0) {

      profilesaved =
          Upload.saveFileSpring(
              files1MF,
              upDir);

      // 이미지인 경우 썸네일 생성
      if (Tool.isImage(profilesaved)) {

        thumbs =
            Tool.preview(
                upDir,
                profilesaved,
                250,
                200);
      }
    }

    MemberVO memberVO =
        new MemberVO();

    memberVO.setMemberno(memberno);

    memberVO.setProfile(profile);
    memberVO.setProfilesaved(profilesaved);
    memberVO.setThumbs(thumbs);
    memberVO.setSizes(sizes);

    this.memberProc.update_profile(memberVO);

    session.setAttribute("profile", profile);
    session.setAttribute("thumbs", thumbs);

    ra.addAttribute("memberno", memberno);

    ra.addAttribute("word", word);

    ra.addAttribute("now_page", now_page);

    return "redirect:/member/read";
  }

  /**
   * 관리자 전용 회원 등급 수정 폼
   */
  @GetMapping(value = "/admin_read_form")
  public String admin_read_form(
      HttpSession session,
      Model model,
      int memberno) {

    // 관리자만 접근 가능
    if (!this.memberProc.isMemberAdmin(session)) {
      return "redirect:/member/login";
    }

    MemberVO memberVO =
        this.memberProc.read(memberno);

    if (memberVO == null) {
      return "redirect:/member/list";
    }

    model.addAttribute("memberVO", memberVO);

    return "member/admin_read";
  }

  /**
   * 관리자 전용 회원 등급 수정 처리
   */
  @PostMapping(value = "/admin_update")
  public String admin_update(
      HttpSession session,
      MemberVO memberVO,
      RedirectAttributes ra) {

    // 관리자만 접근 가능
    if (!this.memberProc.isMemberAdmin(session)) {
      return "redirect:/member/login";
    }

    this.memberProc.grade_update(memberVO);

    ra.addAttribute(
        "memberno",
        memberVO.getMemberno());

    return "redirect:/member/admin_read_form";
  }

  /**
   * 회원 탈퇴 폼
   */
  @GetMapping(value = "/delete_form")
  public String delete_form(
      HttpSession session,
      Model model,
      int memberno) {

    // 로그인 회원 번호
    Integer loginMemberno =
        (Integer) session.getAttribute("memberno");

    // 권한
    String grade =
        (String) session.getAttribute("grade");

    // 로그인 안 된 경우
    if (loginMemberno == null) {
      return "redirect:/member/login";
    }

    // 본인 또는 관리자만 접근 가능
    if (!Integer.valueOf(memberno).equals(loginMemberno)
        && !"admin".equals(grade)) {

      return "redirect:/member/login";
    }

    MemberVO memberVO =
        this.memberProc.read(memberno);

    if (memberVO == null) {
      return "redirect:/member/list";
    }

    model.addAttribute("memberVO", memberVO);

    return "member/delete";
  }

  /**
   * 회원 탈퇴 처리
   */
  @PostMapping(value = "/delete_proc")
  public String delete_proc(
      Model model,
      Integer memberno,
      HttpSession session) {

    // 로그인 회원 번호
    Integer loginMemberno =
        (Integer) session.getAttribute("memberno");

    // 권한
    String grade =
        (String) session.getAttribute("grade");

    // 로그인 안 된 경우
    if (loginMemberno == null) {
      return "redirect:/member/login";
    }

    // 본인 또는 관리자만 허용
    if (!memberno.equals(loginMemberno)
        && !"admin".equals(grade)) {

      return "redirect:/member/login";
    }

    // 회원 조회
    MemberVO memberVO =
        this.memberProc.read(memberno);

    // 파일 삭제
    if (memberVO != null
        && memberVO.getProfilesaved() != null
        && !memberVO.getProfilesaved().isEmpty()) {

      String upDir =
          Member.getUploadDir();

      Tool.deleteFile(
          upDir,
          memberVO.getProfilesaved());

      Tool.deleteFile(
          upDir,
          memberVO.getThumbs());
    }

    // 회원 삭제
    int cnt =
        this.memberProc.delete(memberno);

    if (cnt == 1) {

      // 본인 탈퇴 시 로그아웃
      if (memberno.equals(loginMemberno)) {
        session.invalidate();
      }

      return "redirect:/";

    } else {

      model.addAttribute("code", "delete_fail");

      return "member/msg";
    }
  }
}