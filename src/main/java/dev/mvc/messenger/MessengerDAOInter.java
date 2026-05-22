package dev.mvc.messenger;

import java.util.List;
import java.util.Map;

public interface MessengerDAOInter {
  /** 친구 관리 프로시저 */
  public void proc_manage_friend(Map<String, Object> map);
  
  /** 친구 및 채팅방 목록 조회 */
  public List<MessengerVO> list_friends(String user_id);
  /** 친구 요청 목록 조회 */
  public List<MessengerVO> list_request(String user_id);
  
  /** 메시지 전송 */
  public int send_message(MessengerVO messengerVO);
  
  /** 특정 방 메시지 내역 조회 */
  public List<MessengerVO> list_msg_by_room(int room_no);
  
  /** [추가] 채팅방 입장 시 상대방 메시지 읽음 처리 */
  public int update_read_status(Map<String, Object> map);
  
  /** [추가] 채팅방 상단 상대방 닉네임 조회 */
  public String get_room_partner(Map<String, Object> map);
  
  /** 안 읽은 메시지 총 개수 */
  public int count_unread(String user_id);
}