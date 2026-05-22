package dev.mvc.comments;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import dev.mvc.member.MemberProcInter;
import dev.mvc.member.MemberVO;

import jakarta.servlet.http.HttpSession;

@RequestMapping(value = "/comments")
@Controller
public class CommentsCont {

  @Autowired
  @Qualifier("dev.mvc.comments.CommentsProc")
  private CommentsProcInter commentsProc;

  @Autowired
  @Qualifier("dev.mvc.member.MemberProc")
  private MemberProcInter memberProc;



  public CommentsCont() {
    System.out.println("-> CommentsCont Created");
  }

  /**
   * 댓글 생성 (커뮤니티)
   */
  @PostMapping(value = "/create")
  @ResponseBody
  public String create(HttpSession session, @RequestBody CommentsVO commentsVO) {
    JSONObject json = new JSONObject();
    
    // 세션에서 회원 번호 가져오기
    Object memberno_obj = session.getAttribute("memberno");
    if (memberno_obj == null) {
      json.put("res", -1); // 로그인 필요
      return json.toString();
    }

    int memberno = (int) memberno_obj;
    commentsVO.setMemberno(memberno);

    // 🔥 추가: memberno를 통해 회원 정보를 조회하여 닉네임 세팅
    // memberProc에 read(int memberno) 메소드가 있다고 가정합니다.
    MemberVO memberVO = this.memberProc.read(memberno);
    if (memberVO == null) {
      json.put("res", -2);
      return json.toString();
    }
    commentsVO.setNickname(memberVO.getNickname());

    int cnt = this.commentsProc.create(commentsVO);

    json.put("res", cnt);
    return json.toString();
  }

  /**
   * 커뮤니티 게시글 댓글 목록
   */
  @GetMapping(value = "/list_by_community")
  @ResponseBody
  public String list_by_community(int communityno) {

    ArrayList<CommentsVO> list = this.commentsProc.list_by_community(communityno);
    JSONArray jsonArray = new JSONArray();

    for (CommentsVO vo : list) {
      JSONObject obj = new JSONObject();

      obj.put("comment_no", vo.getComment_no());
      obj.put("communityno", vo.getCommunityno());
      obj.put("memberno", vo.getMemberno());
      obj.put("nickname", vo.getNickname());
      obj.put("content", vo.getContent());
      obj.put("parent_no", vo.getParent_no());
      obj.put("depth", vo.getDepth());
      obj.put("rdate", vo.getRdate());

      jsonArray.put(obj);
    }

    JSONObject result = new JSONObject();
    result.put("res", jsonArray);

    return result.toString();
  }

  /**
   * 댓글 조회
   */
  @GetMapping(value = "/read", produces = "application/json")
  @ResponseBody
  public String read(int comment_no) {

    CommentsVO vo = this.commentsProc.read(comment_no);

    JSONObject obj = new JSONObject();
    obj.put("comment_no", vo.getComment_no());
    obj.put("communityno", vo.getCommunityno());
    obj.put("memberno", vo.getMemberno());
    obj.put("nickname", vo.getNickname());
    obj.put("content", vo.getContent());
    obj.put("parent_no", vo.getParent_no());
    obj.put("depth", vo.getDepth());
    obj.put("rdate", vo.getRdate());

    JSONObject result = new JSONObject();
    result.put("res", obj);

    return result.toString();
  }

  /**
   * 댓글 수정
   */
  @PostMapping(value = "/update")
  @ResponseBody
  public String update(HttpSession session, @RequestBody CommentsVO commentsVO) {

    Object membernoObj = session.getAttribute("memberno");
    if (membernoObj == null) {
      JSONObject json = new JSONObject();
      json.put("res", -1);
      return json.toString();
    }
    int memberno = (int) membernoObj;

    int cnt = 0;
    if (memberno == commentsVO.getMemberno()) {
      cnt = this.commentsProc.update(commentsVO);
    }

    JSONObject json = new JSONObject();
    json.put("res", cnt);

    return json.toString();
  }

  /**
   * 댓글 삭제
   */
  @PostMapping(value = "/delete")
  @ResponseBody
  public String delete(HttpSession session, @RequestBody CommentsVO commentsVO) {

    Object membernoObj = session.getAttribute("memberno");
    if (membernoObj == null) {
      JSONObject json = new JSONObject();
      json.put("res", -1);
      return json.toString();
    }
    int memberno = (int) membernoObj;

    int cnt = 0;
    if (memberno == commentsVO.getMemberno()) {
      cnt = this.commentsProc.delete(commentsVO.getComment_no());
    }

    JSONObject json = new JSONObject();
    json.put("res", cnt);

    return json.toString();
  }
}