package dev.mvc.match_review;

import java.util.ArrayList;

public interface MatchReviewProcInter {

  /**
   * 📝 리뷰 등록
   * 
   * @param matchReviewVO 리뷰 정보
   * @return 등록된 레코드 수
   */
  public int create(MatchReviewVO matchReviewVO);

  
  /**
   * 📋 특정 매칭 게시글의 리뷰 목록 조회
   * 
   * @param matchno 매칭 게시글 번호
   * @return 리뷰 목록
   */
  public ArrayList<MatchReviewVO> list_by_matchno(int matchno);

  
  /**
   * 👤 특정 회원이 받은 리뷰 목록 조회
   * 
   * @param target_no 리뷰 대상 회원 번호
   * @return 받은 리뷰 목록
   */
  public ArrayList<MatchReviewVO> list_by_target(int target_no);

  
  /**
   * 🔍 리뷰 상세 조회
   * 
   * @param reviewno 리뷰 번호
   * @return 리뷰 정보
   */
  public MatchReviewVO read(int reviewno);

  
  /**
   * ❌ 리뷰 삭제
   * 
   * @param reviewno 리뷰 번호
   * @return 삭제된 레코드 수
   */
  public int delete(int reviewno);

  
  /**
   * 🚫 중복 리뷰 작성 여부 확인
   * 동일 매칭에서 동일 사용자가 이미 리뷰를 작성했는지 확인
   * 
   * @param matchno 매칭 번호
   * @param writer_no 리뷰 작성자 회원 번호
   * @return 0: 작성 가능 / 1: 이미 작성됨
   */
  public int check_duplicate(int matchno, int writer_no);

  
  /**
   * 📚 내가 관련된 전체 리뷰 조회
   * (내가 작성한 리뷰 + 내가 받은 리뷰)
   * 
   * @param memberno 로그인 회원 번호
   * @return 통합 리뷰 목록
   */
  public ArrayList<MatchReviewVO> list_all_my_reviews(int memberno);
  /**
   * ⭐ 평균 보호자 평점
   * 
   * @param target_no 리뷰 대상 회원 번호
   * @return 평균 보호자 평점
   */
  public Double avg_owner_score(int target_no);

  /**
   * 🐶 평균 강아지 평점
   * 
   * @param target_no 리뷰 대상 회원 번호
   * @return 평균 강아지 평점
   */
  public Double avg_dog_score(int target_no);
}