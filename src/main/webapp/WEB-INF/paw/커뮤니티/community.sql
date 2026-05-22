-- 1. 기존 테이블 및 시퀀스 삭제
DROP TABLE community CASCADE CONSTRAINTS;
DROP SEQUENCE community_seq;

-- 2. 커뮤니티 테이블 생성
CREATE TABLE community (
    communityno NUMBER(10)     NOT NULL,             
    cat_no      NUMBER(10)     NOT NULL,             
    memberno    NUMBER(10)     NOT NULL,             
    nickname    VARCHAR2(50)   NOT NULL,             
    cateno      NUMBER(10)     DEFAULT 1 NOT NULL,   
    title       VARCHAR2(200)  NOT NULL,             
    content     CLOB           NOT NULL,             
    viewcnt     NUMBER(10)     DEFAULT 0,            
    recom       NUMBER(10)     DEFAULT 0,            
    replycnt    NUMBER(10)     DEFAULT 0,            
    rdate       DATE           NOT NULL,             
    passwd      VARCHAR2(15)   NOT NULL,             
    word        VARCHAR2(100),

    CONSTRAINT PK_COMMUNITY PRIMARY KEY (communityno),
    CONSTRAINT FK_COMMUNITY_CAT_NO 
        FOREIGN KEY (cat_no) REFERENCES category (cat_no),
    CONSTRAINT FK_COMMUNITY_MEMBERNO 
        FOREIGN KEY (memberno) REFERENCES member (memberno)
);

-- 3. 코멘트
COMMENT ON TABLE community IS '통합 커뮤니티 게시판';

COMMENT ON COLUMN community.communityno IS '게시글 번호';
COMMENT ON COLUMN community.cat_no      IS '카테고리 번호';
COMMENT ON COLUMN community.memberno    IS '회원 번호';
COMMENT ON COLUMN community.nickname    IS '닉네임';
COMMENT ON COLUMN community.cateno      IS '게시판 타입';
COMMENT ON COLUMN community.title       IS '제목';
COMMENT ON COLUMN community.content     IS '내용';
COMMENT ON COLUMN community.viewcnt     IS '조회수';
COMMENT ON COLUMN community.recom       IS '추천수';
COMMENT ON COLUMN community.replycnt    IS '댓글수';
COMMENT ON COLUMN community.rdate       IS '작성일';
COMMENT ON COLUMN community.passwd      IS '비밀번호';
COMMENT ON COLUMN community.word        IS '검색어';

-- 4. 시퀀스
CREATE SEQUENCE community_seq 
  START WITH 1 
  INCREMENT BY 1 
  MAXVALUE 9999999999 
  NOCACHE 
  NOCYCLE;