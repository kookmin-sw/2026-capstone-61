package dev.mvc.comments;

import java.util.ArrayList;

public interface CommentsProcInter {

  /**
   * 댓글 생성
   * @param commentsVO
   * @return 생성된 행 수
   */
  public int create(CommentsVO commentsVO);

  /**
   * 게시글 댓글 목록 조회
   * @param communityno 게시글 번호
   * @return 댓글 목록
   */
  public ArrayList<CommentsVO> list_by_community(int communityno);

  /**
   * 댓글 1건 조회
   * @param comment_no 댓글 번호
   * @return 댓글 정보
   */
  public CommentsVO read(int comment_no);

  /**
   * 게시글 댓글 개수
   * @param communityno 게시글 번호
   * @return 댓글 수
   */
  public int count_by_community(int communityno);

  /**
   * 댓글 수정
   * @param commentsVO
   * @return 수정된 행 수
   */
  public int update(CommentsVO commentsVO);

  /**
   * 댓글 삭제 (소프트 삭제)
   * @param comment_no 댓글 번호
   * @return 처리된 행 수
   */
  public int delete(int comment_no);
}