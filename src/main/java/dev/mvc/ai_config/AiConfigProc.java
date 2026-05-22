package dev.mvc.ai_config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * AI 설정 Proc
 */
@Service
public class AiConfigProc implements AiConfigProcInter {

  @Autowired
  private AiConfigDAOInter aiConfigDAO;

  /**
   * Prompt 등록
   */
  @Override
  public int create(AiConfigVO aiConfigVO) {

    return this.aiConfigDAO.create(aiConfigVO);

  }

  /**
   * KEY 기준 조회
   */
  @Override
  public AiConfigVO readByKey(String config_key) {

    return this.aiConfigDAO.readByKey(config_key);

  }

  /**
   * 상세 조회
   */
  @Override
  public AiConfigVO read(int configno) {

    return this.aiConfigDAO.read(configno);

  }

  /**
   * 전체 목록 조회
   */
  @Override
  public List<AiConfigVO> list_all() {

    return this.aiConfigDAO.list_all();

  }

  /**
   * 타입별 조회
   */
  @Override
  public List<AiConfigVO> list_by_type(String config_type) {

    return this.aiConfigDAO.list_by_type(config_type);

  }

  /**
   * 수정
   */
  @Override
  public int update(AiConfigVO aiConfigVO) {

    return this.aiConfigDAO.update(aiConfigVO);

  }

  /**
   * 사용 여부 수정
   */
  @Override
  public int update_use_yn(AiConfigVO aiConfigVO) {

    return this.aiConfigDAO.update_use_yn(aiConfigVO);

  }

  /**
   * 삭제
   */
  @Override
  public int delete(int configno) {

    return this.aiConfigDAO.delete(configno);

  }

}