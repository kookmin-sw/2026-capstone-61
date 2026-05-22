package dev.mvc.chat;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ChatDAOInter {

  /**
   * 채팅 등록
   * @param chatVO
   * @return 등록된 레코드 수
   */
  public int create(ChatVO chatVO);

  /**
   * 채팅 상세 조회
   * @param chatno
   * @return ChatVO
   */
  public ChatVO read(int chatno);

  /**
   * 회원 기준 최근 대화 조회
   * @param memberno
   * @return 최근 대화 목록
   */
  public List<ChatVO> recentList(int memberno);

  /**
   * 전체 채팅 목록
   * @return
   */
  public List<ChatVO> list_all();

  /**
   * 질문 유형별 조회
   * @param question_type
   * @return
   */
  public List<ChatVO> list_by_type(String question_type);

  /**
   * 채팅 수정
   * @param chatVO
   * @return
   */
  public int update(ChatVO chatVO);

  /**
   * 채팅 삭제
   * @param chatno
   * @return
   */
  public int delete(int chatno);

}