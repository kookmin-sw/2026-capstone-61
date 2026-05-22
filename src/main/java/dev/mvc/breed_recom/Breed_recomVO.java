package dev.mvc.breed_recom;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class Breed_recomVO {
  
  /** 추천 번호 (PK) */
  private int recomno;
  
  /** 추천 여부 (1: 추천, 0: 취소) */
  private int recom;
  
  /** 견종 번호 (FK) */
  private int breedno;
  
  /** 회원 번호 (FK) */
  private int memberno;
}