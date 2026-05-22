package dev.mvc.comments;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class CommentsVO {
  
  /** 댓글 번호 (PK) */
  private int comment_no;   // DB와 동일하게 수정
  
  /** 게시글 번호 */
  private int communityno;
  
  /** 작성자 회원 번호 */
  private int memberno;
  
  /** 작성자 닉네임 */
  private String nickname;
  
  /** 댓글 내용 */
  private String content;
  
  /** 부모 댓글 번호 (대댓글용) */
  private Integer parent_no = 0;
  
  /** 댓글 깊이 (0: 댓글, 1: 대댓글) */
  private int depth;
  
  /** 작성일 */
  private String rdate;  // 필요하면 Date로 바꿔도 OK
  
  /** 삭제 여부 */
  private String is_deleted;
}