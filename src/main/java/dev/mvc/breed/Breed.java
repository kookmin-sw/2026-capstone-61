package dev.mvc.breed;

import dev.mvc.tool.Tool;

/**
 * Breed 서비스 관련 설정 및 파일 업로드 경로 관리 클래스
 * 파일 업로드 경로는 war 외부의 절대경로를 지정하여 재배포 시 파일 손실을 방지합니다.
 */
public class Breed {
    /** * 페이지당 출력할 레코드 갯수 
     * (사전형 레이아웃인 3열 Grid 구성을 위해 6개 또는 9개를 추천합니다)
     */
    public static int RECORD_PER_PAGE = 24;

    /** 블럭당 페이지 수 (1 2 3 ... 10 [다음]) */
    public static int PAGE_PER_BLOCK = 10;

    /**
     * 운영체제별 파일 업로드 절대 경로 반환
     * @return 저장 폴더 절대 경로
     */
    public static synchronized String getUploadDir() {
        return Tool.getUploadPath("breed", "storage");
    }
}