package dev.mvc.breed_recom;

import java.util.ArrayList;
import java.util.HashMap;

public interface Breed_recomProcInter {
  
  /**
   * 최초 추천 등록
   * @param breed_recomVO
   * @return 등록된 레코드 수
   */
  public int create(Breed_recomVO breed_recomVO);
  
  /**
   * 추천 이력 존재 여부 확인
   * (해당 회원이 해당 견종을 추천한 적 있는지)
   * @param hashmap (breedno, memberno)
   * @return count
   */
  public int recom_read(HashMap<String, Object> hashmap);
  
  /**
   * 추천 상태 조회 (1: 추천, 0: 취소)
   * @param hashmap (breedno, memberno)
   * @return Breed_recomVO
   */
  public Breed_recomVO recom_check(HashMap<String, Object> hashmap);

  /**
   * 특정 회원이 추천한 견종 목록
   * @param memberno
   * @return 리스트
   */
  public ArrayList<Breed_recomVO> member_read(int memberno);

  /**
   * 특정 견종을 추천한 회원 목록
   * @param breedno
   * @return 리스트
   */
  public ArrayList<Breed_recomVO> breed_read(int breedno);

  /**
   * 추천 처리 (1로 변경)
   * @param hashmap (breedno, memberno)
   * @return 처리된 row 수
   */
  public int recom(HashMap<String, Object> hashmap);

  /**
   * 추천 취소 (0으로 변경)
   * @param hashmap (breedno, memberno)
   * @return 처리된 row 수
   */
  public int recom_cancel(HashMap<String, Object> hashmap);
}