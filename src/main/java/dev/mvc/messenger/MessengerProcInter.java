package dev.mvc.messenger;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("dev.mvc.messenger.MessengerProc")
public class MessengerProcInter implements MessengerProc {

  @Autowired
  private MessengerDAOInter messengerDAO; // DAOInter 주입

  @Override
  public void proc_manage_friend(Map<String, Object> map) {
    this.messengerDAO.proc_manage_friend(map);
  }

  @Override
  public List<MessengerVO> list_friends(String user_id) {
    return this.messengerDAO.list_friends(user_id);
  }
  // =========================
  // 친구 요청 목록 추가
  // =========================
  @Override
  public List<MessengerVO> list_request(String user_id) {
    return this.messengerDAO.list_request(user_id);
  }
  @Override
  public int send_message(MessengerVO messengerVO) {
    return this.messengerDAO.send_message(messengerVO);
  }

  @Override
  public List<MessengerVO> list_msg_by_room(int room_no) {
    return this.messengerDAO.list_msg_by_room(room_no);
  }

  @Override
  public int count_unread(String user_id) {
    return this.messengerDAO.count_unread(user_id);
  }

  // [추가] 상대방 닉네임 가져오기
  @Override
  public String get_room_partner(Map<String, Object> map) {
    return this.messengerDAO.get_room_partner(map);
  }

  // [추가] 읽음 상태 업데이트
  @Override
  public int update_read_status(Map<String, Object> map) {
    return this.messengerDAO.update_read_status(map);
  }
  
}