package dev.mvc.member;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import dev.mvc.tool.Security;
import jakarta.servlet.http.HttpSession;

@Component("dev.mvc.member.MemberProc")
public class MemberProc implements MemberProcInter {

  @Autowired
  private MemberDAOInter memberDAO;

  @Autowired
  Security security;

  /**
   * 생성자
   */
  public MemberProc() {
    System.out.println("-> MemberProc created.");
  }

  /* =========================================================
   * 중복 검사
   * ========================================================= */

  /**
   * 아이디 중복 검사
   */
  @Override
  public int checkID(String id) {
    int cnt = this.memberDAO.checkID(id);
    return cnt;
  }

  /**
   * 닉네임 중복 검사
   */
  @Override
  public int checkNickname(String nickname) {
    int cnt = this.memberDAO.checkNickname(nickname);
    return cnt;
  }

  /* =========================================================
   * 회원 CRUD
   * ========================================================= */

  /**
   * 회원 가입
   */
  @Override
  public int create(MemberVO memberVO) {

    // 비밀번호 암호화는 컨트롤러에서 처리
    // String passwd_encoded = this.security.aesEncode(memberVO.getPasswd());
    // memberVO.setPasswd(passwd_encoded);

    int cnt = this.memberDAO.create(memberVO);

    return cnt;
  }

  /**
   * 회원 전체 목록
   */
  @Override
  public ArrayList<MemberVO> list() {
    ArrayList<MemberVO> list = this.memberDAO.list();

    return list;
  }

  /**
   * 회원 번호로 조회
   */
  @Override
  public MemberVO read(int memberno) {
    MemberVO memberVO = this.memberDAO.read(memberno);

    return memberVO;
  }

  /**
   * 아이디로 회원 조회
   */
  @Override
  public MemberVO readById(String id) {
    MemberVO memberVO = this.memberDAO.readById(id);

    return memberVO;
  }

  /**
   * 회원 정보 수정
   */
  @Override
  public int update(MemberVO memberVO) {

    // 비밀번호 암호화는 컨트롤러에서 처리
    // String passwd_encoded = this.security.aesEncode(memberVO.getPasswd());
    // memberVO.setPasswd(passwd_encoded);

    int cnt = this.memberDAO.update(memberVO);

    return cnt;
  }

  /**
   * 프로필 파일 수정
   */
  @Override
  public int update_profile(MemberVO memberVO) {
    int cnt = this.memberDAO.update_profile(memberVO);

    return cnt;
  }

  /**
   * 회원 등급 수정
   */
  @Override
  public int grade_update(MemberVO memberVO) {
    int cnt = this.memberDAO.grade_update(memberVO);

    return cnt;
  }

  /**
   * 회원 삭제
   */
  @Override
  public int delete(int memberno) {
    int cnt = this.memberDAO.delete(memberno);

    return cnt;
  }

  /* =========================================================
   * 로그인 및 세션 처리
   * ========================================================= */

  /**
   * 로그인 처리
   */
  @Override
  public int login(HashMap<String, Object> map) {
    int cnt = this.memberDAO.login(map);

    return cnt;
  }

  /**
   * 회원 로그인 여부 확인
   */
  @Override
  public boolean isMember(HttpSession session) {

    boolean sw = false;

    String grade = (String) session.getAttribute("grade");

    if (grade != null) {

      // grade
      // admin  : 관리자
      // member : 일반 회원
      // black  : 정지 회원
      // exit   : 탈퇴 회원

      if (grade.equals("member")
          || grade.equals("black")
          || grade.equals("exit")) {

        sw = true;
      }
    }

    return sw;
  }
  /**
   * 🌡️ 매너온도 변경
   */
  @Override
  public int updateMannerTemp(HashMap<String, Object> map) {

      int cnt =
          this.memberDAO.updateMannerTemp(map);

      return cnt;
  }
  /**
   * 관리자 여부 확인
   */
  @Override
  public boolean isMemberAdmin(HttpSession session) {

    boolean sw = false;

    String grade = (String) session.getAttribute("grade");

    if (grade != null) {

      if (grade.equals("admin")) {
        sw = true;
      }
    }

    return sw;
  }

  /* =========================================================
   * 아이디 / 비밀번호 찾기
   * ========================================================= */

  /**
   * 아이디 찾기
   */
  @Override
  public ArrayList<MemberVO> find_id(MemberVO memberVO) {

    ArrayList<MemberVO> list = this.memberDAO.find_id(memberVO);

    return list;
  }

  /**
   * 비밀번호 찾기
   */
  @Override
  public MemberVO find_passwd(MemberVO memberVO) {

    MemberVO passwd = this.memberDAO.find_passwd(memberVO);

    return passwd;
  }

  /**
   * 비밀번호 변경
   */
  @Override
  public int update_passwd(MemberVO memberVO) {

    // 비밀번호 암호화
    String passwd_encoded =
        this.security.aesEncode(
            memberVO.getPasswd());

    memberVO.setPasswd(passwd_encoded);

    int cnt =
        this.memberDAO.update_passwd(memberVO);

    return cnt;
  }
  /* =========================================================
   * 검색 및 페이징
   * ========================================================= */

  /**
   * 검색 목록
   */
  @Override
  public ArrayList<MemberVO> list_by_search(HashMap<String, Object> hashMap) {

    ArrayList<MemberVO> list = this.memberDAO.list_by_search(hashMap);

    return list;
  }

  /**
   * 검색 레코드 수
   */
  @Override
  public int list_by_search_count(HashMap<String, Object> hashMap) {

    int cnt = this.memberDAO.list_by_search_count(hashMap);

    return cnt;
  }

