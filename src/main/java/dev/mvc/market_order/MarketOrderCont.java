package dev.mvc.market_order;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;

import jakarta.servlet.http.HttpSession; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import dev.mvc.market_cart.MarketCartVO;
import dev.mvc.market_order_item.MarketOrderItemVO;

@Controller
@RequestMapping("/market_order")
public class MarketOrderCont {

  @Autowired
  @Qualifier("dev.mvc.market_order.MarketOrderProc")
  private MarketOrderProcInter marketOrderProc;

  public MarketOrderCont() {
    System.out.println("-> MarketOrderCont created.");
  }

  /**
   * [사용자 기능] 주문서 작성 페이지로 이동
   */
  @RequestMapping(value = "/create.do",
          method = RequestMethod.GET)
public ModelAndView create(
  HttpSession session,
  String cart_ids,

  @RequestParam(value = "product_id",
                required = false)
  Integer product_id,

  @RequestParam(value = "quantity",
                required = false)
  Integer quantity) {

ModelAndView mav = new ModelAndView();

Integer memberno =
  (Integer) session.getAttribute("memberno");

if (memberno != null) {

  List<MarketCartVO> list = null;

  // 장바구니 주문
  if (cart_ids != null &&
      !cart_ids.isEmpty()) {

      list = this.marketOrderProc
              .list_by_cart_ids(cart_ids);
  }

  // 바로 구매
  else if (product_id != null) {

      list = this.marketOrderProc
              .list_direct_order(
                  product_id,
                  quantity
              );
  }

  int total_price = 0;

  if (list != null) {
      for (MarketCartVO vo : list) {
          total_price += vo.getTotal_price();
      }
  }

  mav.addObject("list", list);
  mav.addObject("total_price", total_price);

  mav.setViewName("market_order/create");

} else {
  mav.setViewName("redirect:/member/login");
}

return mav;
}
  /**
   * 실제 주문 처리
   */
  @RequestMapping(value = "/create.do", method = RequestMethod.POST)
  public ModelAndView create_proc(HttpSession session, MarketOrderVO marketOrderVO) {
      ModelAndView mav = new ModelAndView();
      if (session.getAttribute("id") != null) {
          marketOrderVO.setMemberno((int) session.getAttribute("memberno"));
          marketOrderVO.setUser_id((String) session.getAttribute("id"));
          int cnt = this.marketOrderProc.create(marketOrderVO);
          if (cnt == 1) {
              mav.setViewName("redirect:/market_order/success.do?order_id=" + marketOrderVO.getOrder_id());
          } else {
              mav.addObject("code", "create_fail");
              mav.setViewName("market_order/msg"); // 앞의 / 제거
          }
      } else {
          mav.setViewName("redirect:/member/login");
      }
      return mav;
  }

  /**
   * 주문 완료 페이지
   */
  @RequestMapping(value = "/success.do", method = RequestMethod.GET)
  public ModelAndView success(int order_id) {
    ModelAndView mav = new ModelAndView("market_order/success"); // 생성자 내부 / 제거
    MarketOrderVO marketOrderVO = this.marketOrderProc.read(order_id);
    mav.addObject("marketOrderVO", marketOrderVO);
    return mav;
  }

  /**
   * [사용자 기능] 실제 주문 취소 요청 처리
   */
  @RequestMapping(value = "/cancel_proc.do", method = RequestMethod.POST)
  public ModelAndView cancel_proc(HttpSession session, int order_id, String reason, String reason_detail) {
    ModelAndView mav = new ModelAndView();

    if (session.getAttribute("memberno") != null) {
        Map<String, Object> map = new HashMap<>();
        map.put("order_id", order_id);
        map.put("order_status", "CANCEL_REQC"); 
        map.put("reason", reason);
        map.put("reason_detail", reason_detail);

        int cnt = this.marketOrderProc.update_status(map);

        if (cnt == 1) {
          mav.setViewName("market_order/cancel_success"); // 앞의 / 제거
        } else {
          mav.addObject("code", "cancel_fail");
          mav.setViewName("market_order/msg"); // 앞의 / 제거
        }
    } else {
      mav.setViewName("redirect:/member/login");
    }
    return mav;
  }

