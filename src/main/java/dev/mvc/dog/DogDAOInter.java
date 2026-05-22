package dev.mvc.dog;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface DogDAOInter {
    public int create(DogVO dogVO);
    public List<DogVO> list_by_memberno(int memberno);
    public DogVO read(int dogno);
    
    // 🔹 추가: 수정 메서드
    public int update(DogVO dogVO);
    public int delete(int dogno);
	
	public void updateDbtiType(Map<String, Object> map);
}