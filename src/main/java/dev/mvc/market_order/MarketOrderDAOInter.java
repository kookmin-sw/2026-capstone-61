package dev.mvc.market_order;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import dev.mvc.market_cart.MarketCartVO;
import dev.mvc.market_order_item.MarketOrderItemVO;

public interface MarketOrderDAOInter {
  /** 주문 마스터 등록 (selectKey 사용) */
  public int create(MarketOrderVO marketOrderVO);
  
  /** 주문 상세 품목 등록 */
  public int create_item(MarketOrderItemVO marketOrderItemVO);

  /** 재고 차감 (구매 시) */
  public int decrease_stock(Map<String, Object> map);

  /** 회원의 주문 목록 조회 */
  public List<MarketOrderVO> list_by_memberno(int memberno);

  /** 회원 테이블 배송지 업데이트 */
  public int update_member_address(MarketOrderVO marketOrderVO);

  /** 주문 상세 정보 단건 조회 (REASON, REASON_DETAIL 포함) */
  public MarketOrderVO read(int order_id);

  /** 주문 완료 후 장바구니 비우기 */
  public int delete_selected(String cart_ids);
 
  /**
   * 바로 구매 상품 조회
   */
  public List<MarketCartVO> list_direct_order(
          @Param("product_id") Integer product_id,
          @Param("quantity") Integer quantity);
  /** 주문 정보 완전 삭제 (관리자용 또는 테스트용) */
  public int delete(int order_id);

  // ==========================================
  // ↓↓↓ 관리자 및 취소 프로세스용 메서드 ↓↓↓
  // ==========================================

  /**
   * 관리자 전용: 모든 회원의 전체 주문 목록 조회
   * @return 전체 주문 리스트
   */
  public List<MarketOrderVO> list_all();

  /**
   * 주문 상태 및 취소 사유 업데이트
   * - map 구성: order_id, order_status, reason(선택), reason_detail(선택)
   * @param map 
   * @return 처리 결과
   */
  public int update_status(Map<String, Object> map);
  /**
   * 주문 테이블 배송지 정보 업데이트 (관리자용)
   * @param marketOrderVO 수정된 배송지 정보를 담은 VO
   * @return 수정된 레코드 수
   */
  public int update_address(MarketOrderVO marketOrderVO);
  /**
   * 재고 복구 (취소 확정 시)
   * - map 구성: product_id, quantity
   * @param map
   * @return 처리 결과
   */
  public int increase_stock(Map<String, Object> map);
}