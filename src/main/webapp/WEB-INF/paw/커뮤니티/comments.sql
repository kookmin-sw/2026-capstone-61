DROP TABLE comments CASCADE CONSTRAINTS;
DROP SEQUENCE comments_seq;

CREATE TABLE comments (
    comment_no  NUMBER(10)     NOT NULL,             -- 댓글 일련번호 (PK)
    communityno NUMBER(10)     NOT NULL,             -- 게시글 번호 (Community FK)
    memberno    NUMBER(10)     NOT NULL,             -- 작성자 회원 번호 (Member FK)
    nickname    VARCHAR2(50)   NOT NULL,             -- 작성자 닉네임 (표시용)
    content     VARCHAR2(1000) NOT NULL,             -- 댓글 내용
    parent_no   NUMBER(10)     DEFAULT NULL,         -- 부모 댓글 번호 (대댓글용 self-FK)
    depth       NUMBER(2)      DEFAULT 0,            -- 댓글 깊이 (0: 댓글, 1: 대댓글)
    rdate       DATE           DEFAULT SYSDATE NOT NULL, -- 등록일
    is_deleted  CHAR(1)        DEFAULT 'N',          -- 삭제 여부 (Y/N)
    
    CONSTRAINT PK_COMMENTS PRIMARY KEY (comment_no),
    CONSTRAINT FK_COMMENTS_COMMUNITYNO FOREIGN KEY (communityno) 
        REFERENCES community (communityno) ON DELETE CASCADE,
    CONSTRAINT FK_COMMENTS_MEMBERNO FOREIGN KEY (memberno) 
        REFERENCES member (memberno),
    CONSTRAINT FK_COMMENTS_PARENT_NO FOREIGN KEY (parent_no) 
        REFERENCES comments (comment_no) ON DELETE CASCADE
);

COMMENT ON TABLE comments IS '커뮤니티 게시판 댓글 테이블 (대댓글 지원)';
COMMENT ON COLUMN comments.comment_no  IS '댓글 일련번호 (PK)';
COMMENT ON COLUMN comments.communityno IS '게시글 일련번호 (Community 테이블 연동)';
COMMENT ON COLUMN comments.memberno    IS '작성자 회원번호 (Member 테이블 연동)';
COMMENT ON COLUMN comments.nickname    IS '작성자 닉네임';
COMMENT ON COLUMN comments.content     IS '댓글 내용';
COMMENT ON COLUMN comments.parent_no   IS '부모 댓글 번호 (일반 댓글은 NULL, 대댓글은 원댓글 번호)';
COMMENT ON COLUMN comments.depth       IS '댓글 깊이 (0: 일반 댓글, 1: 대댓글)';
COMMENT ON COLUMN comments.rdate       IS '작성 일시';
COMMENT ON COLUMN comments.is_deleted  IS '삭제 여부 (Y: 삭제됨, N: 정상)';

CREATE SEQUENCE comments_seq
  START WITH 1
  INCREMENT BY 1
  MAXVALUE 9999999999
  NOCACHE
  NOCYCLE;