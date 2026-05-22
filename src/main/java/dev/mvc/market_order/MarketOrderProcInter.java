package dev.mvc.market_order;

import java.util.List;
import java.util.Map;
import dev.mvc.market_cart.MarketCartVO;
import dev.mvc.market_order_item.MarketOrderItemVO;

public interface MarketOrderProcInter {
  
  /**
   * 주문 등록 및 배송지 정보 처리 (마스터 + 상세 + 장바구니 비우기)
   * @param marketOrderVO
   * @return 처리 결과 (1: 성공, 0: 실패)
   */
  public int create(MarketOrderVO marketOrderVO);

  /**
   * 주문 정보 단건 조회 (마스터 정보)
   * @param order_id
   * @return MarketOrderVO
   */
  public MarketOrderVO read(int order_id);

  /**
   * 장바구니 선택 항목 목록 조회 (주문서 작성 폼 구성용)
   * @param cart_ids "1,2,3" 형태의 문자열
   * @return 장바구니 상품 목록
   */
  public List<MarketCartVO> list_by_cart_ids(String cart_ids);

  /**
   * 회원의 전체 주문 목록 조회
   * @param memberno
   * @return 주문 목록
   */
  public List<MarketOrderVO> list_by_memberno(int memberno);

  /**
   * 특정 주문에 포함된 상품 상세 품목 조회 (상세보기용)
   * @param order_id
   * @return 상세 품목 리스트
   */
  public List<MarketOrderItemVO> list_by_order_id(int order_id);

  /**
   * 선택된 장바구니 항목들 삭제 (주문 완료 후 비우기용)
   * @param cart_ids "1,2,3" 형태의 문자열
   * @return 삭제된 레코드 수
   */
  public int delete_selected(String cart_ids);

  /**
   * 주문 정보 삭제 (상세 내역 먼저 삭제 후 마스터 삭제)
   * @param order_id
   * @return 삭제된 레코드 수
   */
  public int delete(int order_id);
  /**
   * 바로 구매 상품 조회
   * @param product_id 상품 번호
   * @param quantity 수량
   * @return 주문용 상품 리스트
   */
  public List<MarketCartVO> list_direct_order(
          Integer product_id,
          Integer quantity);

  // ==========================================
  // ↓↓↓ 관리자 전용 메서드 추가 ↓↓↓
  // ==========================================

  /**
   * 관리자: 전체 회원의 주문 및 취소 내역 목록 조회
   * @return 전체 주문 목록
   */
  public List<MarketOrderVO> list_all();

  /**
   * 관리자: 주문 상태 변경 (결제완료, 배송중, 취소완료 등)
   * @param map order_id와 order_status를 담은 맵
   * @return 처리 결과
   */
  public int update_status(Map<String, Object> map);

  /**
   * 관리자: 주문 취소(상태변경) 시 재고 복구
   * @param map product_id와 quantity를 담은 맵
   * @return 처리 결과
   */
  public int increase_stock(Map<String, Object> map);
  /**
   * 관리자: 배송지 정보 수정
   * @param marketOrderVO 수정된 배송지 정보를 담은 VO
   * @return 처리 결과
   */
  public int update_address(MarketOrderVO marketOrderVO);
  
}