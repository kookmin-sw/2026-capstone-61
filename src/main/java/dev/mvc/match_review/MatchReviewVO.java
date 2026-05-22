package dev.mvc.match_review;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MatchReviewVO {

  /** 리뷰 번호 */
  private int reviewno;

  /** 매칭 게시글 번호 */
  private int matchno;

  /** 리뷰 작성자 회원번호 */
  private int writer_no;

  /** 리뷰 대상 회원번호 */
  private int target_no;

  // =========================================================
  // 🐶 반려견 평가
  // =========================================================

  /** 반려견 평점 (0.0 ~ 5.0) */
  private double dog_score;

  /** 반려견 후기 */
  private String dog_comment;


  // =========================================================
  // 👤 보호자 매너 평가
  // =========================================================

  /** 보호자 매너 평점 (0.0 ~ 5.0) */
  private double owner_score;

  /** 보호자 매너 후기 */
  private String owner_comment;


  // =========================================================
  // 📝 종합 리뷰
  // =========================================================

  /** 종합 리뷰 내용 */
  private String content;

  /** 리뷰 등록일 */
  private String rdate;


  // =========================================================
  // 🔥 JOIN 조회용 추가 필드
  // =========================================================

  /** 리뷰 작성자 닉네임 */
  private String writer_nickname;

  /** 리뷰 대상자 닉네임 */
  private String target_nickname;

  /** 매칭 게시글 제목 */
  private String match_title;
//=========================================================
//MATCH_APPLY 연결용
//=========================================================
  /** 매너온도 변화량 */
  private double manner_delta;
/** 신청 번호 */
private int applyno;

}