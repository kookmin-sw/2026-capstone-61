package dev.mvc.match_apply;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("dev.mvc.match_apply.MatchApplyProc")
public class MatchApplyProc implements MatchApplyProcInter {

  @Autowired
  private MatchApplyDAOInter matchApplyDAO;
  @Autowired
  @Qualifier("dev.mvc.member.MemberProc")
  private dev.mvc.member.MemberProcInter memberProc;
  /**
   * =========================================================
   * 매칭 신청 등록
   * =========================================================
   */
  @Override
  public int create(
      MatchApplyVO matchApplyVO
  ) {

    // =====================================================
    // 동일 게시글 중복 신청 확인
    // =====================================================
    int count =
        this.check_duplicate(
            matchApplyVO.getMatchno(),
            matchApplyVO.getMemberno()
        );

    // =====================================================
    // 이미 신청한 경우
    // =====================================================
    if (count > 0) {

      return -1;

    }

    // =====================================================
    // 신청 등록
    // =====================================================
    return this.matchApplyDAO.create(
        matchApplyVO
    );

  }

  /**
   * =========================================================
   * 게시글별 신청 목록
   * =========================================================
   */
  @Override
  public ArrayList<MatchApplyVO> list_by_matchno(
      int matchno
  ) {

    // 1. 먼저 신청자 기본 리스트를 불러옵니다.
    ArrayList<MatchApplyVO> list = this.matchApplyDAO.list_by_matchno(matchno);

    // 🎯 2. 불러온 리스트를 돌면서, 각 신청 유저의 찐 온도를 실시간 매핑합니다.
    if (list != null) {
      for (MatchApplyVO vo : list) {
        // 신청서에 박힌 신청자 회원 번호(memberno)로 실시간 회원 정보 호출
        dev.mvc.member.MemberVO memberVO = this.memberProc.read(vo.getMemberno());
        
        if (memberVO != null) {
          // 회원 테이블에 있는 실시간 manner_temp 값을 꺼내 VO의 manner_score 그릇에 전달
          vo.setManner_score(memberVO.getManner_temp());
        } else {
          // 안전을 위해 회원 정보가 탈퇴 등의 사유로 없을 시 기본값 셋팅
          vo.setManner_score(36.5);
        }
      }
    }

    return list;

  }

  /**
   * =========================================================
   * 회원별 신청 목록
   * =========================================================
   */
  @Override
  public ArrayList<MatchApplyVO> list_by_memberno(
      int memberno
  ) {

    return this.matchApplyDAO.list_by_memberno(
        memberno
    );

  }

  /**
   * =========================================================
   * 작성자가 받은 신청 목록
   * =========================================================
   */
  @Override
  public ArrayList<MatchApplyVO> list_by_author(
      int memberno
  ) {

    return this.matchApplyDAO.list_by_author(
        memberno
    );

  }

  /**
   * =========================================================
   * 종료된 산책 통합 목록
   * =========================================================
   */
  @Override
  public ArrayList<MatchApplyVO> completedList(
      int memberno
  ) {

    return this.matchApplyDAO.completedList(
        memberno
    );

  }

  /**
   * =========================================================
   * 신청 상세 조회
   * =========================================================
   */
  @Override
  public MatchApplyVO read(
      int applyno
  ) {

    return this.matchApplyDAO.read(
        applyno
    );

  }

  /**
   * =========================================================
   * 신청 상태 변경
   * =========================================================
   */
  @Override
  public int update_status(
      int applyno,
      int apply_status
  ) {

    HashMap<String, Object> map =
        new HashMap<String, Object>();

    map.put(
        "applyno",
        applyno
    );

    map.put(
        "apply_status",
        apply_status
    );

    return this.matchApplyDAO.update_status(
        map
    );

  }

  /**
   * =========================================================
   * 매칭 수락
   *
   * 처리 내용:
   * 1. 신청 상태 -> 수락
   * 2. SUCCESS_YN -> 1
   * 3. ACCEPT_DATE 기록
   * 4. 모집글 상태 마감
   * 5. 채팅방 생성
   * 6. 참여자 등록
   * =========================================================
   */
  @Transactional
  @Override
  public void acceptMatch(
      int applyno
  ) {

    this.matchApplyDAO.acceptMatch(
        applyno
    );

  }

  /**
   * =========================================================
   * 매칭 거절
   * =========================================================
   */
  @Override
  public int rejectMatch(
      int applyno
  ) {

    return this.matchApplyDAO.rejectMatch(
        applyno
    );

  }

  /**
   * =========================================================
   * 매칭 취소
   * =========================================================
   */
  @Override
  public int cancelMatch(
      MatchApplyVO vo
  ) {

    return this.matchApplyDAO.cancelMatch(
        vo
    );

  }

  /**
   * =========================================================
   * 산책 완료 처리
   * =========================================================
   */
  @Override
  public int completeMatch(
      int applyno
  ) {

    return this.matchApplyDAO.completeMatch(
        applyno
    );

  }

  /**
   * =========================================================
   * 리뷰 작성 완료 처리
   * =========================================================
   */
  @Override
  public int update_review_written(
      int applyno
  ) {

    return this.matchApplyDAO.update_review_written(
        applyno
    );

  }

  /**
   * =========================================================
   * 중복 신청 확인
   * =========================================================
   */
  @Override
  public int check_duplicate(
      int matchno,
      int memberno
  ) {

    HashMap<String, Object> map =
        new HashMap<String, Object>();

    map.put(
        "matchno",
        matchno
    );

    map.put(
        "memberno",
        memberno
    );

    return this.matchApplyDAO.check_duplicate(
        map
    );

  }

  /**
   * =========================================================
   * 신청 삭제
   * =========================================================
   */
  @Override
  public int delete(
      int applyno
  ) {

    return this.matchApplyDAO.delete(
        applyno
    );

  }

  /**
   * =========================================================
   * 산책 시작
   * =========================================================
   */
  @Override
  public int startWalk(
      int applyno
  ) {

    return this.matchApplyDAO.startWalk(
        applyno
    );

  }

  /**
   * =========================================================
   * 약속 시간이 지난 매칭 자동 시작
   * =========================================================
   */
  @Override
  public int auto_start_walk() {

    return this.matchApplyDAO.auto_start_walk();

  }

}