package dev.mvc.community;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class CommunityVO {
  /** 커뮤니티 게시글 번호 (PK) */
  private int communityno;
  
  /** [대장] 대분류 번호 (Category 테이블 연동: 소형견, 중형견, 대형견 등) */
  private int cat_no;

  /** 회원 번호 (Member 테이블 연동: 작성자 식별) */
  private int memberno;
  
  /** 작성자 닉네임 (게시글 목록 및 상세보기에 표시) */
  private String nickname;
  
  /** [필터링용] 연관 견종 번호 (Breed 테이블 연동: 리트리버, 포들 등) 
      - 0: 견종 미지정(일반글), 1이상: 특정 견종 관련글 */
  private int breedno;
  
  /** 게시판 분류 (1:자유, 2:정보, 3:질문, 4:후기) */
  private int cateno;
  
  /** 게시글 제목 */
  private String title;
  
  /** 게시글 내용 (DB의 CLOB 타입 대응) */
  private String content;
  
  /** 조회수 */
  private int viewcnt;
  
  /** 추천수 */
  private int recom;
  
  /** 댓글 수 (게시글 목록에서 [5] 처럼 표시하는 용도) */
  private int replycnt;
  
  /** 등록일 (DB의 DATE 타입 대응) */
  private String rdate;
  
  /** 게시글 수정/삭제용 비밀번호 */
  private String passwd;
  
  /** 검색어 및 해시태그 (예: #산책 #강아지간식) */
  private String word;
  
//-------------------------------------------------------------------------
 // [추가 코드] 갤러리 목록 출력용 필드
 // -------------------------------------------------------------------------
 /** * 메인 갤러리 목록에 표시될 썸네일 이미지 파일명 
  * (ATTACHFILE 테이블의 thumb 컬럼 값을 Join으로 가져와서 저장함)
  */
 private String thumb = "";
}