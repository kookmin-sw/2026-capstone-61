package dev.mvc.dog_dbti;

import java.util.Map;

public interface DogDbtiDAOInter {
    // DBTI 결과 등록
    int create(DogDbtiVO dogDbtiVO);

    // 결과 조회
    DogDbtiVO read(int dogno);

    // 기존 결과 삭제 (재검사 시 필요)
    int delete(int dogno);

    // DOG 테이블의 DBTI 유형 업데이트
    int updateDogTableType(Map<String, Object> map);
}