package dev.mvc.comments;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class CommentsMemberVO {

  /** 댓글 번호 */
  private int comment_no;

  /** 게시글 번호 */
  private int communityno;

  /** 작성자 회원 번호 */
  private int memberno;

  /** 작성자 닉네임 */
  private String nickname;

  /** 댓글 내용 */
  private String content;

  /** 부모 댓글 번호 */
  private Integer parent_no;

  /** 댓글 깊이 */
  private int depth;

  /** 작성일 */
  private String rdate;

  /** 삭제 여부 */
  private String is_deleted;

  /** 회원 아이디 (JOIN용) */
  private String id;

  /** 프로필 이미지 */
  private String thumbs;

  /** 회원 등급 */
  private int grade;
}