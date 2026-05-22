package dev.mvc.category;

import java.util.ArrayList;

public interface CategoryProcInter {

  /**
   * 등록
   * @param categoryVO 등록할 카테고리 정보 객체
   * @return 등록한 레코드 갯수
   */
  public int create(CategoryVO categoryVO);
  
  /**
   * 전체 목록
   * @return 전체 카테고리 목록
   */
  public ArrayList<CategoryVO> list_all();
  
  /**
   * 조회
   * @param cat_no 카테고리 번호 (PK)
   * @return 조회된 카테고리 객체
   */
  public CategoryVO read(int cat_no);
  
  /**
   * 수정
   * @param categoryVO 수정할 카테고리 정보 객체
   * @return 수정된 레코드 갯수
   */
  public int update(CategoryVO categoryVO);
  
  /**
   * 삭제
   * @param cat_no 카테고리 번호
   * @return 삭제된 레코드 갯수
   */
  public int delete(int cat_no);
  
  /**
   * 우선 순위 높임 (숫자 감소: 10 → 9)
   * @param cat_no
   * @return 수정한 레코드 갯수
   */
  public int update_seqno_forward(int cat_no);
  
  /**
   * 우선 순위 낮춤 (숫자 증가: 1 → 2)
   * @param cat_no
   * @return 수정한 레코드 갯수
   */
  public int update_seqno_backward(int cat_no);
 
  /**
   * 카테고리 공개 설정 (VISIBLE = 'Y')
   * @param cat_no
   * @return 수정된 레코드 갯수
   */
  public int update_visible_y(int cat_no);

  /**
   * 카테고리 비공개 설정 (VISIBLE = 'N')
   * @param cat_no
   * @return 수정된 레코드 갯수
   */
  public int update_visible_n(int cat_no);
  
  /**
   * 사용자용 공개 카테고리 목록 (VISIBLE = 'Y')
   * @return 카테고리 목록
   */
  public ArrayList<CategoryVO> list_all_visible();
  
  /**
   * 관리자 검색 목록
   * @param word 검색어
   * @return 조회한 레코드 목록
   */
  public ArrayList<CategoryVO> list_search(String word);      

  /**
   * 검색 + 페이징 목록
   * @param word 검색어
   * @param now_page 현재 페이지
   * @param record_per_page 페이지당 레코드수
   * @return 조회한 페이징 레코드 목록
   */
  public ArrayList<CategoryVO> list_search_paging(String word, int now_page, int record_per_page);

  /**
   * 검색된 레코드 수
   * @param word 검색어
   * @return 레코드 수
   */
  public int list_search_count(String word);

  /**
   * 메뉴용 카테고리 목록 (상단 네비게이션)
   * @return 메뉴 리스트
   */
  public ArrayList<CategoryVOMenu> menu();

  public String pagingBox(int now_page, String word, String list_file, int search_count, 
                                      int record_per_page, int page_per_block);

 

  public ArrayList<CategoryVO> list_by_type(String cat_type);  
  
  
}