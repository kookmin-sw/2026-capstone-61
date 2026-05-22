package dev.mvc.market_review;

import java.util.List;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import dev.mvc.tool.Upload;

@Controller
@RequestMapping(value = "/market_review")
public class MarketReviewCont {
  
  @Autowired
  @Qualifier("dev.mvc.market_review.MarketReviewProc")
  private MarketReviewProcInter marketReviewProc;

  public MarketReviewCont() {
    System.out.println("-> MarketReviewCont created.");
  }

  /**
   * 리뷰 작성 폼
   */
  @RequestMapping(value = "/create.do", method = RequestMethod.GET)
  public ModelAndView create(int product_id, int order_item_id) {
    ModelAndView mav = new ModelAndView();
    
    // 중복 리뷰 체크
    if (this.marketReviewProc.check_duplicate(order_item_id) > 0) {
      mav.setViewName("market_review/msg"); // 앞의 / 제거
      mav.addObject("code", "duplicate_review");
    } else {
      mav.setViewName("market_review/create"); // 앞의 / 제거
      mav.addObject("product_id", product_id);
      mav.addObject("order_item_id", order_item_id);
    }
    
    return mav;
  }

  /**
   * 리뷰 등록 처리 (사진 업로드 포함)
   */
  @RequestMapping(value = "/create.do", method = RequestMethod.POST)
  public ModelAndView create(HttpSession session, 
                               MarketReviewVO marketReviewVO, 
                               MultipartFile review_img_file) {
    ModelAndView mav = new ModelAndView();
    
    if (session.getAttribute("memberno") != null) {
      int memberno = (int)session.getAttribute("memberno");
      marketReviewVO.setMemberno(memberno);
      
      // 파일 업로드 처리
      String review_img = ""; 
      String upDir = MarketReview.getUploadDir();

      if (review_img_file != null && !review_img_file.isEmpty()) {
        review_img = Upload.saveFileSpring(review_img_file, upDir);
        marketReviewVO.setReview_img(review_img);
      }
      
      // 중복 등록 방지 2차 체크
      if (this.marketReviewProc.check_duplicate(marketReviewVO.getOrder_item_id()) > 0) {
        mav.addObject("code", "duplicate_review");
        mav.setViewName("market_review/msg"); // 앞의 / 제거
        return mav;
      }
      
      int cnt = this.marketReviewProc.create(marketReviewVO);
      
      if (cnt == 1) {
        // 성공 시 상품 상세 페이지로 이동 (Redirect는 / 유지)
        mav.setViewName("redirect:/market/read.do?productId=" + marketReviewVO.getProduct_id());
      } else {
        mav.addObject("code", "create_fail");
        mav.setViewName("market_review/msg"); // 앞의 / 제거
      }
    } else {
      mav.setViewName("redirect:/member/login");
    }
    
    return mav;
  }

  /**
   * 리뷰 전체보기 페이지 (list.html 화면 반환)
   */
  @RequestMapping(value = "/list_by_product_all.do", method = RequestMethod.GET)
  public ModelAndView list_by_product_all(int product_id) {
    ModelAndView mav = new ModelAndView();
    
    mav.setViewName("market_review/list"); // 앞의 / 제거
    mav.addObject("product_id", product_id);
    
    return mav;
  }

  /**
   * 상품별 리뷰 목록 (AJAX용 JSON 반환)
   */
  @ResponseBody 
  @RequestMapping(value = "/list_by_product.do", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
  public List<MarketReviewVO> list_by_product(int product_id) {
      return this.marketReviewProc.list_by_product(product_id); 
  }

  /**
   * 개별 리뷰 상세 보기 페이지 (read.html)
   */
  @RequestMapping(value = "/read.do", method = RequestMethod.GET)
  public ModelAndView read(int review_id, HttpSession session) {
      ModelAndView mav = new ModelAndView();
      
      MarketReviewVO marketReviewVO = this.marketReviewProc.read(review_id);
      
      if (marketReviewVO == null) {
        mav.addObject("code", "not_found");
        mav.setViewName("market_review/msg"); // 앞의 / 제거
        return mav;
      }

      // 작성자 아이디가 유실된 경우를 대비한 세션 보정 로직 (선택사항)
      if (marketReviewVO.getId() == null || marketReviewVO.getId().equals("")) {
          String session_id = (String)session.getAttribute("id");
          if (session_id != null) {
              marketReviewVO.setId(session_id);
          }
      }
      
      mav.addObject("marketReviewVO", marketReviewVO);
      mav.setViewName("market_review/read"); // 앞의 / 제거
      
      return mav;
  }

  /**
   * 리뷰 삭제
   */
  @RequestMapping(value = "/delete.do", method = RequestMethod.POST)
  public ModelAndView delete(int review_id, int product_id) {
    ModelAndView mav = new ModelAndView();
    
    this.marketReviewProc.delete(review_id);
    
    // 삭제 후 상품 상세 페이지로 복귀 (Redirect는 / 유지)
    mav.setViewName("redirect:/market/read.do?productId=" + product_id);
    return mav;
  }
}