package dev.mvc.match_post;

import java.math.BigDecimal; // BigDecimal 추가
import java.util.List;
import org.springframework.web.multipart.MultipartFile;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MatchPostVO {

  /** 매칭 번호 */
  private int matchno;

  /** 회원 번호 */
  private int memberno;

  /** 카테고리 번호 */
  private int cat_no;

  /** 제목 */
  private String title = "";

  /** 내용 */
  private String content = "";

  /** 약속 시간 (TIMESTAMP -> String 매핑) */
  private String mdate = "";

  /** 약속 장소 */
  private String mplace = "";

  /** 상세 장소 */
  private String mplace_detail = "";

  /** 
   * 위도 (보완: double -> BigDecimal) 
   * Oracle NUMBER(15, 10) 대응
   */
  private BigDecimal lat = new BigDecimal("0.0");

  /** 
   * 경도 (보완: double -> BigDecimal) 
   * Oracle NUMBER(15, 10) 대응
   */
  private BigDecimal lng = new BigDecimal("0.0");
  
  /** 사용자와의 거리(km) */
  private double distance;

  /** DMTI (강아지 성향 지표) */
  private String dmti = "";

  /** 반려견 메인 이미지명 (저장된 파일명) */
  private String dog_img = "";
  
  /** 실제 업로드된 파일 크기 (선택 사항) */
  private long dog_img_size;

  /** 다중 파일 업로드 처리용 */
  private List<MultipartFile> files;

  /** 
   * 모집 상태
   * 1:모집중, 2:모집마감, 3:산책중, 4:산책완료
   */
  private int status = 1;

  /** 최대 모집 인원 */
  private int max_member = 2;

  /** 현재 모집 인원 */
  private int current_member = 1;

  /** 조회수 */
  private int viewcnt = 0;

  /** 댓글 수 */
  private int commentcnt = 0;

  /** 삭제 여부 (Y, N) */
  private String is_deleted = "N";

  /** 모집 마감 시간 */
  private String close_date = "";

  /** 작성일 */
  private String rdate = "";

  /* =========================================================
     📢 JOIN 및 조회용 추가 필드
  ========================================================= */
  
  /** 작성자 닉네임 (MEMBER 테이블 JOIN) */
  private String nickname = "";

  /** 평균 매너 점수 (MEMBER 테이블 JOIN) */
  private double manner_score = 0.0;

  /** 매칭 참여 횟수 */
  private int match_cnt = 0;

  /** 카테고리 이름 (CATEGORY 테이블 JOIN) */
  private String cat_name = "";

  /* =========================================================
     🔎 페이징 및 검색 필드
  ========================================================= */
  
  /** 검색어 */
  private String word = "";

  /** 시작 번호 */
  private int start_num;

  /** 끝 번호 */
  private int end_num;

  /** 현재 페이지 번호 */
  private int now_page = 1;

  /* =========================================================
     📝 최근 리뷰 내용
  ========================================================= */
  private String review_content = "";

}