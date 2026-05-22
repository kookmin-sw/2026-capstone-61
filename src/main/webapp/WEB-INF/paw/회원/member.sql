-- Creating MEMBER table
-- 1. 기존 테이블 및 시퀀스 삭제 (의존성 포함)
DROP TABLE member CASCADE CONSTRAINTS;
DROP SEQUENCE member_seq;

SELECT id, passwd
FROM member
WHERE id = '1121';


ALTER TABLE MEMBER
ADD LOCATION_UPDATE DATE;

-- 2. MEMBER 테이블 생성
CREATE TABLE member (
    MEMBERNO      NUMBER(10)      NOT NULL, -- 회원 번호
    ID            VARCHAR2(30)    NOT NULL, -- 아이디
    PASSWD        VARCHAR2(200)   NOT NULL, -- 패스워드
    MNAME         VARCHAR2(30)    NOT NULL, -- 성명
    NICKNAME      VARCHAR2(100)   NULL,     -- 닉네임
    INTRO          VARCHAR2(500) NULL,              -- 자기소개
    MANNER_TEMP    NUMBER(4,1) DEFAULT 36.5,        -- 매너 온도

    TEL           VARCHAR2(14)    NOT NULL, -- 전화번호
    ZIPCODE       VARCHAR2(5)     NULL,     -- 우편번호
    ADDRESS1      VARCHAR2(80)    NULL,     -- 주소1
    ADDRESS2      VARCHAR2(50)    NULL,     -- 주소2
    LATITUDE       NUMBER(13,10) NULL,              -- 위도
    LONGITUDE      NUMBER(13,10) NULL,              -- 경도
    BIRTH         VARCHAR2(30)    NULL,     -- 생년월일
    SEX           VARCHAR2(100)   NULL,     -- 성별
    GRADE         NUMBER(2)       NOT NULL, -- 등급
    MATCH_CNT     NUMBER(10)      DEFAULT 0 NOT NULL, -- 매칭 성공 횟수 (추가됨)
    MDATE         DATE            NOT NULL, -- 가입일
    PROFILE       VARCHAR2(100)   NULL,     -- 메인 이미지
    PROFILESAVED  VARCHAR2(100)   NULL,     -- 실제 저장된 메인 이미지
    THUMBS        VARCHAR2(100)   NULL,     -- 메인 이미지 Preview
    SIZES         NUMBER(10)      NULL,     -- 메인 이미지 크기
    
    -- 제약 조건
    CONSTRAINT PK_MEMBER PRIMARY KEY (MEMBERNO),
    CONSTRAINT UQ_MEMBER_ID UNIQUE (ID),
    CONSTRAINT UQ_MEMBER_NICKNAME UNIQUE (NICKNAME)
);

-- 3. 테이블 및 컬럼 주석(Comment) 설정
COMMENT ON TABLE member IS '회원';
COMMENT ON COLUMN MEMBER.MEMBERNO IS '회원 번호';
COMMENT ON COLUMN MEMBER.ID IS '아이디';
COMMENT ON COLUMN MEMBER.PASSWD IS '패스워드';
COMMENT ON COLUMN MEMBER.MNAME IS '성명';
COMMENT ON COLUMN MEMBER.NICKNAME IS '닉네임';
COMMENT ON COLUMN MEMBER.INTRO IS '자기소개';
COMMENT ON COLUMN MEMBER.MANNER_TEMP IS '매너 온도';
COMMENT ON COLUMN MEMBER.TEL IS '전화번호';
COMMENT ON COLUMN MEMBER.ZIPCODE IS '우편번호';
COMMENT ON COLUMN MEMBER.ADDRESS1 IS '주소1';
COMMENT ON COLUMN MEMBER.ADDRESS2 IS '주소2';
COMMENT ON COLUMN MEMBER.LATITUDE IS '위도';
COMMENT ON COLUMN MEMBER.LONGITUDE IS '경도';
COMMENT ON COLUMN MEMBER.BIRTH IS '생년월일';
COMMENT ON COLUMN MEMBER.SEX IS '성별';
COMMENT ON COLUMN MEMBER.GRADE IS '등급';
COMMENT ON COLUMN MEMBER.MATCH_CNT IS '매칭 성공 횟수';
COMMENT ON COLUMN MEMBER.MDATE IS '가입일';
COMMENT ON COLUMN MEMBER.PROFILE IS '메인 이미지';
COMMENT ON COLUMN MEMBER.PROFILESAVED IS '실제 저장된 메인 이미지';
COMMENT ON COLUMN MEMBER.THUMBS IS '메인 이미지 Preview';
COMMENT ON COLUMN MEMBER.SIZES IS '메인 이미지 크기';

-- 4. 시퀀스 생성
CREATE SEQUENCE member_seq
  START WITH 1
  INCREMENT BY 1
  MAXVALUE 9999999999
  CACHE 2
  NOCYCLE;
fS/kjO+fuEKk06Zl7VYMhg==

DESC member;

--
INSERT INTO member ( memberno,
    id,
    passwd,
    mname,
    tel,
    zipcode,
    address1,
    address2,
    mdate,
    grade,
    nickname
)
VALUES (
    member_seq.NEXTVAL,
    'SYSTEM',
    'system',
    '시스템',
    '000-0000-0000',
    '00000',
    'SYSTEM',
    'SYSTEM',
    SYSDATE,
    15,
    '시스템'
);

