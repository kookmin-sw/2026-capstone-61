package dev.mvc.mlogin;

import java.util.ArrayList;



public interface MloginDAOInter {

  /**
   * 로그인 내역 등록
   * @param mloginVO
   * @return 추가한 레코드 갯수
   */
  public int mlogin_insert(MloginVO mloginVO);
  
  /**
   * memberno로 회원 로그인 내역 조회
   * @param memberno
   * @return
   */
  public ArrayList<MloginVO> mlogin_read(int memberno);
  
  
  
  
}
