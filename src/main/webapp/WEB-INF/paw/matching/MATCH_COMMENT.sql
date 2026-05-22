/* =========================================================
   🔥 기존 객체 삭제
========================================================= */

DROP TABLE MATCH_COMMENT CASCADE CONSTRAINTS;
DROP SEQUENCE MATCH_COMMENT_SEQ;

/* =========================================================
   🐾 MATCH_COMMENT
   산책 매칭 댓글 테이블
   ※ 리뷰는 MATCH_REVIEW 테이블에서 관리
========================================================= */

CREATE TABLE MATCH_COMMENT (

    /* =====================================================
       기본 정보
    ===================================================== */

    COMMENTNO       NUMBER(10)        NOT NULL, -- 댓글 번호(PK)

    MATCHNO         NUMBER(10)        NOT NULL, -- 게시글 번호(FK)

    MEMBERNO        NUMBER(10)        NOT NULL, -- 작성자 회원 번호(FK)

    PARENTNO        NUMBER(10),                 -- 부모 댓글 번호(대댓글)


    /* =====================================================
       댓글 내용
    ===================================================== */

    CONTENT         VARCHAR2(1000)   NOT NULL, -- 댓글 내용

    COMMENT_TYPE    NUMBER(1) DEFAULT 1 NOT NULL,
    -- 1 : 일반 댓글
    -- 2 : 대댓글
    -- 3 : 작성자 공지 댓글


    /* =====================================================
       상태 관리
    ===================================================== */

    LIKECNT         NUMBER(10) DEFAULT 0 NOT NULL, -- 좋아요 수

    REPORTCNT       NUMBER(10) DEFAULT 0 NOT NULL, -- 신고 수

    IS_SECRET       CHAR(1) DEFAULT 'N' NOT NULL,
    -- Y : 비밀 댓글
    -- N : 공개 댓글

    IS_DELETED      CHAR(1) DEFAULT 'N' NOT NULL,
    -- Y : 삭제
    -- N : 정상


    /* =====================================================
       작성일
    ===================================================== */

    RDATE           DATE DEFAULT SYSDATE NOT NULL,


    /* =====================================================
       PRIMARY KEY
    ===================================================== */

    CONSTRAINT PK_MATCH_COMMENT
        PRIMARY KEY (COMMENTNO),


    /* =====================================================
       FOREIGN KEY
    ===================================================== */

    CONSTRAINT FK_COMMENT_MATCHNO
        FOREIGN KEY (MATCHNO)
        REFERENCES MATCH_POST(MATCHNO)
        ON DELETE CASCADE,

    CONSTRAINT FK_COMMENT_MEMBERNO
        FOREIGN KEY (MEMBERNO)
        REFERENCES MEMBER(MEMBERNO)
        ON DELETE CASCADE,

    CONSTRAINT FK_COMMENT_PARENTNO
        FOREIGN KEY (PARENTNO)
        REFERENCES MATCH_COMMENT(COMMENTNO)
        ON DELETE CASCADE,


    /* =====================================================
       CHECK 제약조건
    ===================================================== */

    CONSTRAINT CK_COMMENT_TYPE
        CHECK (COMMENT_TYPE IN (1,2,3)),

    CONSTRAINT CK_COMMENT_SECRET
        CHECK (IS_SECRET IN ('Y','N')),

    CONSTRAINT CK_COMMENT_DELETE
        CHECK (IS_DELETED IN ('Y','N'))

);


/* =========================================================
   🔢 시퀀스 생성
========================================================= */

CREATE SEQUENCE MATCH_COMMENT_SEQ
START WITH 1
INCREMENT BY 1
MAXVALUE 9999999999
NOCACHE
NOCYCLE;


/* =========================================================
   📌 인덱스 생성
========================================================= */

CREATE INDEX IDX_COMMENT_MATCHNO
ON MATCH_COMMENT(MATCHNO);

CREATE INDEX IDX_COMMENT_MEMBERNO
ON MATCH_COMMENT(MEMBERNO);

CREATE INDEX IDX_COMMENT_PARENTNO
ON MATCH_COMMENT(PARENTNO);

CREATE INDEX IDX_COMMENT_TYPE
ON MATCH_COMMENT(COMMENT_TYPE);


/* =========================================================
   📌 테이블 / 컬럼 주석
========================================================= */

COMMENT ON TABLE MATCH_COMMENT IS '산책 매칭 댓글 테이블';

COMMENT ON COLUMN MATCH_COMMENT.COMMENTNO IS '댓글 번호(PK)';

COMMENT ON COLUMN MATCH_COMMENT.MATCHNO IS '게시글 번호(FK)';

COMMENT ON COLUMN MATCH_COMMENT.MEMBERNO IS '작성자 회원 번호(FK)';

COMMENT ON COLUMN MATCH_COMMENT.PARENTNO IS '부모 댓글 번호(대댓글)';

COMMENT ON COLUMN MATCH_COMMENT.CONTENT IS '댓글 내용';

COMMENT ON COLUMN MATCH_COMMENT.COMMENT_TYPE IS
'1:일반댓글, 2:대댓글, 3:작성자공지';

COMMENT ON COLUMN MATCH_COMMENT.LIKECNT IS '좋아요 수';

COMMENT ON COLUMN MATCH_COMMENT.REPORTCNT IS '신고 수';

COMMENT ON COLUMN MATCH_COMMENT.IS_SECRET IS
'Y:비밀댓글, N:공개댓글';

COMMENT ON COLUMN MATCH_COMMENT.IS_DELETED IS
'Y:삭제, N:정상';

COMMENT ON COLUMN MATCH_COMMENT.RDATE IS '작성일';


COMMIT;