package dev.mvc.market_cart;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("dev.mvc.market_cart.MarketCartProc")
public class MarketCartProc implements MarketCartProcInter {
  
  @Autowired
  private MarketCartDAOInter marketCartDAO;

  @Override
  public int create(MarketCartVO marketCartVO) {
    return this.marketCartDAO.create(marketCartVO);
  }

  @Override
  public List<MarketCartVO> list_by_member(String member_id) {
    return this.marketCartDAO.list_by_member(member_id);
  }

  @Override
  public int update_quantity(MarketCartVO marketCartVO) {
    return this.marketCartDAO.update_quantity(marketCartVO);
  }
  @Override
  public int update_quantity_by_product(MarketCartVO marketCartVO) {
      return this.marketCartDAO.update_quantity_by_product(marketCartVO);
  }
  @Override
  public int delete(int cart_id) {
    return this.marketCartDAO.delete(cart_id);
  }
  @Override
  public int delete_by_product(MarketCartVO marketCartVO) {
      // DAO에 삭제를 요청하고 삭제된 레코드 갯수를 반환받음
      int cnt = this.marketCartDAO.delete_by_product(marketCartVO);
      return cnt;
  }
}