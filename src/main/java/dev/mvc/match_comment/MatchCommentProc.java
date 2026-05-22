package dev.mvc.match_comment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import dev.mvc.match_post.MatchPostDAOInter;

@Component
public class MatchCommentProc implements MatchCommentProcInter {

  @Autowired
  private MatchCommentDAOInter matchCommentDAO;

  // 🔥 게시글 댓글 수 변경용
  @Autowired
  private MatchPostDAOInter matchPostDAO;


  /* =========================================================
     댓글 등록
  ========================================================= */

  /**
   * 댓글 등록
   */
  @Override
  public int create(MatchCommentVO matchCommentVO) {

    // 댓글 등록 처리
    int cnt = this.matchCommentDAO.create(matchCommentVO);

    // 등록 성공 시 댓글 수 증가
    if (cnt == 1) {

      Map<String, Object> map = new HashMap<String, Object>();

      map.put("matchno", matchCommentVO.getMatchno());
      map.put("mode", "+");

      this.matchPostDAO.update_commentcnt(map);
    }

    return cnt;
  }


  /* =========================================================
     댓글 삭제
  ========================================================= */

  /**
   * 댓글 삭제 (소프트 삭제)
   */
  @Override
  public int delete(int commentno) {

    // 삭제 전 댓글 조회
    MatchCommentVO vo = this.matchCommentDAO.read(commentno);

    // 삭제 처리
    int cnt = this.matchCommentDAO.delete(commentno);

    // 삭제 성공 시 댓글 수 감소
    if (cnt == 1 && vo != null) {

      Map<String, Object> map = new HashMap<String, Object>();

      map.put("matchno", vo.getMatchno());
      map.put("mode", "-");

      this.matchPostDAO.update_commentcnt(map);
    }

    return cnt;
  }


  /* =========================================================
     게시글 댓글 목록
  ========================================================= */

  /**
   * 게시글 댓글 목록 조회
   */
  @Override
  public ArrayList<MatchCommentVO> list_by_matchno(int matchno) {
    return this.matchCommentDAO.list_by_matchno(matchno);
  }


  /* =========================================================
     대댓글 목록
  ========================================================= */

  /**
   * 대댓글 목록 조회
   */
  @Override
  public ArrayList<MatchCommentVO> list_by_parentno(int parentno) {
    return this.matchCommentDAO.list_by_parentno(parentno);
  }


  /* =========================================================
     댓글 조회
  ========================================================= */

  /**
   * 댓글 1건 조회
   */
  @Override
  public MatchCommentVO read(int commentno) {
    return this.matchCommentDAO.read(commentno);
  }


  /* =========================================================
     게시글 댓글 수 조회
  ========================================================= */

  /**
   * 게시글 댓글 수 조회
   */
  @Override
  public int count_by_matchno(int matchno) {
    return this.matchCommentDAO.count_by_matchno(matchno);
  }


  /* =========================================================
     회원 댓글 수 조회
  ========================================================= */

  /**
   * 회원 댓글 수 조회
   */
  @Override
  public int count_by_memberno(int memberno) {
    return this.matchCommentDAO.count_by_memberno(memberno);
  }


  /* =========================================================
     댓글 수정
  ========================================================= */

  /**
   * 댓글 수정
   */
  @Override
  public int update(MatchCommentVO matchCommentVO) {
    return this.matchCommentDAO.update(matchCommentVO);
  }


  /* =========================================================
     좋아요 증가
  ========================================================= */

  /**
   * 좋아요 증가
   */
  @Override
  public int increase_likecnt(int commentno) {
    return this.matchCommentDAO.increase_likecnt(commentno);
  }


  /* =========================================================
     좋아요 감소
  ========================================================= */

  /**
   * 좋아요 감소
   */
  @Override
  public int decrease_likecnt(int commentno) {
    return this.matchCommentDAO.decrease_likecnt(commentno);
  }


  /* =========================================================
     신고 수 증가
  ========================================================= */

  /**
   * 신고 수 증가
   */
  @Override
  public int increase_reportcnt(int commentno) {
    return this.matchCommentDAO.increase_reportcnt(commentno);
  }

}