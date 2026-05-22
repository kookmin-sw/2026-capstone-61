-- 1. 초기화
DROP TABLE breed CASCADE CONSTRAINTS;
DROP SEQUENCE breed_seq;

-- 2. BREED 테이블 생성
CREATE TABLE breed (
    breedno      NUMBER(10)          PRIMARY KEY,       -- 견종 식별 번호 (PK)
    cat_no       NUMBER(10)          NOT NULL,          -- 카테고리 번호 (FK)
    memberno     NUMBER(10)          NOT NULL,          -- 등록자 번호 (관리자 FK)
    
    breed_name   VARCHAR2(200)       NOT NULL,          -- 견종 이름
    title        VARCHAR2(200)       NOT NULL,          -- 콘텐츠 제목
    content      CLOB                NOT NULL,          -- 상세 설명 (CLOB)
    
    recom        NUMBER(7) DEFAULT 0 NOT NULL,          -- 추천 수
    viewcnt      NUMBER(7) DEFAULT 0 NOT NULL,          -- 조회 수
    
    origin       VARCHAR2(200)       NULL,              -- 원산지
    kg           VARCHAR2(50)        NULL,              -- 평균 몸무게
    height       VARCHAR2(50)        NULL,              -- 평균 체고
    size_type    VARCHAR2(20)        NULL,              -- 크기 구분 (소형, 중형, 대형)
    
    rdate        DATE DEFAULT SYSDATE NOT NULL,         -- 등록일
    
    CONSTRAINT fk_breed_cat_no FOREIGN KEY (cat_no) REFERENCES category (cat_no),
    CONSTRAINT fk_breed_memberno FOREIGN KEY (memberno) REFERENCES member (memberno)
);

-- 3. 주석 (COMMENT)
COMMENT ON TABLE  breed             IS '견종 상세 정보 및 콘텐츠 관리 테이블';
COMMENT ON COLUMN breed.breedno     IS '견종 식별 번호 (PK)';
COMMENT ON COLUMN breed.cat_no      IS '카테고리 번호 (FK)';
COMMENT ON COLUMN breed.memberno    IS '등록자 번호 (FK)';
COMMENT ON COLUMN breed.breed_name  IS '견종 이름';
COMMENT ON COLUMN breed.title       IS '콘텐츠 제목';
COMMENT ON COLUMN breed.content     IS '견종 상세 설명 (CLOB)';

COMMENT ON COLUMN breed.recom       IS '추천 수';
COMMENT ON COLUMN breed.viewcnt     IS '조회 수';
COMMENT ON COLUMN breed.origin      IS '원산지';
COMMENT ON COLUMN breed.kg          IS '평균 몸무게';
COMMENT ON COLUMN breed.height      IS '평균 체고';
COMMENT ON COLUMN breed.size_type   IS '견종 크기 구분';
COMMENT ON COLUMN breed.rdate       IS '데이터 등록일';

-- 4. 시퀀스 생성
CREATE SEQUENCE breed_seq 
    START WITH 1 
    INCREMENT BY 1 
    MAXVALUE 9999999999 
    CACHE 2 
    NOCYCLE;