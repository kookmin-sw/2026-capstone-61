package dev.mvc.market;

import java.util.List;
import java.util.Map; // Map 사용을 위한 import 추가

public interface MarketDAOInter {
    
    int insertProduct(MarketVO vo);
    
    List<MarketVO> getProductList();
    
    List<MarketVO> getProductListByCategory(String className);

    /**
     * 카테고리 및 검색어 기반 상품 목록 조회
     * @param map (className, word 포함)
     * @return 검색된 상품 목록
     */
    List<MarketVO> getProductListSearch(Map<String, Object> map); // ★ 검색 메서드 추가
    
    MarketVO getProductDetail(int productId);
    
    int updateProduct(MarketVO vo);
    
    int deleteProduct(int productId);
}