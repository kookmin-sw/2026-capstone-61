package dev.mvc.breed_recom;

import java.util.HashMap;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import dev.mvc.breed.BreedProcInter;
import dev.mvc.member.MemberProcInter;
import jakarta.servlet.http.HttpSession;

@RequestMapping(value = "/breed_recom")
@Controller
public class Breed_recomCont {
  
  @Autowired
  @Qualifier("dev.mvc.member.MemberProc")
  private MemberProcInter memberProc;

  @Autowired
  @Qualifier("dev.mvc.breed.BreedProc")
  private BreedProcInter breedProc;
  
  @Autowired
  @Qualifier("dev.mvc.breed_recom.Breed_recomProc")
  private Breed_recomProcInter recomProc;
  
  /**
   * 추천 토글 (HTML의 handleRecomClick에서 호출)
   */
  @PostMapping(value = "/toggle")
  @ResponseBody
  public String toggle(HttpSession session, @RequestBody Breed_recomVO vo) {
    Object membernoObj = session.getAttribute("memberno");
    if (membernoObj == null) {
      JSONObject json = new JSONObject();
      json.put("res", -1);
      return json.toString();
    }
    int memberno = (int) membernoObj;
    vo.setMemberno(memberno);
    
    HashMap<String, Object> map = new HashMap<>();
    map.put("memberno", memberno);
    map.put("breedno", vo.getBreedno());
    
    // 1. 기존 추천 여부 확인
    Breed_recomVO existing = this.recomProc.recom_check(map);
    JSONObject json = new JSONObject();
    
    if (existing == null) {
      // 처음 추천하는 경우
      this.recomProc.create(vo);
      this.breedProc.recom(vo.getBreedno());
      json.put("res", 1);
    } else if (existing.getRecom() == 0) {
      // 추천했다가 취소했던 경우 다시 추천
      this.recomProc.recom(map);
      this.breedProc.recom(vo.getBreedno());
      json.put("res", 1);
    } else {
      // 이미 추천 상태인 경우 취소
      this.recomProc.recom_cancel(map);
      this.breedProc.recom_cancel(vo.getBreedno());
      json.put("res", 0);
    }
    return json.toString();
  }

  /**
   * 추천 상태 조회 (HTML의 recom_check에서 호출)
   */
  @GetMapping(value = "/check", produces = "application/json")
  @ResponseBody
  public String check(HttpSession session, int breedno) {
    Object membernoObj = session.getAttribute("memberno");
    if (membernoObj == null) {
      JSONObject obj = new JSONObject();
      obj.put("res", 0);
      return obj.toString();
    }
    int memberno = (int) membernoObj;
    
    HashMap<String, Object> map = new HashMap<>();
    map.put("memberno", memberno);
    map.put("breedno", breedno);
    
    Breed_recomVO vo = this.recomProc.recom_check(map);
    JSONObject obj = new JSONObject();

    if (vo == null) {
      obj.put("res", 0);
    } else {
      // HTML 스크립트에서 data.res.recom으로 접근하므로 객체 형태로 반환
      JSONObject row = new JSONObject();
      row.put("recom", vo.getRecom());
      obj.put("res", row);
    }
    return obj.toString();
  }
}