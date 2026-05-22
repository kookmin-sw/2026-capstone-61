package dev.mvc.ai_actionlog;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * AI 실행 로그 Proc
 */
@Service
public class AiActionLogProc implements AiActionLogProcInter {

  /** AI 실행 로그 DAO */
  @Autowired
  private AiActionLogDAOInter aiActionLogDAO;

  /**
   * AI 실행 로그 저장
   * 
   * @param aiActionLogVO AI 실행 로그 정보
   * @return 등록된 레코드 수
   */
  @Override
  public int create(AiActionLogVO aiActionLogVO) {

    return this.aiActionLogDAO.create(aiActionLogVO);

  }

  /**
   * 로그 상세 조회
   * 
   * @param logno 로그 번호
   * @return AI 실행 로그 정보
   */
  @Override
  public AiActionLogVO read(int logno) {

    return this.aiActionLogDAO.read(logno);

  }

  /**
   * 전체 로그 조회
   * 
   * @return 전체 로그 목록
   */
  @Override
  public List<AiActionLogVO> list_all() {

    return this.aiActionLogDAO.list_all();

  }

  /**
   * 회원 번호별 로그 조회
   * 
   * @param memberno 회원 번호
   * @return 회원 로그 목록
   */
  @Override
  public List<AiActionLogVO> list_by_memberno(int memberno) {

    return this.aiActionLogDAO.list_by_memberno(memberno);

  }

  /**
   * 실행 타입별 로그 조회
   * 
   * @param actionType 실행 타입
   * @return 실행 타입별 로그 목록
   */
  @Override
  public List<AiActionLogVO> list_by_action_type(String actionType) {

    return this.aiActionLogDAO.list_by_action_type(actionType);

  }

  /**
   * 로그 삭제
   * 
   * @param logno 로그 번호
   * @return 삭제된 레코드 수
   */
  @Override
  public int delete(int logno) {

    return this.aiActionLogDAO.delete(logno);

  }

}