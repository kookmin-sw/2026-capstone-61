-- =========================================================================
-- [1] 메신저 방 (MESSENGER_ROOM) : 유저 간 1:1 대화방 관리
-- =========================================================================
DROP SEQUENCE messenger_room_seq;
DROP TABLE messenger_room CASCADE CONSTRAINTS;


CREATE TABLE messenger_room (
    room_no NUMBER PRIMARY KEY,         -- 채팅방 고유 번호 (PK)
    rdate   DATE DEFAULT SYSDATE        -- 채팅방 생성 일자
);
COMMENT ON TABLE messenger_room IS '메신저 방 (1:1)';
COMMENT ON COLUMN messenger_room.room_no IS '채팅방 고유 번호';
COMMENT ON COLUMN messenger_room.rdate IS '채팅방 생성 일자';

CREATE SEQUENCE messenger_room_seq
START WITH 1
INCREMENT BY 1
NOCACHE;

-- =========================================================================
-- [2] 메신저 참여자 (MESSENGER_MEMBER) : 해당 방에 누가 있는지 매핑
-- =========================================================================
DROP SEQUENCE messenger_member_seq;
DROP TABLE messenger_member CASCADE CONSTRAINTS;

CREATE TABLE messenger_member (
    member_no NUMBER PRIMARY KEY,       -- 참여 정보 고유 번호 (PK)
    room_no   NUMBER NOT NULL,          -- 소속된 채팅방 번호 (FK)
    user_id   VARCHAR2(50) NOT NULL,    -- 참여한 유저 ID (member.ID FK)
    
    -- 방이 삭제되면 참여자 정보도 연쇄 삭제됨
    CONSTRAINT fk_msg_mem_room FOREIGN KEY (room_no) REFERENCES messenger_room(room_no) ON DELETE CASCADE,
    CONSTRAINT fk_msg_mem_user FOREIGN KEY (user_id) REFERENCES member(id)
);
CREATE SEQUENCE messenger_member_seq
START WITH 1
INCREMENT BY 1
NOCACHE;

-- 인덱스 설정 (조회 성능 최적화용)
DROP INDEX idx_msg_member_user;
DROP INDEX uk_msg_room_user;

CREATE INDEX idx_msg_member_user ON messenger_member(user_id);
-- 한 방에 동일한 유저가 중복해서 들어가는 것을 방지 (유니크 인덱스)
CREATE UNIQUE INDEX uk_msg_room_user ON messenger_member(room_no, user_id);

COMMENT ON TABLE messenger_member IS '메신저 참여자 목록';


-- =========================================================================
-- [3] 메시지 내역 (MESSENGER_DATA) : 실제 오고 간 대화 내용
-- =========================================================================
DROP SEQUENCE messenger_data_seq;
DROP TABLE messenger_data CASCADE CONSTRAINTS;

CREATE TABLE messenger_data (
    msg_no      NUMBER PRIMARY KEY,     -- 메시지 고유 번호 (PK)
    room_no     NUMBER NOT NULL,        -- 전송된 채팅방 번호 (FK)
    sender_id   VARCHAR2(50) NOT NULL,  -- 메시지를 보낸 유저 ID
    content     CLOB,                   -- 대화 내용 (길이 제한 없음)
    file_name   VARCHAR2(255),          -- 첨부 파일 이름 (사진 등)
    is_read     CHAR(1) DEFAULT 'N' CHECK (is_read IN ('Y', 'N')),     -- 읽음 여부 (Y/N)
    is_deleted  CHAR(1) DEFAULT 'N' CHECK (is_deleted IN ('Y', 'N')),  -- 삭제 여부 (Y/N)
    mdate       DATE DEFAULT SYSDATE,   -- 메시지 전송 시간
    
    -- 제약 조건
    CONSTRAINT fk_msg_data_room FOREIGN KEY (room_no) REFERENCES messenger_room(room_no) ON DELETE CASCADE,
    CONSTRAINT fk_msg_data_sender FOREIGN KEY (sender_id) REFERENCES member(id)
);
CREATE SEQUENCE messenger_data_seq
START WITH 1 
INCREMENT BY 1 
NOCACHE;

-- 특정 방의 메시지를 최신순으로 빠르게 불러오기 위한 인덱스
CREATE INDEX idx_msg_data_room_date ON messenger_data(room_no, mdate DESC);

COMMENT ON TABLE messenger_data IS '메신저 메시지 실제 내역';


-- =========================================================================
-- [4] 친구 관계 (FRIEND) : 유저 간 친구 신청 및 수락 상태
-- =========================================================================
DROP PROCEDURE proc_manage_friend;
DROP TABLE friend CASCADE CONSTRAINTS;
DROP SEQUENCE friend_seq;


