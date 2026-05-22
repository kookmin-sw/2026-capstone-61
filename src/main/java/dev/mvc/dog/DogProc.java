package dev.mvc.dog;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("dev.mvc.dog.DogProc") // 컨트롤러에서 주입받을 이름
public class DogProc implements DogProcInter {

    @Autowired
    private DogDAOInter dogDAO; // SqlSession 대신 인터페이스 주입

    // 🔹 강아지 등록
    @Override
    public int create(DogVO dogVO) {
        // 👉 null 체크 (실전에서 중요)
        if (dogVO == null) {
            return 0;
        }
        return dogDAO.create(dogVO); // 인터페이스 메서드 직접 호출
    }

    // 🔹 강아지 목록
    @Override
    public List<DogVO> list_by_memberno(int memberno) {
        // 👉 memberno 체크
        if (memberno <= 0) {
            return null;
        }
        return dogDAO.list_by_memberno(memberno);
    }
 // 🔹 강아지 상세 정보 조회
    @Override
    public DogVO read(int dogno) {
        // 👉 dogno 체크
        if (dogno <= 0) {
            return null;
        }
        return dogDAO.read(dogno);
    }
 // 🔹 강아지 정보 수정
    @Override
    public int update(DogVO dogVO) {
        // 데이터 검증
        if (dogVO == null || dogVO.getDogno() <= 0) {
            return 0;
        }
        return dogDAO.update(dogVO);
    }
    @Override
    public int delete(int dogno) {
        if (dogno <= 0) {
            return 0;
        }
        return dogDAO.delete(dogno);
    }
}