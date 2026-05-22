-- 1. CHAT 테이블 삭제
-- CONSTRAINT가 걸려 있어도 강제로 삭제하려면 CASCADE CONSTRAINTS를 붙입니다.
DROP TABLE chat CASCADE CONSTRAINTS;

-- 2. CHAT_SEQ 시퀀스 삭제
DROP SEQUENCE CHAT_SEQ;

-- 1. CHAT 테이블 생성
CREATE TABLE chat (
    CHATNO    NUMBER(10)      NOT NULL PRIMARY KEY, -- 채팅 번호 (PK)
    MEMBERNO  NUMBER(10)      NULL,                 -- 사용자 번호 (FK, 비회원 가능)
    CAT_NO    NUMBER(10)      NULL,                 -- 카테고리 번호 (FK)
    BREEDNO   NUMBER(10)      NULL,                 -- 품종 번호 (FK)
    QUESTION  CLOB            NOT NULL,             -- 사용자 질문
    ANSWER    CLOB            NOT NULL,             -- AI 답변
    RDATE     DATE            NOT NULL,             -- 대화 일시
    
    -- 외래키(FK) 설정
    CONSTRAINT FK_CHAT_MEMBER   FOREIGN KEY (MEMBERNO) REFERENCES member (MEMBERNO),
    CONSTRAINT FK_CHAT_CATEGORY FOREIGN KEY (CAT_NO)   REFERENCES category (CAT_NO),
    CONSTRAINT FK_CHAT_BREED    FOREIGN KEY (BREEDNO)  REFERENCES breed (BREEDNO) -- 품종 테이블 연결
);

-- 2. 코멘트 추가
COMMENT ON TABLE chat IS 'AI 챗봇 상담 및 로그 테이블';
COMMENT ON COLUMN CHAT.CHATNO IS '채팅 일련번호';
COMMENT ON COLUMN CHAT.MEMBERNO IS '회원 번호';
COMMENT ON COLUMN CHAT.CAT_NO IS '카테고리 번호';
COMMENT ON COLUMN CHAT.BREEDNO IS '품종 번호';
COMMENT ON COLUMN CHAT.QUESTION IS '사용자 질문 내용';
COMMENT ON COLUMN CHAT.ANSWER IS 'AI 답변 내용';
COMMENT ON COLUMN CHAT.RDATE IS '상담 날짜';

-- 3. 시퀀스 생성
CREATE SEQUENCE CHAT_SEQ
  START WITH 1
  INCREMENT BY 1
  MAXVALUE 9999999999
  NOCACHE
  NOCYCLE;

COMMIT;