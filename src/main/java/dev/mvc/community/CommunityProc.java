package dev.mvc.community;

import java.util.ArrayList;
import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("dev.mvc.community.CommunityProc")
public class CommunityProc implements CommunityProcInter {
  
  @Autowired
  private CommunityDAOInter communityDAO; // DAO 인터페이스와 연결

  @Override
  public int create(CommunityVO communityVO) {
    return this.communityDAO.create(communityVO);
  }

  @Override
  public ArrayList<CommunityVO> list_by_community_search_paging(HashMap<String, Object> map) {
    /* * 마이바티스 XML에서 cat_no, breedno, word를 동적으로 처리하도록 
     * 설계되었으므로 전달받은 map을 그대로 DAO로 넘깁니다.
     */
    return this.communityDAO.list_by_community_search_paging(map);
  }

  @Override
  public int search_count(HashMap<String, Object> map) {
    return this.communityDAO.search_count(map);
  }

  @Override
  public CommunityVO read(int communityno) {
    // 1. 상세 조회 시 조회수(viewcnt) 1 증가
    this.communityDAO.update_viewcnt(communityno);
    // 2. 해당 게시글 데이터 가져오기
    return this.communityDAO.read(communityno);
  }

  @Override
  public CommunityVO read_update(int communityno) {
    // 수정 폼을 띄울 때는 본인 확인용이므로 조회수가 올라가지 않게 read만 실행
    return this.communityDAO.read(communityno);
  }

  @Override
  public int update_text(CommunityVO communityVO) {
    return this.communityDAO.update_text(communityVO);
  }

  @Override
  public int delete(int communityno) {
    return this.communityDAO.delete(communityno);
  }

  @Override
  public int update(CommunityVO communityVO) {
    
    return this.communityDAO.update(communityVO); 
  }

}