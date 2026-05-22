package dev.mvc.match_review;

import java.util.ArrayList;
import java.util.HashMap;
import dev.mvc.member.MemberProc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Map;


@Service("dev.mvc.match_review.MatchReviewProc")
public class MatchReviewProc implements MatchReviewProcInter {

  @Autowired
  private MatchReviewDAOInter matchReviewDAO; // DAO 주입
  
  @Autowired
  private MemberProc memberProc;
  public MatchReviewProc() {
    System.out.println("-> MatchReviewProc created.");
  }

  /**
   * 📝 리뷰 등록
   */
  @Override
  public int create(MatchReviewVO matchReviewVO) {

      // =====================================================
      // 👤 보호자 평점 가져오기
      // =====================================================

      double ownerScore =
          matchReviewVO.getOwner_score();

      // =====================================================
      // 🌡️ 매너온도 변화량 계산
      // =====================================================

      double delta = 0;

      // 4.5 ~ 5.0
      if (ownerScore >= 4.5) {
          delta = 2.0;
      }

      // 3.5 ~ 4.4
      else if (ownerScore >= 3.5) {
          delta = 1.0;
      }

      // 2.5 ~ 3.4
      else if (ownerScore >= 2.5) {
          delta = 0;
      }

      // 1.5 ~ 2.4
      else if (ownerScore >= 1.5) {
          delta = -1.5;
      }

      // 0 ~ 1.4
      else {
          delta = -3.0;
      }

      // =====================================================
      // 🌡️ 변화량 저장
      // =====================================================

      matchReviewVO.setManner_delta(delta);

      // =====================================================
      // 📝 리뷰 등록
      // =====================================================

      int cnt =
          this.matchReviewDAO.create(matchReviewVO);

      // =====================================================
      // 👤 리뷰 성공 시 매너온도 반영
      // =====================================================

      if (cnt == 1) {

          HashMap<String, Object> map =
              new HashMap<String, Object>();

          // 리뷰 대상 회원 번호
          map.put(
              "memberno",
              matchReviewVO.getTarget_no());

          // 변화량
          map.put("delta", delta);

          // 매너온도 업데이트
          this.memberProc.updateMannerTemp(map);
      }

      return cnt;
  }

  /**
   * 📋 특정 매칭의 리뷰 목록 조회
   */
  @Override
  public ArrayList<MatchReviewVO> list_by_matchno(int matchno) {

    ArrayList<MatchReviewVO> list =
        this.matchReviewDAO.list_by_matchno(matchno);

    return list;
  }

  /**
   * 👤 특정 회원이 받은 리뷰 목록 조회
   */
  @Override
  public ArrayList<MatchReviewVO> list_by_target(int target_no) {

    ArrayList<MatchReviewVO> list =
        this.matchReviewDAO.list_by_target(target_no);

    return list;
  }

  /**
   * 🔍 리뷰 상세 조회
   */
  @Override
  public MatchReviewVO read(int reviewno) {

    MatchReviewVO matchReviewVO =
        this.matchReviewDAO.read(reviewno);

    return matchReviewVO;
  }

  /**
   * ❌ 리뷰 삭제
   */
  @Override
  public int delete(int reviewno) {

    int cnt = this.matchReviewDAO.delete(reviewno);

    return cnt;
  }

  /**
   * 📚 내가 작성한 리뷰 + 내가 받은 리뷰 전체 조회
   */
  @Override
  public ArrayList<MatchReviewVO> list_all_my_reviews(int memberno) {

    ArrayList<MatchReviewVO> list =
        this.matchReviewDAO.list_all_my_reviews(memberno);

    return list;
  }

  /**
   * 🚫 중복 리뷰 체크
   * 같은 매칭에서 동일 사용자가 리뷰를 이미 작성했는지 확인
   */
  @Override
  public int check_duplicate(int matchno, int writer_no) {

    // DAO 전달용 Map 생성
    HashMap<String, Object> map = new HashMap<String, Object>();

    map.put("matchno", matchno);

    map.put("writer_no", writer_no);

    int cnt = this.matchReviewDAO.check_duplicate(map);

    return cnt;
  }
  /**
   * ⭐ 평균 보호자 평점
   */
  @Override
  public Double avg_owner_score(int target_no) {

      Double avg =
          this.matchReviewDAO.avg_owner_score(target_no);

      return avg;
  }

  /**
   * 🐶 평균 강아지 평점
   */
  @Override
  public Double avg_dog_score(int target_no) {

      Double avg =
          this.matchReviewDAO.avg_dog_score(target_no);

      return avg;
  }
}