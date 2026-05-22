package dev.mvc.market;

import java.util.HashMap; // HashMap 사용을 위한 import 추가
import java.util.List;
import java.util.Map;     // Map 사용을 위한 import 추가
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("marketProc") 
public class MarketProc implements MarketProcInter {

    @Autowired
    private MarketDAOInter marketDAO; 

    @Override
    public int insertProduct(MarketVO vo) {
        return marketDAO.insertProduct(vo);
    }

    @Override
    public List<MarketVO> getProductList() {
        return marketDAO.getProductList();
    }

    @Override
    public List<MarketVO> getProductListByCategory(String className) {
        return marketDAO.getProductListByCategory(className);
    }

    // ★ 검색 기능 구현 추가
    @Override
    public List<MarketVO> getProductListSearch(String className, String word) {
        // MyBatis는 파라미터를 하나만 받을 수 있으므로 Map에 담아서 전달합니다.
        Map<String, Object> map = new HashMap<>();
        map.put("className", className);
        map.put("word", word);
        
        return marketDAO.getProductListSearch(map);
    }

    @Override
    public MarketVO getProductDetail(int productId) {
        return marketDAO.getProductDetail(productId);
    }

    @Override
    public int updateProduct(MarketVO vo) {
        return marketDAO.updateProduct(vo);
    }

    @Override
    public int deleteProduct(int productId) {
        return marketDAO.deleteProduct(productId);
    }
}