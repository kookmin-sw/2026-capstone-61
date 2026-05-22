package dev.mvc.dog;

import dev.mvc.tool.Tool;

// 파일 업로드 경로 관리 클래스
public class Dog {

    // 업로드 폴더 경로 반환 (운영체제별 분기)
    public static synchronized String getUploadDir() {
        return Tool.getUploadPath("dog", "storage");
    }
}