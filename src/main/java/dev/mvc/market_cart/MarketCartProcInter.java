package dev.mvc.market_cart;

import java.util.List;

public interface MarketCartProcInter {
  /** 등록 */
  public int create(MarketCartVO marketCartVO);
  
  /** 회원별 목록 */
  public List<MarketCartVO> list_by_member(String member_id);
  
  /** 수량 수정 */
  public int update_quantity(MarketCartVO marketCartVO);
  /** 상품 번호와 회원 아이디로 수량 업데이트 (상세화면 AJAX용) */
  public int update_quantity_by_product(MarketCartVO marketCartVO);
  /** 장바구니 고유번호로 삭제  */
  public int delete(int cart_id);

  /** 상품 번호와 회원 아이디로 장바구니 항목 삭제 (상세화면 AJAX용) */
  public int delete_by_product(MarketCartVO marketCartVO);
}