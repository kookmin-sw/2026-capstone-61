package dev.mvc.community;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public interface CommunityDAOInter {
  /**
   * 글 등록
   * @param communityVO
   * @return 등록된 레코드 수
   */
  public int create(CommunityVO communityVO);

  /**
   * 카테고리(cat_no) 및 견종(breedno)별 목록 조회 (검색 + 페이징)
   * @param map (cat_no, breedno, word, start_num, end_num 포함)
   * @return 게시글 리스트
   */
  public ArrayList<CommunityVO> list_by_community_search_paging(HashMap<String, Object> map);

  /**
   * 특정 조건(cat_no, breedno, 검색어)에 맞는 게시글 총 개수 (페이징용)
   * @param map
   * @return 레코드 수
   */
  public int search_count(HashMap<String, Object> map);

  /**
   * 글 상세 조회
   * @param communityno
   * @return CommunityVO
   */
  public CommunityVO read(int communityno);

  /**
   * 조회수 증가
   * @param communityno
   * @return 수정된 레코드 수
   */
  public int update_viewcnt(int communityno);
  /**
   * replycnt 증감 처리
   * @param map communityno(게시글 번호), mode("+" 또는 "-")
   * @return 수정된 행 수
   */
  public int updateReplycnt(Map<String, Object> map);
  /**
   * 글 텍스트 내용 수정
   * @param communityVO
   * @return 수정된 레코드 수
   */
  public int update_text(CommunityVO communityVO);
  /**
   * 글 텍스트 내용 수정
   * @param communityVO
   * @return 수정된 레코드 수
   */
  public int update(CommunityVO communityVO);

  /**
   * 글 삭제
   * @param communityno
   * @return 삭제된 레코드 수
   */
  public int delete(int communityno);
}