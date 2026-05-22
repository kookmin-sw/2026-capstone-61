package dev.mvc.market_review;

import java.util.List;

public interface MarketReviewDAOInter {
  
  /**
   * 리뷰 등록
   * @param marketReviewVO
   * @return 등록된 레코드 갯수
   */
  public int create(MarketReviewVO marketReviewVO);

  /**
   * 특정 상품의 리뷰 목록 (MEMBER 테이블과 JOIN하여 작성자 이름/아이디 포함)
   * @param product_id
   * @return 리뷰 목록
   */
  public List<MarketReviewVO> list_by_product(int product_id);

  /**
   * 특정 회원의 리뷰 목록 (본인이 쓴 리뷰 확인용)
   * @param memberno
   * @return 리뷰 목록
   */
  public List<MarketReviewVO> list_by_member(int memberno);

  /**
   * 리뷰 상세 조회
   * @param review_id
   * @return 
   */
  public MarketReviewVO read(int review_id);

  /**
   * 리뷰 수정 (별점, 내용, 이미지 등)
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
   * 특정 주문 상세(order_item_id)에 대해 이미 작성된 리뷰가 있는지 확인
   * 중복 리뷰 작성을 방지하기 위함
   * @param order_item_id
   * @return 존재하면 1, 없으면 0
   */
  public int check_duplicate(int order_item_id);
  
}