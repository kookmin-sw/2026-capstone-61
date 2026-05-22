package dev.mvc.market_order_item;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class MarketOrderItemVO {
  /** 주문 상세 번호 */
  private int order_item_id;
  
  /** 부모 주문 번호 (MARKET_ORDER 외래키) */
  private int order_id;
  
  /** 상품 번호 (MARKET 외래키) */
  private int product_id;
  
  /** 주문 수량 */
  private int quantity;
  
  /** 주문 당시 단가 (나중에 상품 가격이 변해도 주문 기록은 유지되어야 함) */
  private int price;
  
  /** 합계 금액 (quantity * price) */
  private int total_price;

  // --- JOIN을 통해 가져올 추가 정보 (주문 내역 화면용) ---
  /** 상품명 */
  private String product_name;
  /** 메인 이미지 파일명 */
  private String file_name;
}