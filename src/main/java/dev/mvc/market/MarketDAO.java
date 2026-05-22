package dev.mvc.market;

import java.util.List;
import java.util.Map; // Map 사용을 위한 import 추가
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("marketDAO")
public class MarketDAO implements MarketDAOInter {

    @Autowired
    private SqlSession sqlSession;
    
    private static final String NAMESPACE = "dev.mvc.market.MarketDAOInter";

    @Override
    public int insertProduct(MarketVO vo) {
        return sqlSession.insert(NAMESPACE + ".insertProduct", vo);
    }

    @Override
    public List<MarketVO> getProductList() {
        return sqlSession.selectList(NAMESPACE + ".getProductList");
    }

    @Override
    public List<MarketVO> getProductListByCategory(String className) {
        return sqlSession.selectList(NAMESPACE + ".getProductListByCategory", className);
    }

    // ★ 검색 기능 구현 추가
    @Override
    public List<MarketVO> getProductListSearch(Map<String, Object> map) {
        // sqlSession.selectList를 통해 XML의 getProductListSearch 아이디를 찾아 실행합니다.
        return sqlSession.selectList(NAMESPACE + ".getProductListSearch", map);
    }

    @Override
    public MarketVO getProductDetail(int productId) {
        return sqlSession.selectOne(NAMESPACE + ".getProductDetail", productId);
    }

    @Override
    public int updateProduct(MarketVO vo) {
        return sqlSession.update(NAMESPACE + ".updateProduct", vo);
    }

    @Override
    public int deleteProduct(int productId) {
        return sqlSession.delete(NAMESPACE + ".deleteProduct", productId);
    }
}