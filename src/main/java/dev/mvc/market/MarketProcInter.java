package dev.mvc.market;

import java.util.HashMap; // 추가 권장
import java.util.List;

public interface MarketProcInter {
    
    // 1. 상품 등록 로직
    int insertProduct(MarketVO vo);
    
    // 2. 전체 상품 목록 조회
    List<MarketVO> getProductList();
    
    // 3. 카테고리별 상품 목록 조회
    List<MarketVO> getProductListByCategory(String className);
    
    // ★ 7. 카테고리 + 검색어 통합 조회 (검색 기능 핵심)
    // 두 개 이상의 파라미터를 넘길 때는 Map이나 객체로 전달하는 것이 좋습니다.
    List<MarketVO> getProductListSearch(String className, String word);
    
    // 4. 상품 상세 조회 로직
    MarketVO getProductDetail(int productId);
    
    // 5. 상품 정보 수정
    int updateProduct(MarketVO vo);
    
    // 6. 상품 삭제
    int deleteProduct(int productId);
}