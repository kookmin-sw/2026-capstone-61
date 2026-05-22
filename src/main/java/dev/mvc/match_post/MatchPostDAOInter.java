package dev.mvc.match_post;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public interface MatchPostDAOInter {

  /**
   * 산책 매칭 게시글 등록
   * 
   * @param matchPostVO 등록 정보
   * @return 등록 결과
   */
  public int create(MatchPostVO matchPostVO);

  /**
   * 전체 게시글 목록 조회
   * 
   * @param map 검색/정렬 조건
   * @return 전체 게시글 리스트
   */
  public ArrayList<MatchPostVO> list_all(HashMap<String, Object> map);

  /**
   * 카테고리별 게시글 목록 조회
   * 검색 / 거리 / DMTI / 정렬 포함
   * 
   * @param map 조회 조건
   * @return 카테고리별 게시글 리스트
   */
  public ArrayList<MatchPostVO> list_by_cat_no(HashMap<String, Object> map);

  /**
   * 게시글 상세 조회
   * 
   * @param matchno 게시글 번호
   * @return MatchPostVO
   */
  public MatchPostVO read(int matchno);

  /**
   * 조회수 증가
   * 
   * @param matchno 게시글 번호
   * @return 처리 결과
   */
  public int update_viewcnt(int matchno);

  /**
   * 게시글 수정
   * 
   * @param matchPostVO 수정 정보
   * @return 수정 결과
   */
  public int update(MatchPostVO matchPostVO);

  /**
   * 모집 상태 변경
   * 
   * 1 : 모집중
   * 2 : 모집마감
   * 3 : 산책중
   * 4 : 산책완료
   * 
   * @param matchno 게시글 번호
   * @return 처리 결과
   */
  public int update_status_n(int matchno);

  /**
   * 게시글 삭제
   * 
   * @param matchno 게시글 번호
   * @return 삭제 결과
   */
  public int delete(int matchno);

  /**
   * 특정 회원 작성 게시글 조회
   * 
   * @param memberno 회원 번호
   * @return 회원 게시글 리스트
   */
  public ArrayList<MatchPostVO> list_by_memberno(int memberno);

  /**
   * 오늘 산책 예정 목록 조회
   * 
   * @return 오늘 산책 리스트
   */
  public ArrayList<MatchPostVO> list_today_match();

  /**
   * 검색 결과 개수
   * 
   * @param map 검색 조건
   * @return 검색 건수
   */
  public int search_count(HashMap<String, Object> map);

  /**
   * 회원 평균 매너 점수 조회
   * 
   * @param memberno 회원 번호
   * @return 평균 매너 점수
   */
  public double get_manner_score(int memberno);

  /**
   * 댓글 수 조회
   * 
   * @param matchno 게시글 번호
   * @return 댓글 수
   */
  public int list_count_by_matchno(int matchno);
  /**
   * 댓글 수 증가/감소
   * @param map
   * @return 처리 결과
   */
  public int update_commentcnt(Map<String, Object> map);

}