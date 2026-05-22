package dev.mvc.dog;

import org.springframework.web.multipart.MultipartFile;

/**
 * [강아지 정보 테이블(DOG) 대응 VO 클래스]
 * 회원의 반려견 정보와 프로필 사진 파일 정보를 담아 나르는 객체입니다.
 */
public class DogVO {

    /* --- DB 컬럼과 매칭되는 필드 (Field) --- */

    /** 강아지 고유 번호 (PK, 자동증가) */
    private int dogno;

    /** 주인 회원 번호 (FK, MEMBER 테이블 참조) */
    private int memberno;

    /** 강아지 이름 */
    private String name = "";

    /** 견종 (예: 말티즈, 푸들) */
    private String breed = "";

    /** 나이 */
    private int age;

    /** 성별 (M: 수컷, F: 암컷) */
    private String gender = "";

    /** 원본 파일명 (사용자가 올린 이름) */
    private String file1 = "";

    /** 서버 저장 파일명 (중복 방지용) */
    private String file1saved = "";

    /** 썸네일 이미지 파일명 (작은 이미지) */
    private String thumb1 = "";

    /** 파일 크기 (용량) */
    private long size1;

    /** DBTI 검사 결과 (예: ESFP) */
    private String dbti_type = "";

    /** 등록일 */
    private String rdate = "";

    /* --- 파일 업로드를 위한 필드 (DB 저장 안함) --- */

    /** * [스프링 파일 업로드 핵심] 
     * <input type="file" name="file1MF"> 태그의 데이터를 받는 객체입니다.
     */
    private MultipartFile file1MF;


    /* --- Getter & Setter (데이터 접근용) --- */

    public int getDogno() { return dogno; }
    public void setDogno(int dogno) { this.dogno = dogno; }

    public int getMemberno() { return memberno; }
    public void setMemberno(int memberno) { this.memberno = memberno; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getBreed() { return breed; }
    public void setBreed(String breed) { this.breed = breed; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public String getFile1() { return file1; }
    public void setFile1(String file1) { this.file1 = file1; }

    public String getFile1saved() { return file1saved; }
    public void setFile1saved(String file1saved) { this.file1saved = file1saved; }

    public String getThumb1() { return thumb1; }
    public void setThumb1(String thumb1) { this.thumb1 = thumb1; }

    public long getSize1() { return size1; }
    public void setSize1(long size1) { this.size1 = size1; }

    public String getDbti_type() { return dbti_type; }
    public void setDbti_type(String dbti_type) { this.dbti_type = dbti_type; }

    public String getRdate() { return rdate; }
    public void setRdate(String rdate) { this.rdate = rdate; }

    public MultipartFile getFile1MF() { return file1MF; }
    public void setFile1MF(MultipartFile file1MF) { this.file1MF = file1MF; }

    /* --- ToString (디버깅용) --- */

    @Override
    public String toString() {
        return "DogVO [dogno=" + dogno + ", memberno=" + memberno + ", name=" + name + ", breed=" + breed + ", age="
                + age + ", gender=" + gender + ", file1=" + file1 + ", dbti_type=" + dbti_type + "]";
    }
}