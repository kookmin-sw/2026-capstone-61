package dev.mvc.breed_recom;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

// 문제의 원인이었던 별명을 클래스 이름에 맞춰 수정했습니다.
// 기존: @Component("dev.mvc.recom.RecomProc") -> 패키지 경로까지 쓰면 충돌 위험이 큽니다.
@Component("dev.mvc.breed_recom.Breed_recomProc") 
public class Breed_recomProc implements Breed_recomProcInter {
  
  @Autowired
  private Breed_recomDAOInter recomDAO;
  
  /**
   * 최초 추천 등록
   */
  @Override
  public int create(Breed_recomVO breed_recomVO) {
    int cnt = this.recomDAO.create(breed_recomVO);
    return cnt;
  }
  
  /**
   * 추천 이력 존재 여부 확인
   */
  @Override
  public int recom_read(HashMap<String, Object> hashmap) {
    int cnt = this.recomDAO.recom_read(hashmap);
    return cnt;
  }
  
  /**
   * 추천 상태 조회
   */
  @Override
  public Breed_recomVO recom_check(HashMap<String, Object> hashmap) {
    Breed_recomVO breed_recomVO = this.recomDAO.recom_check(hashmap);
    return breed_recomVO;
  }

  /**
   * 특정 회원이 추천한 견종 목록
   */
  @Override
  public ArrayList<Breed_recomVO> member_read(int memberno) {
    ArrayList<Breed_recomVO> list = this.recomDAO.member_read(memberno);
    return list;
  }

  /**
   * 특정 견종을 추천한 회원 목록
   */
  @Override
  public ArrayList<Breed_recomVO> breed_read(int breedno) {
    ArrayList<Breed_recomVO> list = this.recomDAO.breed_read(breedno);
    return list;
  }

  /**
   * 추천 처리 (1)
   */
  @Override
  public int recom(HashMap<String, Object> hashmap) {
    int cnt = this.recomDAO.recom(hashmap);
    return cnt;
  }

  /**
   * 추천 취소 (0)
   */
  @Override
  public int recom_cancel(HashMap<String, Object> hashmap) {
    int cnt = this.recomDAO.recom_cancel(hashmap);
    return cnt;
  }

}