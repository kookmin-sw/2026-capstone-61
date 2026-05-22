package dev.mvc.messenger;

import java.util.List;
import java.util.Map;

public interface MessengerProc {
  /** 친구 관리 프로시저 (CREATE, ACCEPT, DELETE) */
  public void proc_manage_friend(Map<String, Object> map);
  
  /** 친구 및 채팅방 목록 조회 */
  public List<MessengerVO> list_friends(String user_id);
  
  /** 메시지 전송 */
  public int send_message(MessengerVO messengerVO);
  /** 친구 요청 목록 조회 */
  public List<MessengerVO> list_request(String user_id);
  
  /** 특정 방 메시지 내역 조회 */
  public List<MessengerVO> list_msg_by_room(int room_no);
  
  /** 안 읽은 메시지 총 개수 */
  public int count_unread(String user_id);

  /** 
   * [추가] 특정 채팅방에서 내가 아닌 상대방의 닉네임 가져오기 
   * @param map (room_no, user_id 포함)
   */
  public String get_room_partner(Map<String, Object> map);

  /** 
   * [추가] 채팅방 입장 시 상대방이 보낸 메시지들을 '읽음' 처리 
   * @param map (room_no, user_id 포함)
   */
  public int update_read_status(Map<String, Object> map);
  
  
}