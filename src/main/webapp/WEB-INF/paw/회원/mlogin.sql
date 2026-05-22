/**********************************/
/* Table Name:  회원 로그인 내역 */
/**********************************/
CREATE TABLE mlogin(
		MLOGINNO                      		NUMBER(10)		 NOT NULL		 PRIMARY KEY,
		IP                            		VARCHAR2(16)		 NOT NULL,
		LOGINDATE                     		DATE		 NOT NULL,
		MEMBERNO                      		NUMBER(10)		 NULL
);

COMMENT ON TABLE mlogin is ' 회원 로그인 내역';
COMMENT ON COLUMN MLOGIN.MLOGINNO is '회원 로그인 번호';
COMMENT ON COLUMN MLOGIN.IP is '접속 ip';
COMMENT ON COLUMN MLOGIN.LOGINDATE is '로그인 날짜';
COMMENT ON COLUMN MLOGIN.MEMBERNO is '회원번호';

CREATE SEQUENCE mlogin_seq
  START WITH 1              -- 시작 번호
  INCREMENT BY 1          -- 증가값
  MAXVALUE 9999999999 -- 최대값: 9999999 --> NUMBER(7) 대응
  CACHE 2                       -- 2번은 메모리에서만 계산
  NOCYCLE;                     -- 다시 1부터 생성되는 것을 방지
  
