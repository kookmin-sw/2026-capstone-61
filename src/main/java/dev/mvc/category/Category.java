package dev.mvc.category;

import dev.mvc.tool.Tool;

public class Category {
    /** 페이지당 출력할 레코드 갯수 */
    public static int RECORD_PER_PAGE = 10;

    /** 블럭당 페이지 수 (하단 [1][2]... 개수) */
    public static int PAGE_PER_BLOCK = 10;

    /**
     * 카테고리 관련 이미지 및 파일 저장 절대 경로 설정
     * Windows, VMWare, AWS cloud 환경 대응
     */
    public static synchronized String getUploadDir() {
        return Tool.getUploadPath("category", "storage");
    }
}