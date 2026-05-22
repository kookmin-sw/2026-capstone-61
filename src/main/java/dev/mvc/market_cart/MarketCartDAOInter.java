package dev.mvc.market_cart;

import java.util.List;

public interface MarketCartDAOInter {
  /** 등록 (MERGE INTO 권장) */
  public int create(MarketCartVO marketCartVO);
  
  /** 회원별 목록 (Join 포함) */
  public List<MarketCartVO> list_by_member(String member_id);
  
  /** 수량 수정 */
  public int update_quantity(MarketCartVO marketCartVO);
  /** 상품 번호와 회원 아이디로 수량 업데이트 (상세화면 AJAX용) */
  public int update_quantity_by_product(MarketCartVO marketCartVO);
  /** 장바구니 안에서 삭제 */
  public int delete(int cart_id);
  /** 상품 번호와 회원 아이디로 장바구니 항목 삭제 (상세화면 AJAX용) */
  public int delete_by_product(MarketCartVO marketCartVO);
  /**
   * 선택된 장바구니 항목 목록 조회 (주문서 작성용)
   * @param cart_ids "1,2,5" 형태의 문자열
   * @return 장바구니 상품 목록
   */
  public List<MarketCartVO> list_by_cart_ids(String cart_ids);

  public int delete_selected(String cart_ids);
}