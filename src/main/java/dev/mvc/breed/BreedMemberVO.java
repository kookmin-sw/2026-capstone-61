package dev.mvc.breed;

import org.springframework.web.multipart.MultipartFile;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class BreedMemberVO {
  
  // --- Breed(견종) 테이블 관련 필드 ---
  /** 견종 식별 번호 (PK) */
  private int breedno;
  
  /** 카테고리 번호 (FK: 강아지/고양이 구분) */
  private int cat_no;
  
  /** 글 제목 (콘텐츠 제목) */
  private String title = "";
  
  /** 견종 이름 */
  private String breed_name = "";
  
  /** 상세 설명 (CLOB) */
  private String content = "";
  
  /** 추천 수 */
  private int recom = 0;
  
  /** 조회 수 */
  private int viewcnt = 0;
  
  
  /** 검색어/태그 */
  private String word = "";
  
  /** 작성일자 */
  private String rdate = "";
  
  /** 원산지 */
  private String origin = "";
  
  /** 체중 */
  private String kg = "";

  // --- Member(회원/작성자) 테이블 Join 필드 ---
  /** 작성자(관리자) 번호 (FK) */
  private int memberno;
  
  /** 작성자 ID */
  private String id = "";
  
  /** 작성자 닉네임 */
  private String nickname = "";
  
  /** 작성자 프로필 이미지 (썸네일) */
  private String thumbs = "";
  
  /** 작성자 등급 (0: 관리자, 1: 우수회원 등) */
  private int grade = 0;
  


}