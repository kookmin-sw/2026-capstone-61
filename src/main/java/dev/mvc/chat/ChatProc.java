package dev.mvc.chat;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * Chat Proc
 */
@Service
public class ChatProc implements ChatProcInter {


  @Autowired
  private ChatDAOInter chatDAO;

  /**
   * 채팅 저장
   */
  @Override
  public int create(ChatVO chatVO) {

    return this.chatDAO.create(chatVO);
  }

  /**
   * 채팅 상세 조회
   */
  @Override
  public ChatVO read(int chatno) {

    return this.chatDAO.read(chatno);
  }

  /**
   * 전체 조회
   */
  @Override
  public List<ChatVO> list_all() {

    return this.chatDAO.list_all();
  }

  /**
   * 최근 대화 조회
   */
  @Override
  public List<ChatVO> recentList(int memberno) {

    return this.chatDAO.recentList(memberno);
  }

  /**
   * 질문 타입별 조회
   */
  @Override
  public List<ChatVO> list_by_type(String question_type) {

    return this.chatDAO.list_by_type(question_type);
  }

  /**
   * 수정
   */
  @Override
  public int update(ChatVO chatVO) {

    return this.chatDAO.update(chatVO);
  }

  /**
   * 삭제
   */
  @Override
  public int delete(int chatno) {

    return this.chatDAO.delete(chatno);
  }

}