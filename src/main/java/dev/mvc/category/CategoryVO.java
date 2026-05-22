package dev.mvc.category;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter @Getter @ToString
public class CategoryVO {
  
  /** 카테고리 번호 (PK) - Integer wrapper used for null safety during creation */
  private Integer cat_no;
  
  /** 메뉴 표시 이름 */
  @NotBlank(message="메뉴 이름은 필수 입력 항목입니다.") // NotEmpty + trim check
  @Size(max=50, message="메뉴 이름은 50자 이내여야 합니다.")
  private String cat_name;

  /** 내부 코드 (중복 방지용) */
  @NotBlank(message="카테고리 코드는 필수 입력 항목입니다.")
  @Size(max=50, message="코드는 50자 이내여야 합니다.")
  private String cat_code;

  /** 처리 유형 (INFO, BOARD, SHOP, PAGE, MAP, USER) */
  @NotBlank(message="처리 유형은 필수 입력 항목입니다.")
  @Pattern(
      regexp="^(INFO|BOARD|SHOP|PAGE|MAP|USER)$",
      message="INFO, BOARD, SHOP, PAGE, MAP, USER 중 하나만 입력하세요."
  )
  private String cat_type;
  
  /** 출력 순서 */
  @NotNull(message="출력 순서는 필수 항목입니다.")
  @Min(value = 1, message="1 이상의 숫자를 입력하세요.") // Usually starts from 1
  @Max(value = 999, message="999 이하로 입력하세요.")
  private Integer seqno = 1;

  /** 출력 여부 (Y/N) */
  @NotBlank(message="출력 모드는 필수 항목입니다.")
  @Pattern(regexp="^[YN]$", message="Y 또는 N만 입력 가능합니다.")
  private String visible = "Y";
}