COMMIT;


Inserting initial admin member into MEMBER table
INSERT INTO member (MEMBERNO, ID, PASSWD, MNAME, NICKNAME, TEL, ADDRESS1, ADDRESS2, MDATE, BIRTH, GRADE)
VALUES (member_seq.nextval, 'admin', '1234', '통합 관리자', 'admin', '000-0000-0000', '서울시 종로구', '관철동', SYSDATE, 19981121, 0);

INSERT INTO member (memberno, id, passwd, grade, nickname)
VALUES (member_seq.nextval, 'admin', '1234', 'admin', '관리자');
--1. 등록
 
--1) id 중복 확인(null 값을 가지고 있으면 count에서 제외됨)
SELECT COUNT(id)
FROM member
WHERE id='user1';

SELECT COUNT(id) as cnt
FROM member
WHERE id='user1';
 
-- cnt
-- ---
--   0   ← 중복 되지 않음.
   
--2) 등록
-- 회원 관리용 계정, Q/A 용 계정
INSERT INTO member(memberno, id, passwd, mname, nickname, tel,
                                address1, address2, mdate,BIRTH, grade)
VALUES (member_seq.nextval, 'admin', '1234', '통합 관리자', 'Admin', '000-0000-0000',
             '서울시 종로구', '관철동', sysdate,'1998.11.21', 1);
             
INSERT INTO member(memberno, id, passwd, mname, tel, zipcode,
                                address1, address2, mdate, grade)
VALUES (member_seq.nextval, 'beomjo', '1234', '관리자범조', '000-0000-0000', '12345',
             '서울시 종로구', '관철동', sysdate, 1);
 
-- 개인 회원 테스트 계정
INSERT INTO member(memberno, id, passwd, mname, tel, zipcode, address1, address2, mdate, grade)
VALUES (member_seq.nextval, 'user1@gmail.com', '1234', '왕눈이', '000-0000-0000', '12345', '서울시 종로구', '관철동', sysdate, 15);
 
INSERT INTO member(memberno, id, passwd, mname, tel, zipcode, address1, address2, mdate, grade)
VALUES (member_seq.nextval, 'user2@gmail.com', '1234', '아로미', '000-0000-0000', '12345', '서울시 종로구', '관철동', sysdate, 15);
 
INSERT INTO member(memberno, id, passwd, mname, tel, zipcode, address1, address2, mdate, grade)
VALUES (member_seq.nextval, 'user3@gmail.com', '1234', '투투투', '000-0000-0000', '12345', '서울시 종로구', '관철동', sysdate, 15);
 
-- 부서별(그룹별) 공유 회원 기준
INSERT INTO member(memberno, id, passwd, mname, tel, zipcode, address1, address2, mdate, grade)
VALUES (member_seq.nextval, 'team1', '1234', '개발팀', '000-0000-0000', '12345', '서울시 종로구', '관철동', sysdate, 15);
 
INSERT INTO member(memberno, id, passwd, mname, tel, zipcode, address1, address2, mdate, grade)
VALUES (member_seq.nextval, 'team2', '1234', '웹퍼블리셔팀', '000-0000-0000', '12345', '서울시 종로구', '관철동', sysdate, 15);
 
INSERT INTO member(memberno, id, passwd, mname, tel, zipcode, address1, address2, mdate, grade)
VALUES (member_seq.nextval, 'team3', '1234', '디자인팀', '000-0000-0000', '12345', '서울시 종로구', '관철동', sysdate, 15);

COMMIT;

 
--2. 목록
-- 검색을 하지 않는 경우, 전체 목록 출력
 
SELECT memberno, id, passwd, mname, tel, zipcode, address1, address2, mdate, grade
FROM member
ORDER BY grade ASC, id ASC;
     
     
--3. 조회
 
--1) 사원 정보 조회
SELECT memberno, id, passwd, mname, tel, zipcode, address1, address2, mdate, grade
FROM member
WHERE memberno = 1;

SELECT memberno, id, passwd, mname, tel, zipcode, address1, address2, mdate, grade
FROM member
WHERE id = 'user1@gmail.com';
 
    
--4. 수정
UPDATE member 
SET id='user5', mname='조인성', tel='111-1111-1111', zipcode='00000',
    address1='강원도', address2='홍천군', grade=14
WHERE memberno=12;

COMMIT;

 
--5. 삭제
--1) 모두 삭제
--DELETE FROM member;
 
--2) 특정 회원 삭제
--DELETE FROM member
--WHERE memberno=12;

COMMIT;

 
--6. 로그인
SELECT COUNT(memberno) as cnt
FROM member
WHERE id='user1@gmail.com' AND passwd='1234';

   
--7. 패스워드 변경
--1) 패스워드 검사
SELECT COUNT(memberno) as cnt
FROM member
WHERE memberno=1 AND passwd='1234';
 
--2) 패스워드 수정
UPDATE member
SET passwd='0000'
WHERE memberno=1;



UPDATE member
SET grade=16
WHERE memberno=1;
COMMIT;

 
 