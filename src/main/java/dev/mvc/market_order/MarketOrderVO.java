package dev.mvc.market_order;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class MarketOrderVO {
  /** 주문 고유 번호 (PK, 시퀀스 사용) */
  private int order_id;
  
  /** 회원 번호 (MEMBER 테이블 참조 FK) */
  private int memberno;
  
  /** 주문자 아이디 (참조 및 조회용) */
  private String user_id = "";
  
  /** 최종 결제 금액 (상품 합계 + 배송비) */
  private int total_price;
  
  /** 결제 수단 (CARD: 신용카드, BANK: 무통장, PAY: 간편결제) */
  private String payment_method = "";
  
  /** 주문 상태 (PAID: 결제완료, SHIPPED: 배송중, CANCEL: 취소 등) */
  private String order_status = "";
  
  /** 수령인 성명 (MEMBER.MNAME 규격) */
  private String receiver_name = "";
  
  /** 수령인 연락처 (MEMBER.TEL 규격) */
  private String receiver_tel = "";
  
  /** 우편번호 (5자리) */
  private String zipcode = "";
  
  /** 기본 주소 */
  private String address1 = "";
  
  /** 상세 주소 */
  private String address2 = "";
  
  /** 주문 일시 */
  private String order_date = "";

  // ----------------------------------------------------------------------
  // 비즈니스 로직용 추가 필드 (DB 테이블에는 없는 컬럼)
  // ----------------------------------------------------------------------
  
  private String update_address = "N";
  private String cart_ids = "";

  /** ★ 추가: 리뷰 작성을 위한 상품 고유 번호 */
  private int product_id;

  /** ★ 추가: 리뷰 중복 체크 및 구매 인증을 위한 주문 상세 번호 */
  private int order_item_id;

  /** 대표 상품명 (조인용) */
  private String product_name;

  /** 대표 이미지 (조인용) */
  private String file_name;

  /** 해당 주문에 포함된 총 상품 종류 수 */
  private int order_count;

  /** 취소 사유 카테고리 (단순변심, 배송지연 등) */
  private String reason = "";

  /** 상세 취소 사유 내용 */
  private String reason_detail = "";
}