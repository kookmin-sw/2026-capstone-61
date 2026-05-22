package dev.mvc.dog_dbti;

/**
 * DBTI(Dog MBTI) 관련 비즈니스 로직 인터페이스
 */
public interface DogDbtiProcInter {

    /**
     * 상세 수치를 바탕으로 4자리 성향 코드를 생성합니다.
     */
    public String generateDbtiType(DogDbtiVO vo);

    /**
     * 특정 강아지의 최근 검사 결과를 조회합니다.
     */
    public DogDbtiVO read(int dogno);

    /**
     * 검사 결과를 저장합니다.
     */
    public int create_result(DogDbtiVO vo);

    /**
     * 특정 강아지의 검사 기록을 삭제합니다.
     */
    public int delete(int dogno); 
}