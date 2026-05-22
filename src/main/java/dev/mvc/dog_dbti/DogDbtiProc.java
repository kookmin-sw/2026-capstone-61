package dev.mvc.dog_dbti;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("dev.mvc.dog_dbti.DogDbtiProc")
public class DogDbtiProc implements DogDbtiProcInter {

    @Autowired
    private DogDbtiDAOInter dogDbtiDAO;

    @Override
    public String generateDbtiType(DogDbtiVO vo) {
        StringBuilder type = new StringBuilder();
        type.append(vo.getEPer() >= 50 ? "E" : "I");
        type.append(vo.getSPer() >= 50 ? "S" : "N");
        type.append(vo.getAPer() >= 50 ? "A" : "T");
        type.append(vo.getOPer() >= 50 ? "O" : "U");
        return type.toString();
    }

    @Override
    public DogDbtiVO read(int dogNo) {
        return this.dogDbtiDAO.read(dogNo);
    }

    @Transactional
    @Override
    public int create_result(DogDbtiVO vo) {
        int cnt = 0;
        try {
            this.dogDbtiDAO.delete(vo.getDogno());
            cnt = this.dogDbtiDAO.create(vo);
            String dbtiType = this.generateDbtiType(vo);

            Map<String, Object> map = new HashMap<>();
            map.put("dogno", vo.getDogno());
            map.put("dbti_type", dbtiType);
            this.dogDbtiDAO.updateDogTableType(map);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
        return cnt;
    }

    
    @Override
    public int delete(int dogno) {
        return this.dogDbtiDAO.delete(dogno);
    }
}