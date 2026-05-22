package dev.mvc.attachfile;

import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

// @Service 또는 @Component 어노테이션을 사용하여 스프링 컨테이너에 빈(Bean)으로 등록합니다.
@Component("dev.mvc.attachfile.AttachfileProc")
public class AttachfileProc implements AttachfileProcInter {

  @Autowired
  private AttachfileDAOInter attachfileDAO; // DAO 인터페이스 주입

  /**
   * 1. 첨부파일 정보 DB 등록
   * @param attachfileVO 등록할 파일 객체
   * @return 성공 시 1 반환
   */
  @Override
  public int create(AttachfileVO attachfileVO) {
    int cnt = this.attachfileDAO.create(attachfileVO);
    return cnt;
  }

  /**
   * 2. 특정 게시글의 모든 사진 목록 조회
   * @param communityno 게시글 번호
   * @return 사진 리스트 반환
   */
  @Override
  public List<AttachfileVO> list_by_communityno(int communityno) {
    List<AttachfileVO> list = this.attachfileDAO.list_by_communityno(communityno);
    return list;
  }

  /**
   * 3. 강아지 사진 판별 업데이트 (승인/거절)
   * @param attachfileno 파일 번호
   * @param is_dog 'Y' 또는 'N'
   * @return 수정된 행의 수
   */
  @Override
  public int update_is_dog(int attachfileno, String is_dog) {
    // 파라미터가 2개이므로 HashMap에 담아 DAO로 전달합니다.
    HashMap<String, Object> map = new HashMap<String, Object>();
    map.put("attachfileno", attachfileno);
    map.put("is_dog", is_dog);
    
    int cnt = this.attachfileDAO.update_is_dog(map);
    return cnt;
  }

  /**
   * 4. 갤러리 메인용 썸네일 조회 (강아지로 판별된 사진 중 첫 번째 것)
   * @param communityno 게시글 번호
   * @return 조건에 맞는 썸네일 정보
   */
  @Override
  public AttachfileVO read_thumb_dog(int communityno) {
    AttachfileVO attachfileVO = this.attachfileDAO.read_thumb_dog(communityno);
    return attachfileVO;
  }

  /**
   * 5. 특정 파일 정보 삭제 (개별 사진 삭제 시)
   * @param attachfileno 파일 고유 번호
   * @return 삭제된 행의 수
   */
  @Override
  public int delete(int attachfileno) {
    int cnt = this.attachfileDAO.delete(attachfileno);
    return cnt;
  }

  /**
   * 6. 특정 게시글의 모든 파일 정보 삭제
   * @param communityno 게시글 번호
   * @return 삭제된 파일의 총 개수
   */
  @Override
  public int delete_by_communityno(int communityno) {
    int cnt = this.attachfileDAO.delete_by_communityno(communityno);
    return cnt;
  }

}