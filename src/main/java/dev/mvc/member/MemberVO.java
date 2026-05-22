package dev.mvc.member;

import org.springframework.web.multipart.MultipartFile;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class MemberVO {

  /** 회원 번호 */
  private int memberno;

  /** 아이디(이메일) */
  private String id = "";

  /** 패스워드 */
  private String passwd = "";

  /** 회원 성명 */
  private String mname = "";

  /** 전화 번호 */
  private String tel = "";

  /** 우편 번호 */
  private String zipcode = "";

  /** 주소 1 */
  private String address1 = "";

  /** 주소 2 */
  private String address2 = "";

  /** 가입일 */
  private String mdate = "";

  /** 등급 (1: 일반, 2: 정지, 3: 탈퇴, 0: 관리자) */
  private int grade = 1;

  /** 생일 */
  private String birth = "";

  /** 성별 */
  private String sex = "";

  /** 닉네임 */
  private String nickname = "";

  // ========================= 추가 기능 컬럼 =========================

  /** 자기소개 */
  private String intro = "";

  /** 매너 온도 */
  private double manner_temp = 36.5;

  /** 매칭 성공 횟수 */
  private int match_cnt = 0;

  /** 사용자 위치 위도 */
  private double latitude;

  /** 사용자 위치 경도 */
  private double longitude;
  
  /** 위치 업데이트 시간 */
  private String location_update = "";

  // ========================= 로그인 및 세션 관리 =========================

  /** 기존 패스워드 */
  private String old_passwd = "";

  /** 아이디 저장 여부 */
  private String id_save = "";

  /** 비밀번호 저장 여부 */
  private String passwd_save = "";

  /** 로그인 후 이동 주소 */
  private String url_address = "";

  // ========================= 파일 업로드 =========================

  /** 업로드 파일 */
  private MultipartFile files1MF;

  /** 원본 파일명 */
  private String profile = "";

  /** 저장 파일명 */
  private String profilesaved = "";

  /** 썸네일 */
  private String thumbs = "";

  /** 파일 크기 */
  private long sizes;

  /** 파일 크기 라벨 */
  private String sizes_label = "";

  // ================================================================

  /** 비밀번호 입력 여부 확인 */
  public boolean isEmpty() {
    return this.passwd == null || this.passwd.trim().isEmpty();
  }
}