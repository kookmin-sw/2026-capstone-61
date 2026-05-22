DROP TABLE messenger_participant CASCADE CONSTRAINTS;
DROP SEQUENCE messenger_participant_seq;
CREATE TABLE messenger_participant (
    participant_no NUMBER PRIMARY KEY,
    room_no NUMBER NOT NULL,
    user_id VARCHAR2(30) NOT NULL,
    joined_at DATE DEFAULT SYSDATE
);
CREATE SEQUENCE messenger_participant_seq
START WITH 1
INCREMENT BY 1;

INSERT INTO messenger_participant(
    participant_no,
    room_no,
    user_id,
    joined_at
)
VALUES(
    messenger_participant_seq.nextval,
    1,
    'user1',
    SYSDATE
);

INSERT INTO messenger_participant(
    participant_no,
    room_no,
    user_id,
    joined_at
)
VALUES(
    messenger_participant_seq.nextval,
    1,
    'user2',
    SYSDATE
);