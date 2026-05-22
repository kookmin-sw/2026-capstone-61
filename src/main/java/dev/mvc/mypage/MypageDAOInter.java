package dev.mvc.mypage;

import java.util.List;
import java.util.Map;

import dev.mvc.member.MemberVO;
import dev.mvc.market_order.MarketOrderVO;
import dev.mvc.dog.DogVO;
import dev.mvc.comments.CommentsVO;
import dev.mvc.community.CommunityVO;

public interface MypageDAOInter {
    MemberVO readMember(int memberno);
    Map<String, Object> mypageSummary(int memberno);

    List<MarketOrderVO> listOrder(int memberno);
    List<DogVO> listDog(int memberno);
    List<CommentsVO> listComments(int memberno);
    List<CommunityVO> listCommunity(int memberno);

    // VO 대신 Map을 사용하여 빨간줄(에러) 방지
    List<Map<String, Object>> listCart(int memberno);
    List<Map<String, Object>> listLoginHistory(int memberno);

    int createDog(DogVO dogVO);
    int updateDog(DogVO dogVO);
    int deleteDog(int dogno);

    int deleteCart(int cart_id);
    int deleteComment(int comment_no);
}