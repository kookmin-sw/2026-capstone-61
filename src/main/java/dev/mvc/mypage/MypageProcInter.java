package dev.mvc.mypage;

import java.util.List;
import java.util.Map;

import dev.mvc.member.MemberVO;
import dev.mvc.market_order.MarketOrderVO;
import dev.mvc.dog.DogVO;
import dev.mvc.comments.CommentsVO;
import dev.mvc.community.CommunityVO;

public interface MypageProcInter {
    
    // 1. 기본 조회
    MemberVO readMember(int memberno);
    Map<String, Object> mypageSummary(int memberno);

    List<MarketOrderVO> listOrder(int memberno);
    List<DogVO> listDog(int memberno);
    List<CommentsVO> listComments(int memberno);
    List<CommunityVO> listCommunity(int memberno);

    // 2. 장바구니 / 로그인 내역 (Map을 써서 VO 에러 방지)
    List<Map<String, Object>> listCart(int memberno);
    List<Map<String, Object>> listLoginHistory(int memberno);

    // 3. 반려견 추가, 수정, 삭제 (빨간줄의 원인이었던 부분 추가!)
    int createDog(DogVO dogVO);
    int updateDog(DogVO dogVO);
    int deleteDog(int dogno);

    // 4. 장바구니, 댓글 삭제
    int deleteCart(int cart_id);
    int deleteComment(int comment_no);
}