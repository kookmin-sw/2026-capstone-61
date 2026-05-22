-- 기존 삭제
DROP TABLE attachfile CASCADE CONSTRAINTS;
DROP SEQUENCE attachfile_seq;

-- 테이블 생성
CREATE TABLE attachfile (
    attachfileno NUMBER(10)    NOT NULL,        
    communityno  NUMBER(10)    NOT NULL,        
    fname        VARCHAR2(100) NOT NULL,        
    fupname      VARCHAR2(100) NOT NULL,        
    thumb        VARCHAR2(100) NOT NULL,        
    fsize        NUMBER(10)    DEFAULT 0,       
    rdate        DATE          DEFAULT SYSDATE NOT NULL, 
    is_dog       CHAR(1)       DEFAULT 'N' NOT NULL,     

    CONSTRAINT PK_ATTACHFILE PRIMARY KEY (attachfileno),

    -- 🔥 여기 핵심
    CONSTRAINT FK_ATTACHFILE_COMMUNITY
        FOREIGN KEY (communityno)
        REFERENCES community (communityno)
        ON DELETE CASCADE
);

-- 시퀀스
CREATE SEQUENCE attachfile_seq
  START WITH 1
  INCREMENT BY 1
  NOCACHE
  NOCYCLE;