/* =========================================================
   🔥 기존 테이블 및 시퀀스 삭제
   ========================================================= */
DROP TABLE match_review CASCADE CONSTRAINTS;
DROP SEQUENCE match_review_seq;


/* =========================================================
   🐶 매칭 리뷰 테이블 생성
   ========================================================= */
CREATE TABLE match_review (

    reviewno        NUMBER(10)       NOT NULL, -- 리뷰 번호 (PK)

    matchno         NUMBER(10)       NOT NULL, -- 매칭 번호 (FK)

    writer_no       NUMBER(10)       NOT NULL, -- 리뷰 작성자 회원 번호

    target_no       NUMBER(10)       NOT NULL, -- 리뷰 대상 회원 번호


    /* 🐾 반려견 평가 */
    dog_score       NUMBER(2,1)      DEFAULT 0 NOT NULL, -- 반려견 평점 (0.0 ~ 5.0)

    dog_comment     VARCHAR2(500),                    -- 반려견 후기


    /* 👤 보호자 매너 평가 */
    owner_score     NUMBER(2,1)      DEFAULT 0 NOT NULL, -- 보호자 매너 평점 (0.0 ~ 5.0)
    manner_delta NUMBER(4,1),
    owner_comment   VARCHAR2(500),                    -- 보호자 매너 후기


    /* 📝 전체 후기 */
    content         VARCHAR2(1000),                   -- 종합 리뷰 내용

    rdate           DATE             NOT NULL,        -- 작성일


    /* =========================================================
       🔑 제약조건
       ========================================================= */
    CONSTRAINT PK_MATCH_REVIEW
        PRIMARY KEY (reviewno),

    CONSTRAINT FK_REVIEW_MATCHNO
        FOREIGN KEY (matchno)
        REFERENCES match_post (matchno)
        ON DELETE CASCADE,

    CONSTRAINT FK_REVIEW_WRITER
        FOREIGN KEY (writer_no)
        REFERENCES member (memberno),

    CONSTRAINT FK_REVIEW_TARGET
        FOREIGN KEY (target_no)
        REFERENCES member (memberno),


    /* =========================================================
       ⭐ 평점 범위 제한
       ========================================================= */
    CONSTRAINT CK_DOG_SCORE
        CHECK (dog_score BETWEEN 0 AND 5),

    CONSTRAINT CK_OWNER_SCORE
        CHECK (owner_score BETWEEN 0 AND 5)

);


/* =========================================================
   🔢 시퀀스 생성
   ========================================================= */
CREATE SEQUENCE match_review_seq
START WITH 1
INCREMENT BY 1;


/* =========================================================
   📌 테이블/컬럼 주석
   ========================================================= */
COMMENT ON TABLE match_review IS '산책 매칭 리뷰 및 매너 평가';

COMMENT ON COLUMN match_review.reviewno IS '리뷰 번호';

COMMENT ON COLUMN match_review.matchno IS '매칭 번호';

COMMENT ON COLUMN match_review.writer_no IS '리뷰 작성자 회원 번호';

COMMENT ON COLUMN match_review.target_no IS '리뷰 대상 회원 번호';

COMMENT ON COLUMN match_review.dog_score IS '반려견 평점 (0.0 ~ 5.0)';

COMMENT ON COLUMN match_review.dog_comment IS '반려견 후기';

COMMENT ON COLUMN match_review.owner_score IS '보호자 매너 평점 (0.0 ~ 5.0)';

COMMENT ON COLUMN match_review.owner_comment IS '보호자 매너 후기';

COMMENT ON COLUMN match_review.content IS '종합 후기';

COMMENT ON COLUMN match_review.rdate IS '작성일';
commit


