package dev.mvc.market_review;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class MarketReviewVO {
    
    /** 리뷰 고유 번호 (PK) */
    private int review_id;
    
    /** 대상 상품 번호 (FK) */
    private int product_id;
    
    /** 작성자 회원 번호 (FK) */
    private int memberno;
    
    /** 주문 상세 번호 (FK) - 실제 구매 확인용 */
    private int order_item_id;
    
    /** 별점 (1~5점) */
    private int rating;

    /** 리뷰 내용 */
    private String review_content;
    
    /** 리뷰 이미지 (파일 경로 또는 저장된 파일명) */
    private String review_img;
    
    /** 등록 일자 */
    private String reg_date;

    // ---------------------------------------------------------
    // 가공 필드 (JOIN을 통해 가져오거나 화면 출력용으로 추가)
    // ---------------------------------------------------------
    
    /** 작성자 성명 (MEMBER 테이블 JOIN) */
    private String mname;
    
    /** 작성자 아이디 (MEMBER 테이블 JOIN) */
    private String id;
    
    /** 상품 이름 (MARKET 테이블 JOIN) */
    private String product_name;
    
    /** 업로드할 이미지 파일 (MultipartFile은 컨트롤러에서 처리 후 파일명만 VO에 저장) */
    // private MultipartFile review_img_file; 
}