package dev.mvc.chat_memory;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * AI 사용자 기억 저장 VO
 */
@Getter
@Setter
@ToString
public class ChatMemoryVO {

  /** 기억 번호 PK */
  private int memoryno;

  /** 회원 번호 FK */
  private int memberno;

  /** 강아지 번호 FK */
  private int dogno;

  /** 원본 채팅 번호 FK */
  private int source_chatno;

  /** 기억 타입 */
  private String memory_type;

  /** 기억 내용 */
  private String memory_text;

  /** 중요도 */
  private int importance;

  /** 사용 횟수 */
  private int use_count;

  /** 마지막 사용일 */
  private Date last_used;

  /** 활성 여부 */
  private String active_yn;

  /** 생성일 */
  private Date rdate;

}