  /**
   * 검색 + 페이징
   */
  @Override
  public ArrayList<MemberVO> list_by_search_paging(HashMap<String, Object> map) {

    // 시작 rownum 계산
    int begin_of_page =
        ((int) map.get("now_page") - 1) * Member.RECORD_PER_PAGE;

    // 시작 번호
    int start_num = begin_of_page + 1;

    // 종료 번호
    int end_num = begin_of_page + Member.RECORD_PER_PAGE;

    map.put("start_num", start_num);
    map.put("end_num", end_num);

    ArrayList<MemberVO> list =
        this.memberDAO.list_by_search_paging(map);

    return list;
  }

  /**
   * 등급별 검색 레코드 수
   */
  @Override
  public int grade_list_by_search_count(HashMap<String, Object> hasMap) {

    int cnt = this.memberDAO.grade_list_by_search_count(hasMap);

    return cnt;
  }

  /**
   * 등급별 검색 + 페이징
   */
  @Override
  public ArrayList<MemberVO> grade_list_by_search_paging(HashMap<String, Object> map) {

    // 시작 rownum 계산
    int begin_of_page =
        ((int) map.get("now_page") - 1) * Member.RECORD_PER_PAGE;

    // 시작 번호
    int start_num = begin_of_page + 1;

    // 종료 번호
    int end_num = begin_of_page + Member.RECORD_PER_PAGE;

    map.put("start_num", start_num);
    map.put("end_num", end_num);

    ArrayList<MemberVO> list =
        this.memberDAO.grade_list_by_search_paging(map);

    return list;
  }

  /* =========================================================
   * 페이징 박스
   * ========================================================= */

  /**
   * 페이징 HTML 생성
   */
  @Override
  public String pagingBox(
      int now_page,
      String word,
      String list_file,
      int search_count,
      int record_per_page,
      int page_per_block) {

    // 검색어 null 방지
    if (word == null) {
      word = "";
    }

    // 전체 페이지 수
    int total_page =
        (int) Math.ceil((double) search_count / record_per_page);

    // 전체 그룹 수
    int total_grp =
        (int) Math.ceil((double) total_page / page_per_block);

    // 현재 그룹 번호
    int now_grp =
        (int) Math.ceil((double) now_page / page_per_block);

    // 시작 페이지
    int start_page =
        ((now_grp - 1) * page_per_block) + 1;

    // 종료 페이지
    int end_page =
        now_grp * page_per_block;

    StringBuffer str = new StringBuffer();

    // CSS
    str.append("<style type='text/css'>");
    str.append("#paging {text-align:center; margin-top:5px; font-size:1em;}");
    str.append("#paging A:link {text-decoration:none; color:black;}");
    str.append("#paging A:hover {text-decoration:none; color:black;}");
    str.append("#paging A:visited {text-decoration:none; color:black;}");
    str.append(".span_box_1 {");
    str.append("border:1px solid #cccccc;");
    str.append("padding:1px 6px;");
    str.append("margin:1px 2px;");
    str.append("}");
    str.append(".span_box_2 {");
    str.append("background-color:#668db4;");
    str.append("color:#FFFFFF;");
    str.append("border:1px solid #cccccc;");
    str.append("padding:1px 6px;");
    str.append("margin:1px 2px;");
    str.append("}");
    str.append("</style>");

    str.append("<DIV id='paging'>");

    // 이전 버튼
    int _now_page = (now_grp - 1) * page_per_block;

    if (now_grp >= 2) {

      str.append("<span class='span_box_1'>");
      str.append("<A href='");
      str.append(list_file);
      str.append("?word=");
      str.append(word);
      str.append("&now_page=");
      str.append(_now_page);
      str.append("'>이전</A></span>");
    }

    // 페이지 번호 출력
    for (int i = start_page; i <= end_page; i++) {

      if (i > total_page) {
        break;
      }

      // 현재 페이지 강조
      if (now_page == i) {

        str.append("<span class='span_box_2'>");
        str.append(i);
        str.append("</span>");

      } else {

        str.append("<span class='span_box_1'>");
        str.append("<A href='");
        str.append(list_file);
        str.append("?word=");
        str.append(word);
        str.append("&now_page=");
        str.append(i);
        str.append("'>");
        str.append(i);
        str.append("</A></span>");
      }
    }

    // 다음 버튼
    _now_page = (now_grp * page_per_block) + 1;

    if (now_grp < total_grp) {

      str.append("<span class='span_box_1'>");
      str.append("<A href='");
      str.append(list_file);
      str.append("?word=");
      str.append(word);
      str.append("&now_page=");
      str.append(_now_page);
      str.append("'>다음</A></span>");
    }

    str.append("</DIV>");

    return str.toString();
  }

  /* =========================================================
   * 매너 온도 및 매칭 기능
   * ========================================================= */

  /**
   * 매너 온도 증가
   */
  @Override
  public int increase_manner_temp(int memberno) {

    int cnt = this.memberDAO.increase_manner_temp(memberno);

    return cnt;
  }

  /**
   * 매너 온도 감소
   */
  @Override
  public int decrease_manner_temp(int memberno) {

    int cnt = this.memberDAO.decrease_manner_temp(memberno);

    return cnt;
  }

  /**
   * 매칭 성공 횟수 증가
   */
  @Override
  public int increase_match_cnt(int memberno) {

    int cnt = this.memberDAO.increase_match_cnt(memberno);

    return cnt;
  }

  /**
   * 사용자 위치 정보 수정
   */
  @Override
  public int update_location(MemberVO memberVO) {

    int cnt = this.memberDAO.update_location(memberVO);

    return cnt;
  }
  
  /**
   * 주변 사용자 목록 조회
   */
  @Override
  public ArrayList<MemberVO> list_near_member(HashMap<String, Object> map) {

    ArrayList<MemberVO> list =
        this.memberDAO.list_near_member(map);

    return list;
  }

}