package dev.mvc.match_apply;

import java.util.ArrayList;

import jakarta.servlet.http.HttpSession;

import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import dev.mvc.dog.DogProcInter;
import dev.mvc.dog.DogVO;

import dev.mvc.match_post.MatchPostProcInter;
import dev.mvc.match_post.MatchPostVO;

import dev.mvc.match_review.MatchReviewProcInter;
import dev.mvc.match_review.MatchReviewVO;

@Controller
@RequestMapping("/match_apply")
public class MatchApplyCont {

  @Autowired
  @Qualifier("dev.mvc.match_apply.MatchApplyProc")
  private MatchApplyProcInter matchApplyProc;

  @Autowired
  @Qualifier("dev.mvc.dog.DogProc")
  private DogProcInter dogProc;

  @Autowired
  @Qualifier("dev.mvc.match_review.MatchReviewProc")
  private MatchReviewProcInter matchReviewProc;

  @Autowired
  @Qualifier("dev.mvc.match_post.MatchPostProc")
  private MatchPostProcInter matchPostProc;

  public MatchApplyCont() {
    System.out.println("-> MatchApplyCont created.");
  }

  /**
   * =========================================================
   * 매칭 신청
   * =========================================================
   */
  @PostMapping(
      value = "/create",
      produces = "application/json;charset=UTF-8"
  )
  @ResponseBody
  public String create(
      HttpSession session,
      MatchApplyVO matchApplyVO
  ) {

    JSONObject json = new JSONObject();

    // =====================================================
    // 로그인 확인
    // =====================================================
    Integer memberno =
        (Integer) session.getAttribute("memberno");

    if (memberno == null) {

      json.put("res", "login_required");

      return json.toString();
    }

    // 신청 회원 번호 설정
    matchApplyVO.setMemberno(memberno);

    // =====================================================
    // 게시글 조회
    // =====================================================
    MatchPostVO postVO =
        this.matchPostProc.read(matchApplyVO.getMatchno());

    if (postVO == null) {

      json.put("res", "not_found");

      return json.toString();
    }

    // =====================================================
    // 본인 글 신청 방지
    // =====================================================
    if (postVO.getMemberno() == memberno) {

      json.put("res", "self_apply");

      return json.toString();
    }

    // =====================================================
    // 모집 완료 상태 확인
    // =====================================================
    if ("마감".equals(postVO.getStatus())) {

      json.put("res", "closed");

      return json.toString();
    }

    // =====================================================
    // 선택 강아지 확인
    // =====================================================
    DogVO dogVO =
        this.dogProc.read(matchApplyVO.getDogno());

    if (dogVO == null) {

      json.put("res", "dog_not_found");

      return json.toString();
    }

    // =====================================================
    // DMTI 저장
    // =====================================================
    matchApplyVO.setDmti(dogVO.getDbti_type());

    // =====================================================
    // 신청 등록
    // =====================================================
    int cnt =
        this.matchApplyProc.create(matchApplyVO);

    if (cnt == 1) {

      json.put("res", "success");

    } else if (cnt == -1) {

      json.put("res", "duplicate");

    } else {

      json.put("res", "fail");

    }

    return json.toString();
  }

  /**
   * =========================================================
   * 내 신청 / 받은 신청 / 내 모집글 / 리뷰 목록
   * =========================================================
   */
  @GetMapping("/list_by_member")
  public String list_by_member(
      HttpSession session,
      Model model
  ) {

    // =====================================================
    // 로그인 회원 번호 조회
    // =====================================================
    Integer memberno =
        (Integer) session.getAttribute("memberno");

    // =====================================================
    // 로그인 안된 경우
    // =====================================================
    if (memberno == null) {

      return "redirect:/member/login";

    }

    // =====================================================
    // 내가 신청한 목록
    // =====================================================
    ArrayList<MatchApplyVO> list =
        this.matchApplyProc.list_by_memberno(memberno);

    model.addAttribute(
        "list",
        list
    );

    // =====================================================
    // 내가 받은 신청 목록
    // =====================================================
    ArrayList<MatchApplyVO> receivedList =
        this.matchApplyProc.list_by_author(memberno);

    model.addAttribute(
        "receivedList",
        receivedList
    );

    // =====================================================
    // 종료된 산책 통합 목록
    // =====================================================
    ArrayList<MatchApplyVO> completedList =
        this.matchApplyProc.completedList(memberno);

    model.addAttribute(
        "completedList",
        completedList
    );

    // =====================================================
    // 내가 작성한 모집글
    // =====================================================
    ArrayList<MatchPostVO> myPosts =
        this.matchPostProc.list_by_memberno(memberno);

    model.addAttribute(
        "myPosts",
        myPosts
    );

    // =====================================================
    // 내가 작성한 리뷰 목록
    // =====================================================
    ArrayList<MatchReviewVO> reviewList =
        this.matchReviewProc.list_all_my_reviews(memberno);

    model.addAttribute(
        "reviewList",
        reviewList
    );

    // =====================================================
    // 페이지 이동
    // =====================================================
    return "match_apply/list_by_member";

  }

