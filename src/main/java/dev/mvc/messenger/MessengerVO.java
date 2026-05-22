package dev.mvc.messenger;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class MessengerVO {
  // MESSENGER_DATA (메시지 내역)
  private int msg_no;
  private int room_no;
  private String sender_id;
  private String content;
  private String file_name;
  private String is_read;
  private String is_deleted;
  private String mdate; // 전송 시간

  // FRIEND (친구 관계)
  private int friend_id;
  private String user_id;   // 신청자
  private String target_id; // 수신자
  private String status;    // PENDING, ACCEPTED
  private String fdate;
  private String updated_at;

  // 조인용 (상대방 정보)
  private String user_nickname;
  private String target_nickname;
  private String user_profile;
  private String target_profile;
  
  private int unread_cnt;   // 안 읽은 메시지 수
  private String last_msg;  // 마지막 대화 내용 (쿼리에서 제외했다면 null로 표시됨)
  
  
}