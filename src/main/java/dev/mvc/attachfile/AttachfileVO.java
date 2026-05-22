package dev.mvc.attachfile;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class AttachfileVO {
    /** 첨부파일 일련번호 (PK) */
    private int attachfileno;

    /** 부모 게시글 번호 (Community 테이블의 FK) */
    private int communityno;

    /** 사용자가 업로드한 원본 파일명 (예: my_dog.jpg) */
    private String fname = "";

    /** 서버 시스템에 실제 저장된 파일명 (중복 방지를 위해 나노초 등이 붙은 이름) */
    private String fupname = "";

    /** 리스트(갤러리) 화면에서 보여줄 작은 크기의 썸네일 파일명 */
    private String thumb = "";

    /** 파일의 용량 (단위: Byte) */
    private long fsize;

    /** 사진이 등록된 날짜 및 시간 */
    private String rdate = "";

    /** * [핵심] 강아지 사진 판별 여부 
     * 'Y': 강아지 사진으로 확인됨 (갤러리 노출)
     * 'N': 강아지 사진이 아니거나 검토 중 (갤러리 숨김)
     * 기본값은 DB 설정에 따라 'N'으로 시작합니다.
     */
    private String is_dog = "N";
}