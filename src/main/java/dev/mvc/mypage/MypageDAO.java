package dev.mvc.mypage;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import dev.mvc.member.MemberVO;
import dev.mvc.market_order.MarketOrderVO;
import dev.mvc.dog.DogVO;
import dev.mvc.comments.CommentsVO;
import dev.mvc.community.CommunityVO;

@Repository
public class MypageDAO implements MypageDAOInter {

    @Autowired
    private SqlSessionTemplate sqlSession;

    private final String namespace = "dev.mvc.mypage.MypageMapper";

    @Override
    public MemberVO readMember(int memberno) {
        return sqlSession.selectOne(namespace + ".readMember", memberno);
    }

    @Override
    public Map<String, Object> mypageSummary(int memberno) {
        return sqlSession.selectOne(namespace + ".mypageSummary", memberno);
    }

    @Override
    public List<MarketOrderVO> listOrder(int memberno) {
        return sqlSession.selectList(namespace + ".listOrder", memberno);
    }

    @Override
    public List<DogVO> listDog(int memberno) {
        return sqlSession.selectList(namespace + ".listDog", memberno);
    }

    @Override
    public List<CommentsVO> listComments(int memberno) {
        return sqlSession.selectList(namespace + ".listComments", memberno);
    }

    @Override
    public List<CommunityVO> listCommunity(int memberno) {
        return sqlSession.selectList(namespace + ".listCommunity", memberno);
    }

    @Override
    public List<Map<String, Object>> listCart(int memberno) {
        return sqlSession.selectList(namespace + ".listCart", memberno);
    }

    @Override
    public List<Map<String, Object>> listLoginHistory(int memberno) {
        return sqlSession.selectList(namespace + ".listLoginHistory", memberno);
    }

    @Override
    public int createDog(DogVO dogVO) {
        return sqlSession.insert(namespace + ".createDog", dogVO);
    }

    @Override
    public int updateDog(DogVO dogVO) {
        return sqlSession.update(namespace + ".updateDog", dogVO);
    }

    @Override
    public int deleteDog(int dogno) {
        return sqlSession.delete(namespace + ".deleteDog", dogno);
    }

    @Override
    public int deleteCart(int cart_id) {
        return sqlSession.delete(namespace + ".deleteCart", cart_id);
    }

    @Override
    public int deleteComment(int comment_no) {
        return sqlSession.delete(namespace + ".deleteComment", comment_no);
    }
}