package dev.mvc.breed_image;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class Breed_imageVO {
    private int img_no;           // 이미지 번호
    private int breedno;          // 견종 번호 (FK)
    private String original_name; // 원본 파일명
    private String saved_name;    // 저장된 파일명
    private String file_path;     // 저장 경로
    private String thumb_path;    // 썸네일 경로
    private long f_size;          // 파일 크기
    
    private String breed_name;
}