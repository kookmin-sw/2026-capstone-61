package dev.mvc.match_apply;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MatchApplyVO {

  // =========================================================
  // MATCH_APPLY
  // =========================================================

  /** 신청 번호(PK) */
  private int applyno;

  /** 매칭 게시글 번호(FK) */
  private int matchno;

  /** 신청 회원 번호(FK) */
  private int memberno;

  /** 신청 강아지 번호(FK) */
  private int dogno;

  /** 신청 메시지 */
  private String msg = "";

  /** 신청 당시 강아지 DMTI */
  private String dmti = "";

  /**
   * 신청 상태
   * 1 : 신청대기
   * 2 : 수락
   * 3 : 산책중
   * 4 : 산책완료
   * 5 : 거절
   * 6 : 취소
   */
  private int apply_status = 1;

  /**
   * 매칭 성공 여부
   * 0 : 실패/진행중
   * 1 : 성공
   */
  private int success_yn = 0;

  /**
   * 리뷰 작성 여부
   * 0 : 미작성
   * 1 : 작성완료
   */
  private int review_written = 0;

  /** 수락 일시 */
  private String accept_date = "";

  /** 취소/거절 일시 */
  private String cancel_date = "";
  /** 취소 사유 */
  private String cancel_reason = "";

  /** 취소한 회원 번호 */
  private int cancel_by;

  /** 산책 완료 일시 */
  private String complete_date = "";

  /** 신청일 */
  private String rdate = "";

  // =========================================================
  // MEMBER JOIN
  // =========================================================

  /** 신청자 닉네임 */
  private String nickname = "";

  /** 신청자 아이디 */
  private String id = "";
  /** 신청자 회원 번호 */
  private int applicant_memberno;

  /** 신청자 프로필 이미지 */
  private String profile = "";
  /** 신청자 매너 온도 (추가) */
  private double manner_score;
  // =========================================================
  // MATCH_POST JOIN
  // =========================================================

  /** 매칭 게시글 제목 */
  private String title = "";

  /** 약속 시간 */
  private String mdate = "";

  /** 약속 장소 */
  private String mplace = "";

  /** 모집 상태 */
  private String status = "";

  /** 방장 회원 번호 */
  private int host_memberno;

  /** 방장 닉네임 */
  private String host_nickname = "";

  // =========================================================
  // DOG JOIN
  // =========================================================

  /** 강아지 이름 */
  private String dog_name = "";

  /** 견종 */
  private String breed = "";

  /** 강아지 나이 */
  private int age;

  /** 강아지 성별 */
  private String gender = "";

  /** 원본 파일명 */
  private String file1 = "";

  /** 서버 저장 파일명 */
  private String file1saved = "";

  /** 썸네일 */
  private String thumb1 = "";

  /** 강아지 DBTI */
  private String dbti_type = "";

  // =========================================================
  // MESSENGER
  // =========================================================

  /** 채팅방 번호 */
  private int room_no;

  /** 채팅방 존재 여부 */
  private int chat_exists;
  /** 리뷰 대상 회원 번호 */
  private int target_no;

}