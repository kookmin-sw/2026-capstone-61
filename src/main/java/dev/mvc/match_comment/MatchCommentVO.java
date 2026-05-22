package dev.mvc.match_comment;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/* =========================================================
   🐾 MATCH_COMMENT VO
========================================================= */

@Getter
@Setter
@ToString
public class MatchCommentVO {

  /* =========================================================
     기본 정보
  ========================================================= */

  // 댓글 번호(PK)
  private int commentno;

  // 게시글 번호(FK)
  private int matchno;

  // 작성자 회원 번호(FK)
  private int memberno;

  // 부모 댓글 번호(대댓글)
  private Integer parentno;


  /* =========================================================
     댓글 내용
  ========================================================= */

  // 댓글 내용
  private String content;

  // 댓글 타입
  // 1 : 일반 댓글
  // 2 : 대댓글
  // 3 : 작성자 공지
  private int comment_type;


  /* =========================================================
     상태 관리
  ========================================================= */

  // 좋아요 수
  private int likecnt;

  // 신고 수
  private int reportcnt;

  // 비밀 댓글 여부
  // Y : 비밀 댓글
  // N : 공개 댓글
  private String is_secret;

  // 삭제 여부
  // Y : 삭제
  // N : 정상
  private String is_deleted;


  /* =========================================================
     작성일
  ========================================================= */

  // 작성일
  private Date rdate;


  /* =========================================================
     추가 출력용 필드
     (JOIN 조회 시 사용)
  ========================================================= */

  // 작성자 닉네임
  private String nickname;

  // 작성자 프로필 이미지
  private String profile_img;

  // 작성자 매너온도
  private double manner_temp;

}