/**********************************/
/* 기존 객체 삭제 */
/**********************************/

-- 테이블 삭제 (제약조건 포함)
DROP TABLE breed_recom CASCADE CONSTRAINTS;

-- 시퀀스 삭제
DROP SEQUENCE BREED_RECOM_SEQ;


/**********************************/
/* Table Name: 견종 추천 */
/**********************************/

CREATE TABLE breed_recom(
    RECOMNO     NUMBER(10)     NOT NULL PRIMARY KEY,   -- 추천번호 (PK)
    RECOM       NUMBER(10)     DEFAULT 1 NULL,         -- 추천 여부 (1: 추천, 0: 취소)
    
    BREEDNO     NUMBER(10)     NOT NULL,               -- 견종 번호 (FK)
    MEMBERNO    NUMBER(10)     NOT NULL,               -- 회원 번호 (FK)
    
    -- 🔥 FK: 견종 (CASCADE 추가)
    CONSTRAINT fk_breed_recom_breedno 
      FOREIGN KEY (BREEDNO) 
      REFERENCES breed (breedno)
      ON DELETE CASCADE,
    
    -- FK: 회원
    CONSTRAINT fk_breed_recom_memberno 
      FOREIGN KEY (MEMBERNO) 
      REFERENCES member (memberno),
    
    -- 중복 추천 방지
    CONSTRAINT uq_breed_member UNIQUE (BREEDNO, MEMBERNO)
);


/**********************************/
/* 주석 */
/**********************************/

COMMENT ON TABLE breed_recom is '견종 추천';
COMMENT ON COLUMN BREED_RECOM.RECOMNO is '추천번호';
COMMENT ON COLUMN BREED_RECOM.RECOM is '추천여부';
COMMENT ON COLUMN BREED_RECOM.BREEDNO is '견종 번호';
COMMENT ON COLUMN BREED_RECOM.MEMBERNO is '회원 번호';


/**********************************/
/* 시퀀스 */
/**********************************/

CREATE SEQUENCE BREED_RECOM_SEQ
  START WITH 1
  INCREMENT BY 1
  MAXVALUE 9999999999
  CACHE 2
  NOCYCLE;