package dev.mvc.breed;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Spring Boot + MyBatis가 자동으로 구현체 생성
 */
public interface BreedDAOInter {

  /**
   * 신규 견종 등록
   * @param breedVO 등록할 데이터
   * @return 등록된 레코드 갯수
   */
  public int create(BreedVO breedVO);

  /**
   * 모든 카테고리의 견종 목록
   * @return 견종 목록
   */
  public ArrayList<BreedVO> list_all();
  
  /**
   * 카테고리별 견종 목록
   * @param cat_no 카테고리 번호
   * @return 특정 카테고리의 견종 목록
   */
  public ArrayList<BreedVO> list_by_cat_no(int cat_no);
  
  /**
   * 상세 조회
   * @param breedno 견종 번호
   * @return 견종 상세 정보
   */
  public BreedVO read(int breedno);
  
  /**
   * 전체 검색 목록
   * @param word 검색어
   * @return 검색된 견종 목록
   */
  public ArrayList<BreedVO> list_all_search(String word);
  
  /**
   * 카테고리별 검색 목록
   * @param map (cat_no, word)
   * @return 검색된 견종 목록
   */
  public ArrayList<BreedVO> list_by_cat_no_search(HashMap<String, Object> map);
  
  /**
   * 카테고리별 검색된 레코드 갯수
   * @param map (cat_no, word)
   * @return 검색된 결과 수
   */
  public int list_by_cat_no_search_count(HashMap<String, Object> map);
  
  /**
   * 카테고리별 검색 목록 + 페이징
   * @param map (cat_no, word, start_num, end_num)
   * @return 페이징 처리된 견종 목록
   */
  public ArrayList<BreedVO> list_by_cat_no_search_paging(HashMap<String, Object> map);
  
  /**
   * 텍스트 정보 수정 (이름, 제목, 내용, 원산지, 체중 등)
   * @param breedVO
   * @return 수정된 레코드 수
   */
  public int update_text(BreedVO breedVO);


  /**
   * 데이터 삭제
   * @param breedno 삭제할 견종 번호
   * @return 삭제된 레코드 수
   */
  public int delete(int breedno);
  
  /**
   * 특정 카테고리에 속한 견종 수 산출
   * @param cat_no 카테고리 번호
   * @return 레코드 수
   */
  public int count_by_cat_no(int cat_no);
 
  /**
   * 특정 카테고리에 속한 모든 견종 삭제 (카테고리 삭제 시 필요)
   * @param cat_no 카테고리 번호
   * @return 삭제된 레코드 수
   */
  public int delete_by_cat_no(int cat_no);
  
  /**
   * 특정 관리자가 등록한 견종 수 산출
   * @param memberno 관리자 번호
   * @return 레코드 수
   */
  public int count_by_memberno(int memberno);

  /**
   * 조회수 증가
   * @param breedno 견종 번호
   * @return 증가 성공 여부
   */
  public int update_viewcnt(int breedno);
  
  /**
   * 추천수 증가
   * @param breedno 견종 번호
   * @return 증가 성공 여부
   */
  public int recom(int breedno);        // 추천 증가
  public int recom_cancel(int breedno); // 추천 감소
  public BreedVO readByName(String breed_name);
  /**
   * 전체 품종 수
   * @return
   */
  public int count_all();

}