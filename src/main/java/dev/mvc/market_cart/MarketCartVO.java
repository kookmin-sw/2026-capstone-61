package dev.mvc.market_cart;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class MarketCartVO {
  /** 장바구니 번호 */
  private int cart_id;
  /** 회원 번호(아이디) */
  private String member_id;
  /** 상품 번호 */
  private int product_id;
  /** 수량 */
  private int quantity;
  /** 등록일 */
  private String reg_date;

  // JOIN을 통해 가져올 상품 정보
  /** 상품명 */
  private String product_name;
  /** 판매가 */
  private int price;
  /** 배송비 */
  private int delivery_fee;
  /** 메인 이미지 파일명 */
  private String file_name;
  /** 합계 금액 (수량 * 가격) */
  private int total_price;
  private int stock;
}