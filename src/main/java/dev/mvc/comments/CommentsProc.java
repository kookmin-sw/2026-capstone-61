package dev.mvc.comments;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

// 🔥 communityDAO를 사용하기 위해 import 추가
import dev.mvc.community.CommunityDAOInter; 

@Component("dev.mvc.comments.CommentsProc")
public class CommentsProc implements CommentsProcInter {
  
  @Autowired
  private CommentsDAOInter commentsDAO;

  // 🔥 community 테이블의 replycnt를 수정하기 위해 추가
  @Autowired
  private CommunityDAOInter communityDAO; 

  /**
   * 댓글 생성
   */
  @Override
  public int create(CommentsVO commentsVO) {
      int cnt = this.commentsDAO.create(commentsVO);
      
      if (cnt == 1) {
          // null 체크를 먼저 해서 NullPointerException을 방지합니다.
          if (commentsVO.getParent_no() == null || commentsVO.getParent_no() == 0) {
              Map<String, Object> map = new HashMap<>();
              map.put("communityno", commentsVO.getCommunityno());
              map.put("mode", "+");
              this.communityDAO.updateReplycnt(map);
          }
      }
      return cnt;
  }

  /**
   * 댓글 삭제 (소프트 삭제)
   */
  @Override
  public int delete(int comment_no) {
    // 🔥 삭제 시에도 원댓글인 경우 replycnt를 하나 줄여야 숫자가 맞습니다.
    CommentsVO vo = this.commentsDAO.read(comment_no); // 삭제 전 정보 조회
    int cnt = this.commentsDAO.delete(comment_no); // 1. 삭제(is_deleted='Y') 처리
    
    if (cnt == 1 && vo != null) {
      // 2. 삭제된 댓글이 원댓글(parent_no == 0)이었다면 replycnt 감소
      if (vo.getParent_no() == 0) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("communityno", vo.getCommunityno());
        map.put("mode", "-"); // 감소 모드
        
        this.communityDAO.updateReplycnt(map);
      }
    }
    
    return cnt;
  }

  /**
   * 게시글 댓글 목록
   */
  @Override
  public ArrayList<CommentsVO> list_by_community(int communityno) {
    return this.commentsDAO.list_by_community(communityno);
  }

  /**
   * 댓글 1건 조회
   */
  @Override
  public CommentsVO read(int comment_no) {
    return this.commentsDAO.read(comment_no);
  }

  /**
   * 댓글 개수 (전체 데이터 개수 조회가 필요한 경우)
   */
  @Override
  public int count_by_community(int communityno) {
    return this.commentsDAO.count_by_community(communityno);
  }

  /**
   * 댓글 수정
   */
  @Override
  public int update(CommentsVO commentsVO) {
    return this.commentsDAO.update(commentsVO);
  }
}