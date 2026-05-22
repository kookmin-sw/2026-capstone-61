package dev.mvc.market_review;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("dev.mvc.market_review.MarketReviewProc")
public class MarketReviewProc implements MarketReviewProcInter {
  
  @Autowired
  private MarketReviewDAOInter marketReviewDAO; // DAO 인터페이스 주입

  public MarketReviewProc() {
    System.out.println("-> MarketReviewProc created.");
  }

  @Override
  public int create(MarketReviewVO marketReviewVO) {
    // 여기에 등록 전 비즈니스 로직(예: 별점 유효성 검사 등) 추가 가능
    int cnt = this.marketReviewDAO.create(marketReviewVO);
    return cnt;
  }

  @Override
  public List<MarketReviewVO> list_by_product(int product_id) {
    List<MarketReviewVO> list = this.marketReviewDAO.list_by_product(product_id);
    
    // 작성자 아이디 마스킹 처리 등 가공 로직을 여기서 수행하기도 함
    for (MarketReviewVO vo : list) {
        String id = vo.getId();
        if (id != null && id.length() > 3) {
            vo.setId(id.substring(0, 3) + "****"); // 앞 3글자만 노출
        }
    }
    return list;
  }

  @Override
  public List<MarketReviewVO> list_by_member(int memberno) {
    return this.marketReviewDAO.list_by_member(memberno);
  }

  @Override
  public MarketReviewVO read(int review_id) {
    return this.marketReviewDAO.read(review_id);
  }

  @Override
  public int update(MarketReviewVO marketReviewVO) {
    return this.marketReviewDAO.update(marketReviewVO);
  }

  @Override
  public int delete(int review_id) {
    return this.marketReviewDAO.delete(review_id);
  }

  @Override
  public int check_duplicate(int order_item_id) {
    return this.marketReviewDAO.check_duplicate(order_item_id);
  }
}