package dev.mvc.product_image;

import java.util.List;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("dev.mvc.product_image.ProductImageDAO")
public class ProductImageDAO implements ProductImageDAOInter {
    @Autowired
    private SqlSessionTemplate sqlSession;

    @Override
    public int create(ProductImageVO productImageVO) {
        return sqlSession.insert("dev.mvc.product_image.ProductImage.create", productImageVO);
    }

    @Override
    public List<ProductImageVO> list_by_product_id(int product_id) {
        return sqlSession.selectList("dev.mvc.product_image.ProductImage.list_by_product_id", product_id);
    }

    @Override
    public int delete(int image_id) {
        return sqlSession.delete("dev.mvc.product_image.ProductImage.delete", image_id);
    }

    // ★ 추가: 상품 번호에 연결된 모든 이미지 삭제
    @Override
    public int delete_by_product_id(int product_id) {
        return sqlSession.delete("dev.mvc.product_image.ProductImage.delete_by_product_id", product_id);
    }
}