  /**
   * [사용자 기능] 로그인한 회원의 주문 내역 목록
   */
  @RequestMapping(value = "/list_by_memberno.do", method = RequestMethod.GET)
  public ModelAndView list_by_memberno(
          HttpSession session,
          @RequestParam(value = "status", required = false) String status) {
      
      ModelAndView mav = new ModelAndView();
      Integer memberno = (Integer) session.getAttribute("memberno");

      if (memberno != null) {
          List<MarketOrderVO> list = this.marketOrderProc.list_by_memberno(memberno);
          
          if (status != null && !status.isEmpty()) {
              String paramStatus = status.trim().toUpperCase();
              
              list = list.stream()
                         .filter(vo -> {
                             String dbStatus = (vo.getOrder_status() != null) ? 
                                               vo.getOrder_status().trim().toUpperCase() : "";
                             
                             if (paramStatus.equals("CANCEL")) {
                                 return dbStatus.contains("CANCEL");
                             }
                             if (paramStatus.equals("RETURN")) {
                                 return dbStatus.contains("RETURN");
                             }
                             if (paramStatus.equals("DELIVERED")) {
                                 return dbStatus.equals("DELIVERED");
                             }
                             return dbStatus.equals(paramStatus);
                         })
                         .collect(Collectors.toList());
          }
          
          mav.addObject("list", list);
          mav.setViewName("market_order/list_by_memberno"); // 앞의 / 제거
      } else {
          mav.setViewName("redirect:/member/login");
      }
      return mav;
  }

  /**
   * [사용자 기능] 일반 회원의 주문 상세 조회
   */
  @RequestMapping(value = "/read.do", method = RequestMethod.GET)
  public ModelAndView read(HttpSession session, int order_id) {
    ModelAndView mav = new ModelAndView();
    Integer memberno = (Integer) session.getAttribute("memberno");

    if (memberno != null) {
      MarketOrderVO orderVO = this.marketOrderProc.read(order_id);
      String grade = (String) session.getAttribute("grade");
      
      if (!"admin".equals(grade) && (orderVO == null || orderVO.getMemberno() != memberno)) {
        mav.setViewName("redirect:/market_order/list_by_memberno.do");
        return mav;
      }
      
      List<MarketOrderItemVO> item_list = this.marketOrderProc.list_by_order_id(order_id);
      mav.addObject("orderVO", orderVO);
      mav.addObject("item_list", item_list);
      mav.setViewName("market_order/read"); // 앞의 / 제거
    } else {
      mav.setViewName("redirect:/member/login");
    }
    return mav;
  }

  /**
   * [사용자 기능] 취소 사유 입력 페이지 호출
   */
  @RequestMapping(value = "/cancel.do", method = RequestMethod.GET)
  public ModelAndView cancel(HttpSession session, int order_id) {
    ModelAndView mav = new ModelAndView();
    if (session.getAttribute("memberno") != null) {
      MarketOrderVO orderVO = this.marketOrderProc.read(order_id);
      mav.addObject("orderVO", orderVO);
      mav.setViewName("market_order/cancel"); // 앞의 / 제거
    } else {
      mav.setViewName("redirect:/member/login");
    }
    return mav;
  }

  /**
   * [사용자 기능] 반품 신청 페이지 호출
   */
  @RequestMapping(value = "/return.do", method = RequestMethod.GET)
  public ModelAndView return_form(HttpSession session, int order_id) {
    ModelAndView mav = new ModelAndView();
    Integer memberno = (Integer) session.getAttribute("memberno");

    if (memberno != null) {
      MarketOrderVO orderVO = this.marketOrderProc.read(order_id);
      
      if (orderVO != null && orderVO.getMemberno() == memberno.intValue()) {
        mav.addObject("orderVO", orderVO);
        mav.setViewName("market_order/return"); // 앞의 / 제거
      } else {
        mav.setViewName("redirect:/market_order/list_by_memberno");
      }
    } else {
      mav.setViewName("redirect:/member/login");
    }
    return mav;
  }

