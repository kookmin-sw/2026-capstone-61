package dev.mvc.ai_config;

import java.util.List;

public interface AiConfigDAOInter {

  /**
   * Prompt 등록
   * @param aiConfigVO
   * @return
   */
  public int create(AiConfigVO aiConfigVO);

  /**
   * KEY 기준 조회
   * @param config_key
   * @return
   */
  public AiConfigVO readByKey(String config_key);

  /**
   * 설정 상세 조회
   * @param configno
   * @return
   */
  public AiConfigVO read(int configno);

  /**
   * 전체 목록
   * @return
   */
  public List<AiConfigVO> list_all();

  /**
   * 타입별 조회
   * @param config_type
   * @return
   */
  public List<AiConfigVO> list_by_type(String config_type);

  /**
   * Prompt 수정
   * @param aiConfigVO
   * @return
   */
  public int update(AiConfigVO aiConfigVO);

  /**
   * 사용 여부 변경
   * @param aiConfigVO
   * @return
   */
  public int update_use_yn(AiConfigVO aiConfigVO);

  /**
   * 삭제
   * @param configno
   * @return
   */
  public int delete(int configno);

}