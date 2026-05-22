package dev.mvc.attachfile;

import java.util.HashMap;
import java.util.List;

public interface AttachfileDAOInter {
  
  /**
   * 1. 첨부파일 정보 등록 (게시글 작성 시 호출)
   * @param attachfileVO 등록할 파일 정보 객체
   * @return 등록된 레코드 수 (성공 시 1)
   */
  public int create(AttachfileVO attachfileVO);

  /**
   * 2. 특정 게시글에 속한 모든 사진 목록 조회 (상세 보기 페이지용)
   * @param communityno 부모 게시글 번호
   * @return 사진 객체들이 담긴 리스트
   */
  public List<AttachfileVO> list_by_communityno(int communityno);

  /**
   * 3. 강아지 사진 판별 결과 업데이트 (관리자 승인 또는 AI 결과 반영)
   * @param map "attachfileno": 파일번호, "is_dog": 'Y' 또는 'N'을 담은 맵
   * @return 수정된 레코드 수
   */
  public int update_is_dog(HashMap<String, Object> map);

  /**
   * 4. 갤러리 목록용 썸네일 정보 조회 (강아지 사진 중 첫 번째 것만)
   * @param communityno 부모 게시글 번호
   * @return 조건에 맞는 썸네일 정보 객체
   */
  public AttachfileVO read_thumb_dog(int communityno);

  /**
   * 5. 특정 파일 정보 삭제 (게시글 수정 중 사진 한 장만 지울 때 사용)
   * @param attachfileno 삭제할 파일의 고유 번호
   * @return 삭제된 레코드 수
   */
  public int delete(int attachfileno);

  /**
   * 6. 특정 게시글의 모든 파일 정보 삭제 (게시글 삭제 시 연쇄 삭제용)
   * @param communityno 부모 게시글 번호
   * @return 삭제된 파일 정보의 총 개수
   */
  public int delete_by_communityno(int communityno);
  
}