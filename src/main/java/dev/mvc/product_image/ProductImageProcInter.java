package dev.mvc.product_image;

import java.util.List;

public interface ProductImageProcInter {
  public int create(ProductImageVO productImageVO);
  public List<ProductImageVO> list_by_product_id(int product_id);
  public int delete(int image_id);
  public int delete_by_product_id(int product_id);
}