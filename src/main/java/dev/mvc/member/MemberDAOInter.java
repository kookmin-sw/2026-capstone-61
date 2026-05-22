package dev.mvc.member;

import java.util.ArrayList;
import java.util.HashMap;

public interface MemberDAOInter {

  /* =========================================================
   * 중복 검사
   * ========================================================= */

  /**
   * 아이디 중복 검사
   * @param id 아이디
   * @return 중복 아이디 갯수
   */
  public int checkID(String id);

  /**
   * 닉네임 중복 검사
   * @param nickname 닉네임
   * @return 중복 닉네임 갯수
   */
  public int checkNickname(String nickname);


  /* =========================================================
   * 회원 CRUD
   * ========================================================= */

  /**
   * 회원 가입
   * @param memberVO 회원 정보
   * @return 추가한 레코드 갯수
   */
  public int create(MemberVO memberVO);

  /**
   * 회원 전체 목록
   * @return 회원 목록
   */
  public ArrayList<MemberVO> list();

  /**
   * 회원 번호로 조회
   * @param memberno 회원 번호
   * @return 회원 정보
   */
  public MemberVO read(int memberno);

  /**
   * 아이디로 회원 조회
   * @param id 아이디
   * @return 회원 정보
   */
  public MemberVO readById(String id);

  /**
   * 회원 정보 수정
   * @param memberVO 회원 정보
   * @return 처리된 레코드 수
   */
  public int update(MemberVO memberVO);

  /**
   * 프로필 파일 수정
   * @param memberVO 회원 정보
   * @return 처리된 레코드 수
   */
  public int update_profile(MemberVO memberVO);

  /**
   * 회원 등급 수정
   * @param memberVO 회원 정보
   * @return 처리된 레코드 수
   */
  public int grade_update(MemberVO memberVO);

  /**
   * 회원 삭제
   * @param memberno 회원 번호
   * @return 처리된 레코드 수
   */
  public int delete(int memberno);


  /* =========================================================
   * 로그인 및 계정 찾기
   * ========================================================= */

  /**
   * 로그인 처리
   * @param map 로그인 정보
   * @return 로그인 성공 여부
   */
  public int login(HashMap<String, Object> map);

  /**
   * 아이디 찾기
   * @param memberVO 회원 정보
   * @return 회원 목록
   */
  public ArrayList<MemberVO> find_id(MemberVO memberVO);

  /**
   * 비밀번호 찾기
   * @param memberVO 회원 정보
   * @return 회원 정보
   */
  public MemberVO find_passwd(MemberVO memberVO);
  /**
   * 비밀번호 변경
   * @param memberVO 회원 정보
   * @return 처리된 레코드 수
   */
  public int update_passwd(MemberVO memberVO);

  /* =========================================================
   * 검색 및 페이징
   * ========================================================= */

  /**
   * 이름, 아이디 검색 목록
   * @param hashMap 검색 조건
   * @return 검색 목록
   */
  public ArrayList<MemberVO> list_by_search(HashMap<String, Object> hashMap);

  /**
   * 검색 레코드 수
   * @param hashMap 검색 조건
   * @return 검색된 레코드 수
   */
  public int list_by_search_count(HashMap<String, Object> hashMap);

  /**
   * 검색 + 페이징 목록
   * @param map 검색 조건
   * @return 검색 목록
   */
  public ArrayList<MemberVO> list_by_search_paging(HashMap<String, Object> map);

  /**
   * 등급별 검색 레코드 수
   * @param hasMap 검색 조건
   * @return 검색된 레코드 수
   */
  public int grade_list_by_search_count(HashMap<String, Object> hasMap);

  /**
   * 등급별 검색 + 페이징
   * @param map 검색 조건
   * @return 검색 목록
   */
  public ArrayList<MemberVO> grade_list_by_search_paging(HashMap<String, Object> map);


  /* =========================================================
   * 매너 온도 및 매칭 기능
   * ========================================================= */

  /**
   * 매너 온도 증가
   * @param memberno 회원 번호
   * @return 처리된 레코드 수
   */
  public int increase_manner_temp(int memberno);

  /**
   * 매너 온도 감소
   * @param memberno 회원 번호
   * @return 처리된 레코드 수
   */
  public int decrease_manner_temp(int memberno);
  /**
   * 🌡️ 매너온도 변경
   */
  public int updateMannerTemp(HashMap<String, Object> map);
  
  /**
   * 매칭 성공 횟수 증가
   * @param memberno 회원 번호
   * @return 처리된 레코드 수
   */
  public int increase_match_cnt(int memberno);

  /**
   * 사용자 위치 정보 수정
   * @param memberVO 회원 정보
   * @return 처리된 레코드 수
   */
  public int update_location(MemberVO memberVO);
  /**
   * 주변 사용자 목록 조회
   * @param map 위치 정보
   * @return 회원 목록
   */
  public ArrayList<MemberVO> list_near_member(HashMap<String, Object> map);

}