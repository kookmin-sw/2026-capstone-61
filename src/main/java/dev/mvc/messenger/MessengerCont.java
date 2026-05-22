package dev.mvc.messenger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import dev.mvc.member.MemberProc;
import dev.mvc.member.MemberVO;
import jakarta.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/messenger")
public class MessengerCont {

  @Autowired
  @Qualifier("dev.mvc.messenger.MessengerProc")
  private MessengerProc messengerProc;
  @Autowired
  @Qualifier("dev.mvc.member.MemberProc")
  private MemberProc memberProc;
  @GetMapping("/list.do")
  public String list(Model model, HttpSession session) {

      String id = (String) session.getAttribute("id");

      if (id == null) {
          return "redirect:/member/login";
      }

      // =========================
      // 채팅/친구 목록
      // =========================
      model.addAttribute(
          "roomList",
          this.messengerProc.list_friends(id)
      );

      // =========================
      // 친구 요청 목록
      // =========================
      model.addAttribute(
          "requestList",
          this.messengerProc.list_request(id)
      );

      model.addAttribute("session_id", id);

      return "messenger/list";
  }
  /** 유저 검색 (친구 신청용) */
  @GetMapping("/search_user.do")
  @ResponseBody
  public Map<String, Object> searchUser(@RequestParam String id) {

      Map<String, Object> response = new HashMap<>();

      // 실제 회원 조회
      MemberVO memberVO = this.memberProc.readById(id);

      // 회원 없음
      if (memberVO == null) {
          response.put("code", "error");
          response.put("message", "존재하지 않는 사용자입니다.");
          return response;
      }

      // 회원 존재
      response.put("code", "success");
      response.put("id", memberVO.getId());
      response.put("nickname", memberVO.getNickname());
      response.put("profile", memberVO.getProfile());

      return response;
  }
  @PostMapping("/manage_friend.do")
  @ResponseBody
  public String manageFriend(@RequestParam String target_id, @RequestParam String action, HttpSession session) {
    String user_id = (String) session.getAttribute("id");
    
    Map<String, Object> map = new HashMap<>();
    map.put("p_user_id", user_id);
    map.put("p_target_id", target_id);
    map.put("p_action", action);
    
    try {
      this.messengerProc.proc_manage_friend(map);
      return "{\"code\": \"success\"}";
    } catch (Exception e) {
      return "{\"code\": \"error\", \"message\": \"" + e.getMessage() + "\"}";
    }
  }
  /** 메시지 전송 API */
  @PostMapping("/send.do")
  @ResponseBody
  public Map<String, Object> sendMessage(@RequestParam int room_no, @RequestParam String content, HttpSession session) {
    String sender_id = (String) session.getAttribute("id");
    Map<String, Object> response = new HashMap<>();

    if (sender_id == null) {
      response.put("code", "error");
      response.put("message", "로그인이 필요합니다.");
      return response;
    }

    MessengerVO messengerVO = new MessengerVO();
    messengerVO.setRoom_no(room_no);
    messengerVO.setSender_id(sender_id);
    messengerVO.setContent(content);

    int result = this.messengerProc.send_message(messengerVO);
    if (result > 0) {
      response.put("code", "success");
    } else {
      response.put("code", "error");
    }
    return response;
  }

  @GetMapping("/room.do")
  public String room(@RequestParam("room_no") int room_no, Model model, HttpSession session) {
      String id = (String) session.getAttribute("id");
      if (id == null) return "redirect:/member/login";

      // 데이터 조회를 위한 파라미터 맵 생성
      Map<String, Object> map = new HashMap<>();
      map.put("room_no", room_no);
      map.put("user_id", id);

      // 1. 방 접속 시 상대방이 보낸 메시지 '읽음(Y)' 처리
      this.messengerProc.update_read_status(map);

      // 2. 상대방 닉네임 가져오기 (null 방지를 위해 Proc/DAO 확인)
      String partnerName = this.messengerProc.get_room_partner(map);
      
      // 만약 파트너 이름이 없다면 '알 수 없는 사용자'로 기본값 설정
      if (partnerName == null) partnerName = "상대방";

      model.addAttribute("partnerName", partnerName);
      model.addAttribute("msg_list", this.messengerProc.list_msg_by_room(room_no));
      model.addAttribute("room_no", room_no);
      model.addAttribute("session_id", id);
      
      return "messenger/room";
  }
}