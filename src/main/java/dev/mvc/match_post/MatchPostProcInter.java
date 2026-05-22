package dev.mvc.match_post;

import java.util.ArrayList;
import java.util.HashMap;

public interface MatchPostProcInter {

  /**
   * 산책 매칭 게시글 등록
   */
  public int create(MatchPostVO matchPostVO);

  /**
   * 전체 목록 조회
   */
  public ArrayList<MatchPostVO> list_all();

  /**
   * 오늘 산책 예정 목록
   */
  public ArrayList<MatchPostVO> list_today_match();

  /**
   * 카테고리별 목록 조회 + 검색/필터/정렬
   */
  public ArrayList<MatchPostVO> list_by_cat_no(HashMap<String, Object> map);

  /**
   * 게시글 상세 조회
   */
  public MatchPostVO read(int matchno);

  /**
   * 조회수 증가
   */
  public int update_viewcnt(int matchno);

  /**
   * 게시글 수정
   */
  public int update(MatchPostVO matchPostVO);

  /**
   * 모집 상태 변경
   *
   * 1 : 모집중
   * 2 : 모집마감
   * 3 : 산책중
   * 4 : 산책완료
   */
  public int update_status_n(int matchno);

  /**
   * 게시글 삭제
   */
  public int delete(int matchno);

  /**
   * 회원별 게시글 조회
   */
  public ArrayList<MatchPostVO> list_by_memberno(int memberno);

  /**
   * 검색 결과 개수
   */
  public int search_count(HashMap<String, Object> map);

  /**
   * 특정 게시글 개수 조회
   */
  public int list_count_by_matchno(int matchno);

}