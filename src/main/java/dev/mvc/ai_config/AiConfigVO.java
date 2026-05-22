package dev.mvc.ai_config;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * AI 설정 VO
 */
@Getter
@Setter
@ToString
public class AiConfigVO {

  /** 설정 번호 PK */
  private int configno;

  /** 설정 KEY */
  private String config_key;

  /** 설정 이름 */
  private String config_name;

  /** 설정 타입 (PROMPT / STYLE / RULE) */
  private String config_type;

  /** 실제 Prompt 내용 */
  private String config_val;

  /** 설명 */
  private String description;

  /** 사용 여부 */
  private String use_yn;

  /** 생성일 */
  private Date rdate;

}