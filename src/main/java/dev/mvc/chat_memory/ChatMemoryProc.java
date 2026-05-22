package dev.mvc.chat_memory;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * AI 사용자 기억 Proc
 */
@Service
public class ChatMemoryProc implements ChatMemoryProcInter {

  @Autowired
  private ChatMemoryDAOInter chatMemoryDAO;

  /**
   * 기억 저장
   */
  @Override
  public int create(ChatMemoryVO chatMemoryVO) {

    return this.chatMemoryDAO.create(chatMemoryVO);

  }

  /**
   * 기억 상세 조회
   */
  @Override
  public ChatMemoryVO read(int memoryno) {

    return this.chatMemoryDAO.read(memoryno);

  }

  /**
   * 회원별 기억 조회
   */
  @Override
  public List<ChatMemoryVO> list_by_memberno(int memberno) {

    return this.chatMemoryDAO.list_by_memberno(memberno);

  }

  /**
   * 강아지 기준 기억 조회
   */
  @Override
  public List<ChatMemoryVO> list_by_dogno(int dogno) {

    return this.chatMemoryDAO.list_by_dogno(dogno);

  }

  /**
   * 전체 기억 조회
   */
  @Override
  public List<ChatMemoryVO> list_all() {

    return this.chatMemoryDAO.list_all();

  }

  /**
   * 기억 수정
   */
  @Override
  public int update(ChatMemoryVO chatMemoryVO) {

    return this.chatMemoryDAO.update(chatMemoryVO);

  }

  /**
   * 사용 횟수 증가
   */
  @Override
  public int increase_use_count(int memoryno) {

    return this.chatMemoryDAO.increase_use_count(memoryno);

  }

  /**
   * 마지막 사용일 업데이트
   */
  @Override
  public int update_last_used(int memoryno) {

    return this.chatMemoryDAO.update_last_used(memoryno);

  }

  /**
   * 활성 여부 변경
   */
  @Override
  public int update_active_yn(ChatMemoryVO chatMemoryVO) {

    return this.chatMemoryDAO.update_active_yn(chatMemoryVO);

  }

  /**
   * 기억 삭제
   */
  @Override
  public int delete(int memoryno) {

    return this.chatMemoryDAO.delete(memoryno);

  }

}