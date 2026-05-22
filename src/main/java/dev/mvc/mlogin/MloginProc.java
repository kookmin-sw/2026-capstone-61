package dev.mvc.mlogin;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;



@Component("dev.mvc.mlogin.MloginProc")
public class MloginProc implements MloginProcInter {
  
  @Autowired
  private MloginDAOInter mloginDAO;
  
  public MloginProc(){
    System.out.println("-> AloginProc created.");
  }


  
  @Override
  public int mlogin_insert(MloginVO mloginVO) {
    int cnt = this.mloginDAO.mlogin_insert(mloginVO);
    return cnt;
  }

  @Override
  public ArrayList<MloginVO> mlogin_read(int memberno) {
    ArrayList<MloginVO> mloginVO = this.mloginDAO.mlogin_read(memberno);
    return mloginVO;
  }

}
