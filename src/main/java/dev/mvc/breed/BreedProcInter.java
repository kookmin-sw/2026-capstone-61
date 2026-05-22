package dev.mvc.breed;

import java.util.ArrayList;
import java.util.HashMap;

public interface BreedProcInter {

  /** 신규 견종 등록 */
  public int create(BreedVO breedVO);

  /** 전체 목록 */
  public ArrayList<BreedVO> list_all();
 
  /** 카테고리별 목록 */
  public ArrayList<BreedVO> list_by_cat_no(int cat_no);
  
  /** 상세 조회 */
  public BreedVO read(int breedno);
 
  /** 전체 검색 */
  public ArrayList<BreedVO> list_all_search(String word);
  
  /** 카테고리 검색 */
  public ArrayList<BreedVO> list_by_cat_no_search(HashMap<String, Object> map);
  
  /** 카테고리 검색 개수 */
  public int list_by_cat_no_search_count(HashMap<String, Object> map);
  
  /** 카테고리 검색 + 페이징 */
  public ArrayList<BreedVO> list_by_cat_no_search_paging(HashMap<String, Object> map);
  
  /** 페이징 UI */
  public String pagingBox(int cat_no, int now_page, String word, String list_file, int search_count, 
                         int record_per_page, int page_per_block);   

  /** 텍스트 수정 */
  public int update_text(BreedVO breedVO);
  
  
  /** 삭제 */
  public int delete(int breedno);
  
  /** 카테고리별 개수 */
  public int count_by_cat_no(int cat_no);
  
  /** 카테고리 전체 삭제 */
  public int delete_by_cat_no(int cat_no);

  /** 회원별 개수 */
  public int count_by_memberno(int memberno);
  
  /** 조회수 증가 */
  public int update_viewcnt(int breedno);
  
  /** 추천수 증가 */
  public int recom(int breedno);          // 🔥 증가
  
  /** 추천수 감소 */
  public int recom_cancel(int breedno);   // 🔥 감소

  public BreedVO readByName(String breed_name);
  /**
   * 전체 품종 수
   * @return
   */
  public int count_all();
}