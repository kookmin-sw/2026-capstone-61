package dev.mvc.market_order_item;

import java.util.List;

public interface MarketOrderItemDAOInter {
  /**
   * 주문 상세 내역 등록 (주문 시 각 상품 하나하나 저장)
   * @param marketOrderItemVO
   * @return 등록된 레코드 수
   */
  public int create(MarketOrderItemVO marketOrderItemVO);

  /**
   * 특정 주문(order_id)에 포함된 상품 상세 목록 조회
   * @param order_id
   * @return 상세 품목 리스트
   */
  public List<MarketOrderItemVO> list_by_order_id(int order_id);
  /**
   * 특정 주문(order_id)에 속한 모든 상세 품목 삭제 (주문 취소 시 사용)
   * @param order_id 삭제할 주문 번호
   * @return 삭제된 레코드 수
   */
  public int delete_by_order_id(int order_id);
}