  /**
   * [사용자 기능] 실제 반품 신청 처리
   */
  @RequestMapping(value = "/return_proc.do", method = RequestMethod.POST)
  public ModelAndView return_proc(HttpSession session, int order_id, String reason, String reason_detail) {
    ModelAndView mav = new ModelAndView();

    if (session.getAttribute("memberno") != null) {
        Map<String, Object> map = new HashMap<>();
        map.put("order_id", order_id);
        map.put("order_status", "RETURN_REQC"); 
        map.put("reason", reason);
        map.put("reason_detail", reason_detail);

        int cnt = this.marketOrderProc.update_status(map);

        if (cnt == 1) {
          mav.setViewName("market_order/return_success"); // 앞의 / 제거
        } else {
          mav.addObject("code", "return_fail");
          mav.setViewName("market_order/msg"); // 앞의 / 제거
        }
    } else {
      mav.setViewName("redirect:/member/login");
    }
    return mav;
  }

  /**
   * [관리자 기능] 전체 주문 내역 목록
   */
  @RequestMapping(value = "/admin_list.do", method = RequestMethod.GET)
  public ModelAndView admin_list(HttpSession session) {
    ModelAndView mav = new ModelAndView();
    String grade = (String) session.getAttribute("grade");
    if ("admin".equals(grade)) {
      List<MarketOrderVO> list = this.marketOrderProc.list_all(); 
      mav.addObject("orderList", list);
      mav.setViewName("market_order/admin_list"); // 앞의 / 제거
    } else {
      mav.setViewName("redirect:/member/login");
    }
    return mav;
  }

  /**
   * [관리자 기능] 주문 상태 업데이트 (AJAX)
   */
  @RequestMapping(value = "/update_status.do", method = RequestMethod.POST)
  @ResponseBody
  public String update_status(@RequestBody Map<String, Object> map, HttpSession session) {
      String grade = (String) session.getAttribute("grade");
      if (grade == null || !grade.equals("admin")) {
          return "{\"res\":\"no_permission\"}";
      }

      String status = (String)map.get("order_status");
      if("CANCEL".equals(status)) {
          map.put("order_status", "CANCEL_DONE");
      }

      int cnt = this.marketOrderProc.update_status(map); 
      return (cnt > 0) ? "{\"res\":\"success\"}" : "{\"res\":\"fail\"}";
  }
  
  /**
   * [관리자 기능] 배송지 정보 수정 처리 (AJAX)
   */
  @PostMapping("/update_address.do")
  @ResponseBody
  public String update_address(@RequestBody MarketOrderVO marketOrderVO, HttpSession session) {
      String grade = (String) session.getAttribute("grade");
      
      if (grade == null || !grade.equals("admin")) {
          return "{\"res\":\"no_permission\"}";
      }

      int cnt = this.marketOrderProc.update_address(marketOrderVO); 
      
      return (cnt > 0) ? "{\"res\":\"success\"}" : "{\"res\":\"fail\"}";
  }

  /**
   * [관리자 기능] 관리자용 주문 상세 조회
   */
  @RequestMapping(value = "/read_admin.do", method = RequestMethod.GET)
  public ModelAndView read_admin(HttpSession session, @RequestParam("order_id") int order_id) {
    ModelAndView mav = new ModelAndView();
    String grade = (String) session.getAttribute("grade");

    if ("admin".equals(grade)) {
      MarketOrderVO orderVO = this.marketOrderProc.read(order_id);
      List<MarketOrderItemVO> item_list = this.marketOrderProc.list_by_order_id(order_id);
      
      mav.addObject("orderVO", orderVO);
      mav.addObject("item_list", item_list);
      mav.setViewName("market_order/read_admin"); // 앞의 / 제거
    } else {
      mav.setViewName("redirect:/member/login");
    }
    return mav;
  }
}