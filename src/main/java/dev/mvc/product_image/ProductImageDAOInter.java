package dev.mvc.product_image;

import java.util.List;

public interface ProductImageDAOInter {
  /**
   * 등록
   * @param productImageVO
   * @return
   */
	public int create(ProductImageVO productImageVO);

  /**
   * 특정 상품의 이미지 목록
   * @param product_id
   * @return
   */
  public List<ProductImageVO> list_by_product_id(int product_id);

  /**
   * 삭제
   * @param product_image_id
   * @return
   */
  public int delete(int image_id);
  public int delete_by_product_id(int product_id);
}