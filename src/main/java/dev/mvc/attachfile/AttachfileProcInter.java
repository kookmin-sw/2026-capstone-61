package dev.mvc.attachfile;

import java.util.List;

/**
 * 첨부파일(이미지) 처리 비즈니스 로직 규격 정의
 */
public interface AttachfileProcInter {

  /**
   * 1. 첨부파일 정보 DB 등록 (게시글 작성 시 반복 호출)
   * @param attachfileVO 등록할 파일 객체
   * @return 성공 시 1, 실패 시 0 반환
   */
  public int create(AttachfileVO attachfileVO);

  /**
   * 2. 특정 게시글에 등록된 모든 사진 목록 불러오기 (상세보기용)
   * @param communityno 부모 게시글 번호
   * @return 사진 객체 리스트
   */
  public List<AttachfileVO> list_by_communityno(int communityno);

  /**
   * 3. 강아지 사진 판별 결과 업데이트 (승인/숨김 처리)
   * @param attachfileno 수정할 파일의 고유 번호
   * @param is_dog 'Y'(강아지 맞음) 또는 'N'(엉뚱한 사진)
   * @return 수정된 행의 수
   */
  public int update_is_dog(int attachfileno, String is_dog);

  /**
   * 4. 갤러리 메인 리스트용 첫 번째 강아지 썸네일 조회
   * @param communityno 부모 게시글 번호
   * @return 조건에 맞는 썸네일 정보 객체
   */
  public AttachfileVO read_thumb_dog(int communityno);

  /**
   * 5. 특정 파일 정보 삭제 (게시글 수정 중 개별 사진만 지울 때)
   * @param attachfileno 삭제할 파일 번호
   * @return 삭제된 행의 수
   */
  public int delete(int attachfileno);

  /**
   * 6. 특정 게시글의 모든 파일 정보 일괄 삭제 (게시글 삭제 연동용)
   * @param communityno 부모 게시글 번호
   * @return 삭제된 파일의 총 개수
   */
  public int delete_by_communityno(int communityno);
  
}