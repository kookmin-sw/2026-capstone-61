package dev.mvc.match_review;

import java.util.ArrayList;

import jakarta.servlet.http.HttpSession;
import dev.mvc.match_apply.MatchApplyProcInter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/match_review")
public class MatchReviewCont {

  @Autowired
  @Qualifier("dev.mvc.match_review.MatchReviewProc")
  private MatchReviewProcInter matchReviewProc;
  @Autowired
  @Qualifier("dev.mvc.match_apply.MatchApplyProc")
  private MatchApplyProcInter matchApplyProc;
  public MatchReviewCont() {
    System.out.println("-> MatchReviewCont created.");
  }

  /**
   * ✍ 리뷰 작성 폼
   * 
   * @param matchno   매칭 번호
   * @param target_no 리뷰 대상 회원 번호
   * @return
   */
  @RequestMapping(value = "/create.do", method = RequestMethod.GET)
  public ModelAndView create(int matchno, int target_no) {

    ModelAndView mav = new ModelAndView();

    // 리뷰 작성에 필요한 값 전달
    mav.addObject("matchno", matchno);

    mav.addObject("target_no", target_no);

    // templates/match_review/create.html
    mav.setViewName("match_review/create");

    return mav;
  }


  /**
   * 📝 리뷰 등록 처리
   * 
   * @param session 로그인 세션
   * @param matchReviewVO 리뷰 정보
   * @return
   */
  @RequestMapping(value = "/create.do", method = RequestMethod.POST)
  @ResponseBody // ★ 중요: 페이지 이동이 아닌 문자열 자체를 응답으로 보냄
  public String create_proc(HttpSession session, MatchReviewVO matchReviewVO) {

    // 1. 로그인 여부 확인
    if (session.getAttribute("memberno") == null) {
      return "LOGIN_REQUIRED";
    }

    int memberno = (int) session.getAttribute("memberno");
    matchReviewVO.setWriter_no(memberno);

    // 2. 중복 리뷰 체크
    int check = this.matchReviewProc.check_duplicate(
        matchReviewVO.getMatchno(),
        memberno
    );

    if (check > 0) {
      return "DUPLICATE"; // 이미 리뷰 작성함
    }

 // 3. 리뷰 등록 실행
    int cnt =
        this.matchReviewProc.create(
            matchReviewVO
        );

    // =====================================================
    // 리뷰 등록 성공
    // =====================================================
    if (cnt == 1) {

      // ===============================================
      // 리뷰 작성 완료 처리
      // ===============================================
      this.matchApplyProc.update_review_written(
          matchReviewVO.getApplyno()
      );
      System.out.println("writer_no: " + matchReviewVO.getWriter_no());

      System.out.println("target_no: " + matchReviewVO.getTarget_no());
      return "SUCCESS";

    } else {

      return "CREATE_FAIL";

    }
  }


  /**
   * 👤 특정 회원이 받은 리뷰 목록
   * 마이페이지용
   * 
   * @param target_no 리뷰 대상 회원 번호
   * @return
   */
  @RequestMapping(value = "/list_by_target.do",
          method = RequestMethod.GET)
	public ModelAndView list_by_target(int target_no) {
	
	ModelAndView mav = new ModelAndView();
	
	ArrayList<MatchReviewVO> list =
	this.matchReviewProc.list_by_target(target_no);
	
	// =====================================================
	// ⭐ 전체 평균 계산
	// =====================================================
	double avg_score = 0;
	
	if (list.size() > 0) {
	
	double total = 0;
	
	for (MatchReviewVO vo : list) {
	
	total += vo.getOwner_score();
	
	}
	
	avg_score = total / list.size();
	
	}
	
	mav.addObject("avg_score", avg_score);
	
	mav.addObject("list", list);
	
	mav.addObject("target_no", target_no);
	
	mav.setViewName("match_review/list_by_target");
	
	return mav;
	}


  /**
   * 📚 내가 관련된 리뷰 전체 조회
   * (내가 작성한 리뷰 + 내가 받은 리뷰)
   * 
   * @param session 로그인 세션
   * @return
   */
  @RequestMapping(value = "/my_reviews.do",
                  method = RequestMethod.GET)
  public ModelAndView my_reviews(HttpSession session) {

    ModelAndView mav = new ModelAndView();

    // 로그인 확인
    if (session.getAttribute("memberno") != null) {

      int memberno = (int) session.getAttribute("memberno");

      ArrayList<MatchReviewVO> list =
          this.matchReviewProc.list_all_my_reviews(memberno);

      mav.addObject("list", list);

      mav.setViewName("match_review/my_reviews");

    } else {

      mav.addObject("code", "login_required");

      mav.setViewName("match_review/msg");

    }

    return mav;
  }


  /**
   * ❌ 리뷰 삭제 처리
   * 
   * @param reviewno 리뷰 번호
   * @param target_no 리뷰 대상 회원 번호
   * @return
   */
  @RequestMapping(value = "/delete.do", method = RequestMethod.POST)
  public ModelAndView delete(HttpSession session, int reviewno, int target_no) {
      ModelAndView mav = new ModelAndView();
      
      // 1. 로그인 확인
      Integer memberno = (Integer)session.getAttribute("memberno");
      if (memberno == null) {
          mav.setViewName("redirect:/member/login");
          return mav;
      }

      // 2. 작성자 본인 확인 로직 (필수!)
      // MatchReviewVO vo = this.matchReviewProc.read(reviewno);
      // if (vo.getWriter_no() != memberno) { ... 에러 처리 ... }

      int cnt = this.matchReviewProc.delete(reviewno);
      mav.setViewName("redirect:/match_review/list_by_target.do?target_no=" + target_no);
      return mav;
  }

}