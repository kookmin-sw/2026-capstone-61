/* =========================================================
   🔥 기존 객체 삭제
========================================================= */
DROP TABLE MATCH_APPLY CASCADE CONSTRAINTS;
DROP SEQUENCE MATCH_APPLY_SEQ;

/* =========================================================
   🐾 MATCH_APPLY (매칭 신청 내역)
========================================================= */
CREATE TABLE MATCH_APPLY (
    -- 기본 정보
    APPLYNO         NUMBER(10)      NOT NULL, -- 신청 번호(PK)
    MATCHNO         NUMBER(10)      NOT NULL, -- 게시글 번호(FK)
    MEMBERNO        NUMBER(10)      NOT NULL, -- 신청 회원 번호(FK)
    DOGNO           NUMBER(10)      NOT NULL, -- 신청 강아지 번호(FK)

    -- 신청 내용
    MSG             VARCHAR2(600)   NOT NULL, -- 신청 메시지
    DMTI            VARCHAR2(20),             -- 신청 당시 강아지 DMTI

    -- 상태 관리
    APPLY_STATUS    NUMBER(1)       DEFAULT 1 NOT NULL, 
    -- 1:신청대기, 2:수락, 3:거절, 4:취소, 5:산책완료
    
    SUCCESS_YN      NUMBER(1)       DEFAULT 0 NOT NULL, 
    -- 0:진행중/실패, 1:매칭성공
    
    REVIEW_WRITTEN  NUMBER(1)       DEFAULT 0 NOT NULL, 
    -- 0:리뷰미작성, 1:리뷰작성완료

    -- 히스토리 시간
    ACCEPT_DATE     DATE,                     -- 수락 일시
    CANCEL_DATE     DATE,                     -- 취소/거절 일시
    COMPLETE_DATE   DATE,                     -- 산책 완료 일시
    RDATE           DATE            DEFAULT SYSDATE NOT NULL, -- 신청 일시

    -- 제약 조건
    CONSTRAINT PK_MATCH_APPLY PRIMARY KEY (APPLYNO),
    CONSTRAINT FK_APPLY_MATCHNO FOREIGN KEY (MATCHNO) REFERENCES MATCH_POST(MATCHNO) ON DELETE CASCADE,
    CONSTRAINT FK_APPLY_MEMBERNO FOREIGN KEY (MEMBERNO) REFERENCES MEMBER(MEMBERNO) ON DELETE CASCADE,
    CONSTRAINT FK_APPLY_DOGNO FOREIGN KEY (DOGNO) REFERENCES DOG(DOGNO) ON DELETE CASCADE,
    
    -- 한 명의 회원이 같은 게시글에 중복 신청 방지
    CONSTRAINT UK_MATCH_MEMBER UNIQUE (MATCHNO, MEMBERNO),

    -- 도메인 무결성
    CONSTRAINT CK_APPLY_STATUS CHECK (APPLY_STATUS IN (1,2,3,4,5,6)),
    CONSTRAINT CK_SUCCESS_YN CHECK (SUCCESS_YN IN (0,1)),
    CONSTRAINT CK_REVIEW_WRITTEN CHECK (REVIEW_WRITTEN IN (0,1))
);

/* =========================================================
   🔢 시퀀스 및 인덱스 생성
========================================================= */
CREATE SEQUENCE MATCH_APPLY_SEQ START WITH 1 INCREMENT BY 1 NOCACHE;

CREATE INDEX IDX_APPLY_MATCHNO ON MATCH_APPLY(MATCHNO);
CREATE INDEX IDX_APPLY_MEMBERNO ON MATCH_APPLY(MEMBERNO);
CREATE INDEX IDX_APPLY_STATUS ON MATCH_APPLY(APPLY_STATUS);

/* =========================================================
   📌 코멘트 설정
========================================================= */
COMMENT ON TABLE MATCH_APPLY IS '매칭 신청 및 진행 내역';
COMMENT ON COLUMN MATCH_APPLY.APPLYNO IS '신청 번호(PK)';
COMMENT ON COLUMN MATCH_APPLY.MATCHNO IS '게시글 번호(FK)';
COMMENT ON COLUMN MATCH_APPLY.MEMBERNO IS '신청자 번호(FK)';
COMMENT ON COLUMN MATCH_APPLY.DOGNO IS '신청 강아지 번호(FK)';
COMMENT ON COLUMN MATCH_APPLY.APPLY_STATUS
IS '1:신청대기, 2:매칭완료, 3:산책중, 4:산책완료, 5:취소, 6:거절';
COMMENT ON COLUMN MATCH_APPLY.SUCCESS_YN IS '0:실패, 1:성공';
COMMENT ON COLUMN MATCH_APPLY.REVIEW_WRITTEN IS '0:미작성, 1:작성완료';

COMMIT;
ALTER TABLE MATCH_APPLY
ADD CANCEL_REASON VARCHAR2(500);










SELECT
    applyno,
    matchno,
    memberno,
    apply_status,
    review_written
FROM match_apply
WHERE apply_status = 4;


SELECT
    a.applyno,
    a.matchno,
    a.memberno,
    a.apply_status
FROM match_apply a
JOIN match_post p
ON a.matchno = p.matchno
WHERE a.apply_status = 4
AND (
      a.memberno = 5
   OR p.memberno = 5
);

