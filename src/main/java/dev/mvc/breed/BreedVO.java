package dev.mvc.breed;

import org.springframework.web.multipart.MultipartFile;

import dev.mvc.breed_image.Breed_imageVO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class BreedVO {
    /** 견종 식별 번호 (PK) */
    private int breedno;
    
    /** 카테고리 번호 (FK: 강아지/고양이 구분) */
    private int cat_no;
    
    /** 등록자 번호 (FK: 관리자 번호) */
    private int memberno;
    
    /** 견종 이름 */
    private String breed_name = "";
    
    /** 콘텐츠 제목 */
    private String title = "";
    
    /** 상세 설명 (CLOB 대응) */
    private String content = "";

    
    /** 추천 수 */
    private int recom = 0;
    
    /** 조회 수 */
    private int viewcnt = 0;
    
    /** 원산지 */
    private String origin = "";
    
    /** 평균 몸무게 */
    private String kg = "";
    
    /** 평균 체고 (키) */
    private String height = "";
    
    /** 크기 구분 (소형견, 중형견, 대형견) */
    private String size_type = "";
    
    /** 등록일 */
    private String rdate = "";
    private Breed_imageVO mainImg;


}