CREATE TABLE friend (
    friend_id   NUMBER(10)      NOT NULL,      -- 관계 고유 번호 (PK)
    user_id     VARCHAR2(30)    NOT NULL,      -- 요청을 보낸 사람
    target_id   VARCHAR2(30)    NOT NULL,      -- 요청을 받은 사람
    status      VARCHAR2(20)    DEFAULT 'PENDING', -- 상태 (PENDING:대기, ACCEPTED:수락, BLOCKED:차단)
    fdate       DATE            DEFAULT SYSDATE,   -- 최초 신청 일시
    updated_at  DATE            DEFAULT SYSDATE,   -- 최종 상태 변경 일시
    
    CONSTRAINT PK_FRIEND PRIMARY KEY (friend_id),
    CONSTRAINT FK_FRIEND_USER FOREIGN KEY (user_id) REFERENCES member (ID),
    CONSTRAINT FK_FRIEND_TARGET FOREIGN KEY (target_id) REFERENCES member (ID),
    CONSTRAINT UQ_FRIEND_RELATION UNIQUE (user_id, target_id), -- 중복 신청 방지
    CONSTRAINT CHK_NO_SELF_FRIEND CHECK (user_id <> target_id), -- 자기 자신에게 신청 방지
    CONSTRAINT CHK_FRIEND_STATUS CHECK (status IN ('PENDING', 'ACCEPTED', 'BLOCKED'))
);

COMMENT ON TABLE friend IS '유저 간 친구 관계 및 신청/차단 내역';
COMMENT ON COLUMN friend.status IS '진행 상태 (PENDING/ACCEPTED/BLOCKED)';
CREATE SEQUENCE friend_seq 
START WITH 1 
INCREMENT BY 1 
MAXVALUE 9999999999 
NOCACHE;

-- =========================================================================
-- [5] 친구 관리 및 채팅방 연동 프로시저 (PROC_MANAGE_FRIEND)
--     * 핵심: 친구 '수락(ACCEPT)' 시 1:1 채팅방(room_no)을 자동 생성합니다.
-- =========================================================================
CREATE OR REPLACE PROCEDURE proc_manage_friend (
    p_user_id IN VARCHAR2,   -- 현재 로그인한 사용자 ID (명령을 내리는 주체)
    p_target_id IN VARCHAR2, -- 대상 사용자 ID
    p_action IN VARCHAR2     -- 작업 종류 (CREATE, ACCEPT, DELETE)
) AS
    v_new_room_no NUMBER;    -- 새로 생성될 채팅방 번호를 담을 변수
BEGIN
    -- [1] 친구 신청 (대기 상태 생성)
    IF p_action = 'CREATE' THEN
        INSERT INTO friend (friend_id, user_id, target_id, status, fdate, updated_at)
        VALUES (friend_seq.NEXTVAL, p_user_id, p_target_id, 'PENDING', SYSDATE, SYSDATE);
        
    -- [2] 친구 수락 (수락 처리 + 채팅방 동시 생성)
    ELSIF p_action = 'ACCEPT' THEN
        -- 1. 친구 상태를 ACCEPTED로 업데이트
        UPDATE friend 
        SET status = 'ACCEPTED', 
            updated_at = SYSDATE
        WHERE user_id = p_target_id   -- 상대방이 나(p_user_id)에게 보낸 요청을
          AND target_id = p_user_id   -- 수락하는 로직
          AND status = 'PENDING';
          
        -- 2. 업데이트가 성공적으로 되었다면 (즉, 수락 처리가 완료되었다면) 채팅방 생성
        IF SQL%ROWCOUNT > 0 THEN
            -- 방 번호 발급 및 생성
            v_new_room_no := messenger_room_seq.NEXTVAL;
            INSERT INTO messenger_room (room_no, rdate) 
            VALUES (v_new_room_no, SYSDATE);
            
            -- 생성된 방에 두 명의 유저(참여자)를 매핑하여 입장시킴
            INSERT INTO messenger_member (member_no, room_no, user_id) 
            VALUES (messenger_member_seq.NEXTVAL, v_new_room_no, p_user_id);
            
            INSERT INTO messenger_member (member_no, room_no, user_id) 
            VALUES (messenger_member_seq.NEXTVAL, v_new_room_no, p_target_id);
        END IF;
        
    -- [3] 삭제/거절 (관계 삭제)
    -- 참고: 메신저 방까지 연쇄 삭제하려면 별도의 트리거를 만들거나, 여기서 DELETE 문을 추가해야 함. 
    -- 지금은 단순히 친구 관계만 삭제하는 기본 로직.
    ELSIF p_action = 'DELETE' THEN
        DELETE FROM friend 
        WHERE (user_id = p_user_id AND target_id = p_target_id)
           OR (user_id = p_target_id AND target_id = p_user_id);
    END IF;
    
    COMMIT;
EXCEPTION
    WHEN OTHERS THEN
        ROLLBACK;
        RAISE; -- 에러 발생 시 외부(Java/MyBatis)로 예외 던짐
END;
/
COMMIT;

SELECT * FROM messenger_room;



SELECT *
FROM messenger_member
ORDER BY room_no;

SELECT friend_id, user_id, target_id, status
FROM friend;




