package dev.mvc.mypage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MypageController {

  // =========================================================
  // 🐾 마이페이지 Proc
  // =========================================================
  
  @Autowired
  private MypageProcInter mypageProc;

  // =========================================================
  // 🐾 마이페이지 메인
  // =========================================================
  
  @GetMapping("/mypage")
  public String mypage(
      @RequestParam("memberno") int memberno,
      Model model) {

    // =========================================================
    // 👤 회원 기본 정보
    // =========================================================
    
    model.addAttribute(
        "member",
        mypageProc.readMember(memberno));

    // =========================================================
    // 📊 마이페이지 요약 통계
    // =========================================================
    
    model.addAttribute(
        "summary",
        mypageProc.mypageSummary(memberno));

    // =========================================================
    // 🐶 반려견 목록
    // =========================================================
    
    model.addAttribute(
        "dogs",
        mypageProc.listDog(memberno));

    // =========================================================
    // 📝 내가 작성한 게시글
    // =========================================================
    
    model.addAttribute(
        "posts",
        mypageProc.listCommunity(memberno));

    // =========================================================
    // 💬 내가 작성한 댓글
    // =========================================================
    
    model.addAttribute(
        "comments",
        mypageProc.listComments(memberno));

    // =========================================================
    // 🛒 주문 내역
    // =========================================================
    
    model.addAttribute(
        "orders",
        mypageProc.listOrder(memberno));

    // =========================================================
    // 🧺 장바구니
    // =========================================================
    
    model.addAttribute(
        "carts",
        mypageProc.listCart(memberno));

    // =========================================================
    // 🔐 로그인 내역
    // =========================================================
    
    model.addAttribute(
        "logins",
        mypageProc.listLoginHistory(memberno));

    // =========================================================
    // ⭐ 리뷰 내역 (추가 추천)
    // =========================================================
    
//    model.addAttribute(
//        "reviews",
//        mypageProc.listReview(memberno));

    // =========================================================
    // 💬 채팅방 내역 (추가 추천)
    // =========================================================
    
//    model.addAttribute(
//        "chats",
//        mypageProc.listChat(memberno));

    // =========================================================
    // 🐾 매칭 내역 (추가 추천)
    // =========================================================
    
//    model.addAttribute(
//        "matches",
//        mypageProc.listMatch(memberno));

    // =========================================================
    // 📄 마이페이지 이동
    // =========================================================
    
    return "mypage/mypage";
  }

  // =========================================================
  // 🐶 반려견 삭제
  // =========================================================
  
  @PostMapping("/mypage/dog/delete.do")
  public String deleteDog(
      @RequestParam("dogno") int dogno,
      @RequestParam("memberno") int memberno) {

    // =========================================================
    // 🗑 반려견 삭제
    // =========================================================
    
    this.mypageProc.deleteDog(dogno);

    // =========================================================
    // ↩ 삭제 후 반려견 탭 유지
    // =========================================================
    
    return "redirect:/mypage?memberno="
        + memberno
        + "#section-dog";
  }

  // =========================================================
  // 💬 댓글 삭제
  // =========================================================
  
  @PostMapping("/mypage/comment/delete.do")
  public String deleteComment(
      @RequestParam("comment_no") int commentNo,
      @RequestParam("memberno") int memberno) {

    // =========================================================
    // 🗑 댓글 삭제
    // =========================================================
    
    this.mypageProc.deleteComment(commentNo);

    // =========================================================
    // ↩ 댓글 탭 유지
    // =========================================================
    
    return "redirect:/mypage?memberno="
        + memberno
        + "#section-comment";
  }

}