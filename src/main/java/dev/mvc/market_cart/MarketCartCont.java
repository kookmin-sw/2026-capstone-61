package dev.mvc.market_cart;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/market_cart")
public class MarketCartCont {
  
  @Autowired
  @Qualifier("dev.mvc.market_cart.MarketCartProc")
  private MarketCartProcInter marketCartProc;

  /**
   * 장바구니 담기 (AJAX용 JSON 응답)
   */
  @PostMapping("/create.do")
  @ResponseBody
  public String create(@RequestBody MarketCartVO marketCartVO) {
    int cnt = this.marketCartProc.create(marketCartVO);
    return (cnt > 0) ? "{\"res\":\"success\"}" : "{\"res\":\"fail\"}";
  }

  /**
   * 나의 장바구니 목록 (View 리턴)
   */
  @GetMapping("/list.do")
  public ModelAndView list(HttpSession session) {
      ModelAndView mav = new ModelAndView();
      
      String member_id = (String)session.getAttribute("id"); 
      
      if (member_id == null) {
          // 리다이렉트는 URL 주소이므로 앞에 / 유지
          mav.setViewName("redirect:/member/login"); 
      } else {
          // HTML 경로 리턴 시 맨 앞의 / 제거
          mav.setViewName("market_cart/list"); 
          List<MarketCartVO> list = this.marketCartProc.list_by_member(member_id);
          mav.addObject("list", list);
      }
      return mav;
  }

  /**
   * 장바구니 페이지용 수량 수정
   */
  @PostMapping("/update_quantity.do")
  @ResponseBody
  public String update_quantity(@RequestBody MarketCartVO marketCartVO) {
    int cnt = this.marketCartProc.update_quantity(marketCartVO);
    return (cnt > 0) ? "success" : "fail";
  }

  /**
   * 상품 상세 페이지용 수량 수정 (AJAX 전용)
   */
  @PostMapping("/update_quantity_ajax.do")
  @ResponseBody
  public String update_quantity_ajax(@RequestBody MarketCartVO marketCartVO) {
    int cnt = this.marketCartProc.update_quantity_by_product(marketCartVO);
    return (cnt > 0) ? "{\"res\":\"success\"}" : "{\"res\":\"fail\"}";
  }

  /**
   * 장바구니 페이지용 삭제 (삭제 후 페이지 새로고침)
   */
  @PostMapping("/delete.do")
  public String delete(int cart_id) {
    this.marketCartProc.delete(cart_id);
    return "redirect:/market_cart/list.do";
  }

  /**
   * 상품 상세 페이지용 삭제 (AJAX 전용, JSON 응답)
   */
  @PostMapping("/delete_ajax.do")
  @ResponseBody
  public String delete_ajax(@RequestBody MarketCartVO marketCartVO) {
    int cnt = this.marketCartProc.delete_by_product(marketCartVO);
    return (cnt > 0) ? "{\"res\":\"success\"}" : "{\"res\":\"fail\"}";
  }
}