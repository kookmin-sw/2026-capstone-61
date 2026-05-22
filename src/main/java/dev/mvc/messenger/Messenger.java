package dev.mvc.messenger;

import dev.mvc.tool.Tool;

/**
 * Messenger 서비스 관련 설정 및 파일 업로드 경로 관리 클래스
 * 친구 목록 페이징 및 채팅 중 전송되는 파일(이미지 등)의 저장 경로를 관리합니다.
 */
public class Messenger {
    /** 
     * 친구 목록 페이지당 출력할 레코드 갯수 
     * (채팅방 리스트의 가독성을 위해 10개 정도를 추천합니다)
     */
    public static int RECORD_PER_PAGE = 10;

    /** 블럭당 페이지 수 (1 2 3 ... 10 [다음]) */
    public static int PAGE_PER_BLOCK = 10;

    /**
     * 메신저 대화 중 업로드되는 파일(이미지, 첨부파일 등)의 저장 경로 반환
     * messenger_data 테이블의 file_name 컬럼에 저장되는 파일들이 이곳에 저장됩니다.
     * 
     * @return messenger/storage 폴더의 절대 경로
     */
    public static synchronized String getUploadDir() {
        // "messenger"는 기능명, "storage"는 하위 폴더명으로 설정
        return Tool.getUploadPath("messenger", "storage");
    }
}