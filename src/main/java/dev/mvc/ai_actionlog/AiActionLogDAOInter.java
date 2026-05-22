package dev.mvc.ai_actionlog;

import java.util.List;

/**
 * AI 실행 로그 DAO 인터페이스
 */
public interface AiActionLogDAOInter {

  /**
   * AI 실행 로그 등록
   * 
   * @param aiActionLogVO AI 실행 로그 정보
   * @return 등록된 레코드 수
   */
  public int create(AiActionLogVO aiActionLogVO);

  /**
   * 로그 상세 조회
   * 
   * @param logno 로그 번호
   * @return AI 실행 로그 정보
   */
  public AiActionLogVO read(int logno);

  /**
   * 전체 로그 조회
   * 
   * @return 전체 로그 목록
   */
  public List<AiActionLogVO> list_all();

  /**
   * 회원 번호별 로그 조회
   * 
   * @param memberno 회원 번호
   * @return 회원 로그 목록
   */
  public List<AiActionLogVO> list_by_memberno(int memberno);

  /**
   * 실행 타입별 로그 조회
   * 
   * actionType:
   * CHAT / MATCH / ANALYZE
   * 
   * @param actionType 실행 타입
   * @return 실행 타입별 로그 목록
   */
  public List<AiActionLogVO> list_by_action_type(String actionType);

  /**
   * 로그 삭제
   * 
   * @param logno 로그 번호
   * @return 삭제된 레코드 수
   */
  public int delete(int logno);

}