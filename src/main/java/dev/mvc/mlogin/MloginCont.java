package dev.mvc.mlogin;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import dev.mvc.member.MemberProcInter;
import dev.mvc.member.MemberVO;

@RequestMapping("/mlogin")
@Controller
public class MloginCont {

  @Autowired
  @Qualifier("dev.mvc.mlogin.MloginProc")
  private MloginProcInter mloginProc;
  
  @Autowired
  @Qualifier("dev.mvc.member.MemberProc")
  private MemberProcInter memberProc;

  public MloginCont() {
    System.out.println("-> MloginCont created."); // 클래스명 오타 수정
  }
  
  /**
   * 로그인 내역 조회 및 출력
   * http://localhost:9093/mlogin/mlogin_insert_form?memberno=1
   * @param model
   * @param memberno 회원 번호
   * @return mlogin/mlogin_form.html
   */
  @GetMapping(value="/mlogin_insert_form")
  public String mlogin_insert(Model model, int memberno) {
    
    // 1. 회원 정보 조회 (화면에 사용자 이름 등을 출력하기 위함)
    MemberVO memberVO = this.memberProc.read(memberno);
    model.addAttribute("memberVO", memberVO);
    
    // 2. 해당 회원의 최근 로그인 내역 리스트 조회
    ArrayList<MloginVO> mloginVO_list = this.mloginProc.mlogin_read(memberno);
    model.addAttribute("mloginVO", mloginVO_list);
    
    return "mlogin/mlogin_form"; // 앞의 / 제거하여 리턴
  }
}