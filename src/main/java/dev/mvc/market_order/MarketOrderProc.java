package dev.mvc.market_order;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dev.mvc.market_cart.MarketCartDAOInter; 
import dev.mvc.market_cart.MarketCartVO;
import dev.mvc.market_order_item.MarketOrderItemDAOInter;
import dev.mvc.market_order_item.MarketOrderItemVO;

@Service("dev.mvc.market_order.MarketOrderProc")
public class MarketOrderProc implements MarketOrderProcInter {

  @Autowired
  private MarketOrderDAOInter marketOrderDAO; 

  @Autowired
  private MarketOrderItemDAOInter marketOrderItemDAO; 

  @Autowired
  private MarketCartDAOInter marketCartDAO; 

  /**
   * 주문 생성 및 재고 차감 통합 로직
   */
  @Transactional
  @Override
  public int create(MarketOrderVO marketOrderVO) {
    // 1. 주문 마스터 저장
    int cnt = this.marketOrderDAO.create(marketOrderVO);
    
    if (cnt == 1) {
      // 2. 장바구니 리스트 획득
      List<MarketCartVO> cartList = this.marketCartDAO.list_by_cart_ids(marketOrderVO.getCart_ids());

      // 3. 상세 저장 및 재고 차감
      if (cartList != null) {
        for (MarketCartVO cartVO : cartList) {
          MarketOrderItemVO itemVO = new MarketOrderItemVO();
          itemVO.setOrder_id(marketOrderVO.getOrder_id());
          itemVO.setProduct_id(cartVO.getProduct_id());
          itemVO.setQuantity(cartVO.getQuantity());
          itemVO.setPrice(cartVO.getPrice());
          itemVO.setTotal_price(cartVO.getTotal_price());
          
          this.marketOrderItemDAO.create(itemVO);

          Map<String, Object> map = new HashMap<String, Object>();
          map.put("product_id", cartVO.getProduct_id());
          map.put("quantity", cartVO.getQuantity());
          
          int updateCnt = this.marketOrderDAO.decrease_stock(map);
          
          if (updateCnt == 0) {
            throw new RuntimeException(cartVO.getProduct_id() + "번 상품의 재고가 부족합니다.");
          }
        }
      }

      this.marketCartDAO.delete_selected(marketOrderVO.getCart_ids());

      if ("Y".equals(marketOrderVO.getUpdate_address())) {
        this.marketOrderDAO.update_member_address(marketOrderVO);
      }
    }
    return cnt;
  }

  @Override
  public MarketOrderVO read(int order_id) {
    return this.marketOrderDAO.read(order_id);
  }

  @Override
  public List<MarketOrderVO> list_by_memberno(int memberno) {
    return this.marketOrderDAO.list_by_memberno(memberno);
  }

  @Override
  public List<MarketCartVO> list_by_cart_ids(String cart_ids) {
    if (cart_ids == null || cart_ids.trim().isEmpty()) return null;
    return this.marketCartDAO.list_by_cart_ids(cart_ids);
  }

  @Override
  public List<MarketOrderItemVO> list_by_order_id(int order_id) {
    return this.marketOrderItemDAO.list_by_order_id(order_id);
  }

  @Override
  public int delete_selected(String cart_ids) {
    return this.marketCartDAO.delete_selected(cart_ids);
  }

  @Transactional
  @Override
  public int delete(int order_id) {
    this.marketOrderItemDAO.delete_by_order_id(order_id);
    return this.marketOrderDAO.delete(order_id);
  }

  // ==========================================
  // ↓↓↓ 관리자 전용 기능 구현 ↓↓↓
  // ==========================================

  /**
   * 관리자 전용: 전체 주문 내역 목록
   */
  @Override
  public List<MarketOrderVO> list_all() {
    return this.marketOrderDAO.list_all();
  }
  /**
   * 바로 구매용 단일 상품 조회
   */
  @Override
  public List<MarketCartVO> list_direct_order(
          Integer product_id,
          Integer quantity) {

      return this.marketOrderDAO
              .list_direct_order(
                  product_id,
                  quantity
              );
  }
  /**
   * 주문 상태 변경 및 취소/반품 시 재고 복구 로직
   */
  @Transactional
  @Override
  public int update_status(Map<String, Object> map) {
      // 1. 주문 상태 업데이트 (공통 실행: PAID -> RETURN_REQC -> RETURN_DONE 등)
      int cnt = this.marketOrderDAO.update_status(map);
      
      if (cnt > 0) {
          String status = (String) map.get("order_status");
          int order_id = Integer.parseInt(String.valueOf(map.get("order_id")));
          
          // [핵심] HTML에서 관리자가 보낸 체크박스 값 (Y 또는 N)을 꺼냄
          // 값이 없으면 기본적으로 복구하도록 'Y'로 처리
          String restoreStock = map.get("restore_stock") != null ? (String)map.get("restore_stock") : "Y";

          // 2. 재고 복구 실행 조건 판단
          // 조건 A: 주문 취소(CANCEL_DONE)는 물건이 나간 적 없으므로 무조건 복구
          // 조건 B: 반품 승인(RETURN_DONE)이고, 관리자가 '판매 가능(Y)'이라고 체크했을 때만 복구
          boolean shouldRestore = "CANCEL_DONE".equals(status) 
                               || ("RETURN_DONE".equals(status) && "Y".equals(restoreStock));

          if (shouldRestore) {
              // 해당 주문에 포함된 상세 상품 리스트 조회
              List<MarketOrderItemVO> items = this.marketOrderItemDAO.list_by_order_id(order_id);
              if (items != null) {
                  for (MarketOrderItemVO item : items) {
                      Map<String, Object> stockMap = new HashMap<>();
                      stockMap.put("product_id", item.getProduct_id());
                      stockMap.put("quantity", item.getQuantity());
                      
                      // 재고 증가 쿼리 실행
                      this.marketOrderDAO.increase_stock(stockMap);
                  }
              }
          } else {
              // 반품은 승인하지만, 상품 훼손 등의 사유로 재고는 복구하지 않는 경우
              System.out.println("-> [재고 관리] 상품 훼손 확인: 재고 복구 로직을 건너뜁니다.");
          }
      }
      return cnt;
  }
  @Override
  public int update_address(MarketOrderVO marketOrderVO) {
    // DAO의 메서드를 호출합니다.
    return this.marketOrderDAO.update_address(marketOrderVO);
  }
  /**
   * 재고 복구 (단독 호출용)
   */
  @Override
  public int increase_stock(Map<String, Object> map) {
    return this.marketOrderDAO.increase_stock(map);
  }
}