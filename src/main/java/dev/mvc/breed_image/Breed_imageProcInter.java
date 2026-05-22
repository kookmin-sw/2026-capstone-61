package dev.mvc.breed_image;

import java.util.ArrayList;
import java.util.List;

public interface Breed_imageProcInter {

  /**
   * 이미지 정보 등록
   * @param breed_imageVO 등록할 이미지 객체
   * @return 등록된 레코드 갯수
   */
  public int create(Breed_imageVO breed_imageVO);

  /**
   * 특정 견종에 등록된 모든 이미지 목록 조회
   * @param breedno 견종 번호
   * @return 이미지 리스트
   */
  public List<Breed_imageVO> list_by_breedno(int breedno);

  /**
   * 목록 페이지용 대표 이미지(첫 번째 사진) 조회
   * @param breedno 견종 번호
   * @return 메인 이미지 객체
   */
  public Breed_imageVO read_main_img(int breedno);

  /**
   * 특정 이미지 상세 정보 조회
   * @param img_no 이미지 번호
   * @return 이미지 객체
   */
  public Breed_imageVO read(int img_no);

  /**
   * 개별 이미지 삭제 (수정 시 사용)
   * @param img_no 이미지 번호
   * @return 삭제된 레코드 갯수
   */
  public int delete(int img_no);

  /**
   * 견종 삭제 시 해당 견종의 모든 이미지 레코드 삭제
   * @param breedno 견종 번호
   * @return 삭제된 레코드 갯수
   */
  public int delete_by_breedno(int breedno);

  public ArrayList<Breed_imageVO> list_all(String word);
}