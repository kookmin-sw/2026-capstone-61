package dev.mvc.chat;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * AI 챗봇 대화 로그 VO
 */
@Getter
@Setter
@ToString
public class ChatVO {

  /** 채팅 번호 PK */
  private int chatno;

  /** 회원 번호 FK */
  private int memberno;

  /** 강아지 번호 FK */
  private int dogno;

  /** 카테고리 번호 FK */
  private int cat_no;

  /** 사용자 질문 */
  private String question;

  /** AI 답변 */
  private String answer;

  /** 질문 유형 */
  private String question_type;

  /** 감정 분석 */
  private String emotion;

  /** AI 모델명 */
  private String ai_model;

  /** GPT 전달 컨텍스트 */
  private String context_data;

  /** 입력 토큰 */
  private int prompt_tokens;

  /** 출력 토큰 */
  private int answer_tokens;

  /** 응답 시간(ms) */
  private int response_time;

  /** 기억 저장 여부 */
  private String memory_saved_yn;

  /** 생성일 */
  private Date rdate;

}