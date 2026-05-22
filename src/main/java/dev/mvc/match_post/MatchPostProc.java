package dev.mvc.match_post;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("dev.mvc.match_post.MatchPostProc")
public class MatchPostProc implements MatchPostProcInter {

  @Autowired
  private MatchPostDAOInter matchPostDAO;

  /**
   * 산책 매칭 게시글 등록
   */
  @Override
  public int create(MatchPostVO matchPostVO) {

    // HTML datetime-local 형식의 T 제거
    // ex) 2026-05-13T18:30 -> 2026-05-13 18:30
    if (matchPostVO.getMdate() != null &&
        matchPostVO.getMdate().contains("T")) {

      matchPostVO.setMdate(
          matchPostVO.getMdate().replace("T", " ")
      );
    }

    return this.matchPostDAO.create(matchPostVO);
  }

  /**
   * 카테고리별 목록 조회 + 검색/필터/정렬
   */
  @Override
  public ArrayList<MatchPostVO> list_by_cat_no(HashMap<String, Object> map) {

    /*
     * 사용 예시
     *
     * map.put("cat_no", 1);
     * map.put("word", "강남");
     * map.put("dmti_filter", "E");
     * map.put("manner_filter", "Y");
     * map.put("my_lat", 37.123);
     * map.put("my_lng", 127.123);
     * map.put("sort", "distance");
     */

    return this.matchPostDAO.list_by_cat_no(map);
  }

  /**
   * 게시글 상세 조회
   */
  @Override
  public MatchPostVO read(int matchno) {

    // 조회수 증가
    this.matchPostDAO.update_viewcnt(matchno);

    // 게시글 조회
    return this.matchPostDAO.read(matchno);
  }

  /**
   * 게시글 수정
   */
  @Override
  public int update(MatchPostVO matchPostVO) {

    // datetime-local의 T 제거
    if (matchPostVO.getMdate() != null &&
        matchPostVO.getMdate().contains("T")) {

      matchPostVO.setMdate(
          matchPostVO.getMdate().replace("T", " ")
      );
    }

    return this.matchPostDAO.update(matchPostVO);
  }

  /**
   * 게시글 삭제
   */
  @Override
  public int delete(int matchno) {

    // 실제 삭제
    return this.matchPostDAO.delete(matchno);

    /*
    // 소프트 삭제 사용 시
    return this.matchPostDAO.delete_update(matchno);
    */
  }

  /**
   * 특정 게시글 개수 조회
   */
  @Override
  public int list_count_by_matchno(int matchno) {
    return this.matchPostDAO.list_count_by_matchno(matchno);
  }

  /**
   * 회원별 작성 게시글 조회
   */
  @Override
  public ArrayList<MatchPostVO> list_by_memberno(int memberno) {
    return this.matchPostDAO.list_by_memberno(memberno);
  }

  /**
   * 모집 상태 변경
   *
   * 1 : 모집중
   * 2 : 모집마감
   * 3 : 산책중
   * 4 : 산책완료
   */
  @Override
  public int update_status_n(int matchno) {
    return this.matchPostDAO.update_status_n(matchno);
  }

  /**
   * 조회수 증가
   */
  @Override
  public int update_viewcnt(int matchno) {
    return this.matchPostDAO.update_viewcnt(matchno);
  }

  /**
   * 검색 결과 개수
   */
  @Override
  public int search_count(HashMap<String, Object> map) {
    return this.matchPostDAO.search_count(map);
  }

  /**
   * 오늘 산책 예정 목록
   */
  @Override
  public ArrayList<MatchPostVO> list_today_match() {
    return this.matchPostDAO.list_today_match();
  }

  /**
   * 전체 목록 조회
   */
  @Override
  public ArrayList<MatchPostVO> list_all() {

    // 빈 map 생성
    HashMap<String, Object> map = new HashMap<String, Object>();

    // cat_no 없는 전체 조회
    map.put("cat_no", null);

    return this.matchPostDAO.list_by_cat_no(map);
  }

}