package dev.mvc.chat_memory;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

/**
 * AI 사용자 기억 DAO
 */
@Mapper
public interface ChatMemoryDAOInter {

  /**
   * 기억 저장
   * @param chatMemoryVO
   * @return
   */
  public int create(ChatMemoryVO chatMemoryVO);

  /**
   * 기억 상세 조회
   * @param memoryno
   * @return
   */
  public ChatMemoryVO read(int memoryno);

  /**
   * 회원별 기억 목록
   * @param memberno
   * @return
   */
  public List<ChatMemoryVO> list_by_memberno(int memberno);

  /**
   * 강아지 기준 기억 조회
   * @param dogno
   * @return
   */
  public List<ChatMemoryVO> list_by_dogno(int dogno);

  /**
   * 전체 기억 조회
   * @return
   */
  public List<ChatMemoryVO> list_all();

  /**
   * 기억 수정
   * @param chatMemoryVO
   * @return
   */
  public int update(ChatMemoryVO chatMemoryVO);

  /**
   * 사용 횟수 증가
   * @param memoryno
   * @return
   */
  public int increase_use_count(int memoryno);

  /**
   * 마지막 사용일 업데이트
   * @param memoryno
   * @return
   */
  public int update_last_used(int memoryno);

  /**
   * 활성 여부 변경
   * @param chatMemoryVO
   * @return
   */
  public int update_active_yn(ChatMemoryVO chatMemoryVO);

  /**
   * 기억 삭제
   * @param memoryno
   * @return
   */
  public int delete(int memoryno);

}