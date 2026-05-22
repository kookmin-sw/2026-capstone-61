package dev.mvc.product_image;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("dev.mvc.product_image.ProductImageProc")
public class ProductImageProc implements ProductImageProcInter {
  @Autowired
  @Qualifier("dev.mvc.product_image.ProductImageDAO")
  private ProductImageDAOInter productImageDAO;

  @Override
  public int create(ProductImageVO productImageVO) {
    return this.productImageDAO.create(productImageVO);
  }

  @Override
  public List<ProductImageVO> list_by_product_id(int product_id) {
    return this.productImageDAO.list_by_product_id(product_id);
  }

  @Override
  public int delete(int image_id) {
    return this.productImageDAO.delete(image_id);
  }
  @Override
  public int delete_by_product_id(int product_id) {
      return this.productImageDAO.delete_by_product_id(product_id);
  }
}