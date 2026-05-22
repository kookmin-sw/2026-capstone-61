package dev.mvc.ai_actionlog;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * AI 실행 로그 VO
 * 
 * DB 테이블: ai_action_log
 */
@Getter
@Setter
@ToString
public class AiActionLogVO {

  /** 로그 번호 PK */
  private int logno;

  /** 회원 번호 FK */
  private Integer memberno;

  /** 강아지 번호 FK */
  private Integer dogno;

  /** 채팅 번호 FK */
  private Integer chatno;

  /** 실행 타입 (CHAT / MATCH / ANALYZE) */
  private String actionType;

  /** 입력 데이터 */
  private String inputData;

  /** 결과 데이터 */
  private String outputData;

  /** AI 모델명 */
  private String aiModel;

  /** 성공 여부 (Y/N) */
  private String successYn;

  /** 에러 메시지 */
  private String errorMessage;

  /** 응답 시간(ms) */
  private Integer responseTime;

  /** 토큰 사용량 */
  private Integer tokenUsage;

  /** 생성일 */
  private Date rdate;

}