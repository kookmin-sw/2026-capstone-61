package dev.mvc.mypage;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import dev.mvc.member.MemberVO;
import dev.mvc.market_order.MarketOrderVO;
import dev.mvc.dog.DogVO;
import dev.mvc.comments.CommentsVO;
import dev.mvc.community.CommunityVO;

@Component
public class MypageProc implements MypageProcInter {

    @Autowired
    private MypageDAOInter mypageDAO;

    @Override
    public MemberVO readMember(int memberno) {
        return mypageDAO.readMember(memberno);
    }

    @Override
    public Map<String, Object> mypageSummary(int memberno) {
        return mypageDAO.mypageSummary(memberno);
    }

    @Override
    public List<MarketOrderVO> listOrder(int memberno) {
        return mypageDAO.listOrder(memberno);
    }

    @Override
    public List<DogVO> listDog(int memberno) {
        return mypageDAO.listDog(memberno);
    }

    @Override
    public List<CommentsVO> listComments(int memberno) {
        return mypageDAO.listComments(memberno);
    }

    @Override
    public List<CommunityVO> listCommunity(int memberno) {
        return mypageDAO.listCommunity(memberno);
    }

    @Override
    public List<Map<String, Object>> listCart(int memberno) {
        return mypageDAO.listCart(memberno);
    }

    @Override
    public List<Map<String, Object>> listLoginHistory(int memberno) {
        return mypageDAO.listLoginHistory(memberno);
    }

    @Override
    public int createDog(DogVO dogVO) {
        return mypageDAO.createDog(dogVO);
    }

    @Override
    public int updateDog(DogVO dogVO) {
        return mypageDAO.updateDog(dogVO);
    }

    @Override
    public int deleteDog(int dogno) {
        return mypageDAO.deleteDog(dogno);
    }

    @Override
    public int deleteCart(int cart_id) {
        return mypageDAO.deleteCart(cart_id);
    }

    @Override
    public int deleteComment(int comment_no) {
        return mypageDAO.deleteComment(comment_no);
    }
}