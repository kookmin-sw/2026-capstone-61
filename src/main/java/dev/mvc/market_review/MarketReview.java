package dev.mvc.market_review;

import dev.mvc.tool.Tool;

/**
 * MarketReview 서비스 관련 설정 및 파일 업로드 경로 관리 클래스
 */
public class MarketReview {
    /** 페이지당 출력할 레코드 갯수 (리뷰는 보통 상품 상세 하단에 5~10개씩 보여줌) */
    public static int RECORD_PER_PAGE = 5;

    /** 블럭당 페이지 수 */
    public static int PAGE_PER_BLOCK = 10;

    /**
     * 운영체제별 리뷰 파일 업로드 절대 경로 반환
     */
    public static synchronized String getUploadDir() {
        return Tool.getUploadPath("market_review", "storage");
    }
}