package dev.mvc.match_comment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/match_comment")
public class MatchCommentCont {

  @Autowired
  private MatchCommentProcInter matchCommentProc;


  /* =========================================================
     📝 댓글 등록
  ========================================================= */

  /**
   * 댓글 등록 처리
   */
  @ResponseBody
  @PostMapping("/create")
  public Map<String, Object> create(
      MatchCommentVO matchCommentVO,
      @SessionAttribute(name="memberno", required=false)
      Integer memberno) {

    Map<String, Object> map =
        new HashMap<String, Object>();

    try {

      // 로그인 체크
      if (memberno == null) {

        map.put("res", "login_required");

        return map;
      }

      // 작성자 설정
      matchCommentVO.setMemberno(memberno);

      // 댓글 타입 설정
      if (matchCommentVO.getParentno() == null) {

        // 일반 댓글
        matchCommentVO.setComment_type(1);

      } else {

        // 대댓글
        matchCommentVO.setComment_type(2);
      }

      // 댓글 등록
      int cnt =
          this.matchCommentProc.create(matchCommentVO);

      if (cnt == 1) {

        map.put("res", "success");

      } else {

        map.put("res", "fail");
      }

    } catch(Exception e) {

      e.printStackTrace();

      map.put("res", "error");
    }

    return map;
  }


  /* =========================================================
     📋 게시글 댓글 목록
  ========================================================= */

  /**
   * 게시글 댓글 목록 조회
   */
  @GetMapping("/list_by_matchno/{matchno}")
  public ModelAndView list_by_matchno(
      @PathVariable("matchno") int matchno) {

    ModelAndView mav =
        new ModelAndView();

    ArrayList<MatchCommentVO> list =
        this.matchCommentProc.list_by_matchno(matchno);

    mav.addObject("list", list);

    mav.addObject("matchno", matchno);

    mav.setViewName(
        "match_comment/list_by_matchno");

    return mav;
  }


  /* =========================================================
     📖 댓글 상세 조회
  ========================================================= */

  /**
   * 댓글 상세 조회
   */
  @GetMapping("/read/{commentno}")
  public ModelAndView read(
      @PathVariable("commentno") int commentno) {

    ModelAndView mav =
        new ModelAndView();

    MatchCommentVO matchCommentVO =
        this.matchCommentProc.read(commentno);

    mav.addObject(
        "matchCommentVO",
        matchCommentVO
    );

    mav.setViewName("match_comment/read");

    return mav;
  }


  /* =========================================================
     ✏ 댓글 수정 폼
  ========================================================= */

  /**
   * 댓글 수정 폼
   */
  @GetMapping("/update/{commentno}")
  public ModelAndView update(
      @PathVariable("commentno") int commentno) {

    ModelAndView mav =
        new ModelAndView();

    MatchCommentVO matchCommentVO =
        this.matchCommentProc.read(commentno);

    mav.addObject(
        "matchCommentVO",
        matchCommentVO
    );

    mav.setViewName("match_comment/update");

    return mav;
  }


  /* =========================================================
     ✏ 댓글 수정 처리
  ========================================================= */

  /**
   * 댓글 수정 처리
   */
  @ResponseBody
  @PostMapping("/update")
  public Map<String, Object> update_proc(

      // 댓글 번호
      @RequestParam("commentno")
      int commentno,

      // 수정 내용
      @RequestParam("content")
      String content,

      // 로그인 회원 번호
      @SessionAttribute(name="memberno", required=false)
      Integer memberno) {

    Map<String, Object> map =
        new HashMap<String, Object>();

    try {

      // 로그인 체크
      if (memberno == null) {

        map.put("res", "login_required");

        return map;
      }

      // 기존 댓글 조회
      MatchCommentVO oldVO =
          this.matchCommentProc.read(commentno);

      // 작성자 검증
      if (oldVO == null ||
          oldVO.getMemberno() != memberno.intValue()) {

        map.put("res", "not_owner");

        return map;
      }
      // 수정용 VO 생성
      MatchCommentVO matchCommentVO =
          new MatchCommentVO();

      // 댓글 번호 설정
      matchCommentVO.setCommentno(commentno);

      // 댓글 내용 설정
      matchCommentVO.setContent(content);

      // 댓글 수정 실행
      int cnt =
          this.matchCommentProc.update(
              matchCommentVO);

      if (cnt == 1) {

        map.put("res", "success");

      } else {

        map.put("res", "fail");
      }

    } catch(Exception e) {

      e.printStackTrace();

      map.put("res", "error");
    }

    return map;
  }


  /* =========================================================
     ❌ 댓글 삭제
  ========================================================= */

  /**
   * 댓글 삭제 처리
   */
  @ResponseBody
  @PostMapping("/delete")
  public Map<String, Object> delete(

      // 댓글 번호
      @RequestParam("commentno")
      int commentno,

      // 로그인 회원 번호
      @SessionAttribute(name="memberno", required=false)
      Integer memberno) {

    Map<String, Object> map =
        new HashMap<String, Object>();

    try {

      // 로그인 체크
      if (memberno == null) {

        map.put("res", "login_required");

        return map;
      }

      // 댓글 조회
      MatchCommentVO vo =
          this.matchCommentProc.read(commentno);

      // 작성자 검증
      if (vo == null ||
          vo.getMemberno() != memberno.intValue()) {

        map.put("res", "not_owner");

        return map;
      }

      // 댓글 삭제
      int cnt =
          this.matchCommentProc.delete(commentno);

      if (cnt == 1) {

        map.put("res", "success");

      } else {

        map.put("res", "fail");
      }

    } catch(Exception e) {

      e.printStackTrace();

      map.put("res", "error");
    }

    return map;
  }


  /* =========================================================
     👍 좋아요 증가
  ========================================================= */

  /**
   * 좋아요 증가
   */
  @ResponseBody
  @PostMapping("/increase_likecnt")
  public Map<String, Object> increase_likecnt(
      int commentno) {

    Map<String, Object> map =
        new HashMap<String, Object>();

    try {

      int cnt =
          this.matchCommentProc
              .increase_likecnt(commentno);

      if (cnt == 1) {

        map.put("res", "success");

      } else {

        map.put("res", "fail");
      }

    } catch(Exception e) {

      e.printStackTrace();

      map.put("res", "error");
    }

    return map;
  }


  /* =========================================================
     🚨 신고 수 증가
  ========================================================= */

  /**
   * 신고 수 증가
   */
  @ResponseBody
  @PostMapping("/increase_reportcnt")
  public Map<String, Object> increase_reportcnt(
      int commentno) {

    Map<String, Object> map =
        new HashMap<String, Object>();

    try {

      int cnt =
          this.matchCommentProc
              .increase_reportcnt(commentno);

      if (cnt == 1) {

        map.put("res", "success");

      } else {

        map.put("res", "fail");
      }

    } catch(Exception e) {

      e.printStackTrace();

      map.put("res", "error");
    }

    return map;
  }

}