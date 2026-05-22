package dev.mvc.dog;

import java.util.List;

public interface DogProcInter {
    public int create(DogVO dogVO);
    public List<DogVO> list_by_memberno(int memberno);
	public DogVO read(int dogno);
	public int update(DogVO dogVO);
	public int delete(int dogno);
}