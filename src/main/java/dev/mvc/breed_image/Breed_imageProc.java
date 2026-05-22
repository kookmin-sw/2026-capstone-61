package dev.mvc.breed_image;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("dev.mvc.breed_image.Breed_imageProc")
public class Breed_imageProc implements Breed_imageProcInter {
  
  @Autowired
  private Breed_imageDAOInter breed_imageDAO;

  /**
   * 이미지 등록
   */
  @Override
  public int create(Breed_imageVO breed_imageVO) {
    int cnt = this.breed_imageDAO.create(breed_imageVO);
    return cnt;
  }

  /**
   * 특정 견종의 모든 이미지 목록 조회
   */
  @Override
  public List<Breed_imageVO> list_by_breedno(int breedno) {
    List<Breed_imageVO> list = this.breed_imageDAO.list_by_breedno(breedno);
    return list;
  }

  /**
   * 대표 이미지 1장 조회
   */
  @Override
  public Breed_imageVO read_main_img(int breedno) {
    Breed_imageVO breed_imageVO = this.breed_imageDAO.read_main_img(breedno);
    return breed_imageVO;
  }

  /**
   * 특정 이미지 정보 조회
   */
  @Override
  public Breed_imageVO read(int img_no) {
    Breed_imageVO breed_imageVO = this.breed_imageDAO.read(img_no);
    return breed_imageVO;
  }

  /**
   * 개별 이미지 삭제
   */
  @Override
  public int delete(int img_no) {
    int cnt = this.breed_imageDAO.delete(img_no);
    return cnt;
  }

  /**
   * 특정 견종의 이미지 모두 삭제
   */
  @Override
  public int delete_by_breedno(int breedno) {
    int cnt = this.breed_imageDAO.delete_by_breedno(breedno);
    return cnt;
  }
  @Override
  public ArrayList<Breed_imageVO> list_all(String word) {
      // DAO의 list_all 에도 word를 전달해줍니다.
      return this.breed_imageDAO.list_all(word);
  }
}