  /**
   * =========================================================
   * 게시글별 신청 목록
   * =========================================================
   */
  @GetMapping("/list_by_match")
  public String list_by_match(
      int matchno,
      Model model
  ) {

    ArrayList<MatchApplyVO> list =
        this.matchApplyProc.list_by_matchno(matchno);

    model.addAttribute("list", list);
    model.addAttribute("matchno", matchno);

    return "match_apply/list_by_match";
  }

  /**
   * =========================================================
   * 신청 상태 변경
   * =========================================================
   */
  @PostMapping(
      value = "/update_status",
      produces = "application/json;charset=UTF-8"
  )
  @ResponseBody
  public String update_status(
      HttpSession session,
      int applyno,
      int apply_status
  ) {

    JSONObject json = new JSONObject();

    try {

      // ===================================================
      // 로그인 확인
      // ===================================================
      Integer memberno =
          (Integer) session.getAttribute("memberno");

      if (memberno == null) {

        json.put("res", "login_required");

        return json.toString();
      }

      // ===================================================
      // 신청 조회
      // ===================================================
      MatchApplyVO applyVO =
          this.matchApplyProc.read(applyno);

      if (applyVO == null) {

        json.put("res", "not_found");

        return json.toString();
      }
      
	   // ===================================================
	   // 게시글 조회
	   // ===================================================
	   MatchPostVO postVO =
	       this.matchPostProc.read(applyVO.getMatchno());
	
	   // ===================================================
	   // 권한 체크
	   // ===================================================
	   if(postVO.getMemberno() != memberno){
	
	     json.put("res", "forbidden");
	
	     return json.toString();
	
	   }
      // ===================================================
      // 수락
      // ===================================================
      if (apply_status == 2) {

        this.matchApplyProc.acceptMatch(applyno);

        json.put("res", "success");
        json.put("msg", "매칭 수락 완료");

      }   
      // 3 = 산책중
      else if (apply_status == 3) {

        int cnt =
            this.matchApplyProc.startWalk(applyno);

        json.put("res", cnt == 1 ? "success" : "fail");

      }

      // 4 = 산책완료
      else if (apply_status == 4) {

        int cnt =
            this.matchApplyProc.completeMatch(applyno);

        json.put("res", cnt == 1 ? "success" : "fail");

      }

      else {

        json.put("res", "invalid_status");

      }

    } catch (Exception e) {

      e.printStackTrace();

      json.put("res", "fail");
      json.put("msg", e.getMessage());

    }

    return json.toString();
  }
  /**
   * =========================================================
   * 매칭 취소
   * =========================================================
   */
  @PostMapping(
      value = "/cancel",
      produces = "application/json;charset=UTF-8"
  )
  @ResponseBody
  public String cancel(
      HttpSession session,
      MatchApplyVO matchApplyVO
  ) {

    JSONObject json = new JSONObject();

    try {

      // ===================================================
      // 로그인 확인
      // ===================================================
      Integer memberno =
          (Integer) session.getAttribute("memberno");

      if (memberno == null) {

        json.put("res", "login_required");

        return json.toString();

      }

      // ===================================================
      // 신청 조회
      // ===================================================
      MatchApplyVO applyVO =
          this.matchApplyProc.read(
              matchApplyVO.getApplyno()
          );

      if (applyVO == null) {

        json.put("res", "not_found");

        return json.toString();

      }

      // ===================================================
      // 게시글 조회
      // ===================================================
      MatchPostVO postVO =
          this.matchPostProc.read(
              applyVO.getMatchno()
          );

      // ===================================================
      // 권한 확인
      // 신청자 OR 방장만 가능
      // ===================================================
      if (
          applyVO.getMemberno() != memberno
          &&
          postVO.getMemberno() != memberno
      ) {

        json.put("res", "forbidden");

        return json.toString();

      }

   // ===================================================
   // 취소한 회원 번호 저장
   // ===================================================
   matchApplyVO.setCancel_by(memberno);

   // ===================================================
   // 취소 처리
   // ===================================================
   int cnt =
       this.matchApplyProc.cancelMatch(
           matchApplyVO
       );

   json.put(
       "res",
       cnt == 1 ? "success" : "fail"
   );

    } catch (Exception e) {

      e.printStackTrace();

      json.put("res", "fail");

    }

    return json.toString();

  }

}