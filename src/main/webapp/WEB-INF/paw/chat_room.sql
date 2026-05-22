-- 1. 메신저 방 (유저 간 1:1 방)
DROP SEQUENCE messenger_room_seq;
CREATE SEQUENCE messenger_room_seq START WITH 1 INCREMENT BY 1;

DROP TABLE messenger_room CASCADE CONSTRAINTS;
CREATE TABLE messenger_room (
    room_no NUMBER PRIMARY KEY,
    rdate   DATE DEFAULT SYSDATE
);

-- 2. 메신저 참여자 (누가 대화 중인지)
DROP SEQUENCE messenger_member_seq;
CREATE SEQUENCE messenger_member_seq START WITH 1 INCREMENT BY 1;

DROP TABLE messenger_member CASCADE CONSTRAINTS;
CREATE TABLE messenger_member (
    member_no NUMBER PRIMARY KEY,
    room_no    NUMBER NOT NULL,
    user_id    VARCHAR2(50) NOT NULL,
    CONSTRAINT fk_msg_mem_room FOREIGN KEY (room_no) REFERENCES messenger_room(room_no) ON DELETE CASCADE,
    CONSTRAINT fk_msg_mem_user FOREIGN KEY (user_id) REFERENCES member(id)
);
-- 일반 인덱스 삭제
DROP INDEX idx_msg_member_user;

-- 유니크 인덱스 삭제
DROP INDEX uk_msg_room_user;
CREATE INDEX idx_msg_member_user ON messenger_member(user_id);
CREATE UNIQUE INDEX uk_msg_room_user ON messenger_member(room_no, user_id);

-- 3. 메시지 내역 (실제 대화 내용, 사진, 읽음 여부)
DROP SEQUENCE messenger_data_seq;
CREATE SEQUENCE messenger_data_seq START WITH 1 INCREMENT BY 1;

DROP TABLE messenger_data CASCADE CONSTRAINTS;
CREATE TABLE messenger_data (
    msg_no      NUMBER PRIMARY KEY,
    room_no     NUMBER NOT NULL,
    sender_id   VARCHAR2(50) NOT NULL,
    content     CLOB,                   
    file_name   VARCHAR2(255),          
    is_read     CHAR(1) DEFAULT 'N' CHECK (is_read IN ('Y', 'N')), 
    is_deleted  CHAR(1) DEFAULT 'N' CHECK (is_deleted IN ('Y', 'N')),
    mdate       DATE DEFAULT SYSDATE,
    -- 제약 조건 추가: 방이 존재해야 함
    CONSTRAINT fk_msg_data_room FOREIGN KEY (room_no) REFERENCES messenger_room(room_no) ON DELETE CASCADE,
    -- 제약 조건 추가: 보낸 사람이 실제 회원이어야 함 (중요!)
    CONSTRAINT fk_msg_data_sender FOREIGN KEY (sender_id) REFERENCES member(id)
);

CREATE INDEX idx_msg_data_room_date ON messenger_data(room_no, mdate DESC);

COMMENT ON TABLE messenger_room IS '메신저 방';
COMMENT ON TABLE messenger_member IS '메신저 참여자';
COMMENT ON TABLE messenger_data IS '메신저 메시지 내역';


-- 특정 방(예: 1번 방)의 참여자와 그들의 닉네임 확인
SELECT mm.ROOM_NO, mm.USER_ID, m.NICKNAME
FROM MESSENGER_MEMBER mm
JOIN MEMBER m ON mm.USER_ID = m.ID
WHERE mm.ROOM_NO = 1; -- 확인하고 싶은 방 번호 입력