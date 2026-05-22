package dev.mvc.market_review;

import java.util.List;
import java.util.Map;

public interface MarketReviewProcInter {
  
  /**
   * 리뷰 등록
   * @param marketReviewVO
   * @return 등록된 레코드 갯수
   */
  public int create(MarketReviewVO marketReviewVO);

  /**
   * 상품별 리뷰 목록 (JOIN을 통해 이름 포함)
   * @param product_id
   * @return 리뷰 리스트
   */
  public List<MarketReviewVO> list_by_product(int product_id);

  /**
   * 특정 회원이 쓴 리뷰 목록
   * @param memberno
   * @return 리뷰 리스트
   */
  public List<MarketReviewVO> list_by_member(int memberno);

  /**
   * 리뷰 상세 조회
   * @param review_id
   * @return
   */
  public MarketReviewVO read(int review_id);

  /**
   * 리뷰 수정
   * @param marketReviewVO
   * @return 수정된 레코드 갯수
   */
  public int update(MarketReviewVO marketReviewVO);

  /**
   * 리뷰 삭제
   * @param review_id
   * @return 삭제된 레코드 갯수
   */
  public int delete(int review_id);
  
  /**
   * 특정 주문 상품에 대해 이미 작성한 리뷰가 있는지 확인
   * @param order_item_id
   * @return 중복이면 1, 아니면 0
   */
  public int check_duplicate(int order_item_id);
}