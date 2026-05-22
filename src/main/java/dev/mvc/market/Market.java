package dev.mvc.market;

import dev.mvc.tool.Tool;

/**
 * Market 서비스 관련 설정 및 파일 업로드 경로 관리 클래스
 */
public class Market {
    /** 페이지당 출력할 레코드 갯수 */
    public static int RECORD_PER_PAGE = 8;

    /** 블럭당 페이지 수 */
    public static int PAGE_PER_BLOCK = 10;

    /**
     * 운영체제별 파일 업로드 절대 경로 반환
     */
    public static synchronized String getUploadDir() {
        return Tool.getUploadPath("product", "storage");
    }
}