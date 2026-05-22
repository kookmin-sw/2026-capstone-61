package dev.mvc.category;

import java.util.ArrayList;
import java.util.Map;

public interface CategoryDAOInter {

  /** 등록 */
  public int create(CategoryVO categoryVO);
  
  /** 전체 목록 */
  public ArrayList<CategoryVO> list_all();
  
  /** 조회 */
  public CategoryVO read(int cat_no);
  
  /** 수정 */
  public int update(CategoryVO categoryVO);
  
  /** 삭제 */
  public int delete(int cat_no);

  /** 순서 ↑ (숫자 감소) */
  public int update_seqno_forward(int cat_no);
  
  /** 순서 ↓ (숫자 증가) */
  public int update_seqno_backward(int cat_no);
  
  /** 공개 설정 (Y) */
  public int update_visible_y(int cat_no);

  /** 비공개 설정 (N) */
  public int update_visible_n(int cat_no);
    
  /** 공개 카테고리 전체 목록 */
  public ArrayList<CategoryVO> list_all_visible();
  
  /** 검색 목록 */
  public ArrayList<CategoryVO> list_search(String word);    
      
  /** 검색 목록 + 페이징 */
  public ArrayList<CategoryVO> list_search_paging(Map<String, Object> map);
  
  /** 검색 레코드 수 */
  public int list_search_count(String word);
	
  /** 특정 카테고리에 속한 레코드 수 */
  public int count_by_cat_no(int cat_no);

  /** 메뉴용 대분류(type) 목록 */
  public ArrayList<String> gen_type();

  /** 특정 대분류(type)에 속하는 소분류 목록 */
  public ArrayList<CategoryVO> list_by_type(String cat_type);
  
}