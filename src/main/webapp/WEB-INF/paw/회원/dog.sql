-- =========================================
-- [강아지 정보 테이블 (DOG)]
-- 회원이 등록한 반려견의 기본 정보와 프로필 사진, DBTI 결과를 저장합니다.
-- =========================================

-- 1. 기존 테이블 및 시퀀스 초기화 (수정 후 재실행을 위해 필요)
DROP TABLE dog CASCADE CONSTRAINTS; -- 참조 중인 외래키 제약조건까지 강제로 무시하고 테이블 삭제
DROP SEQUENCE DOG_SEQ;              -- 시퀀스 삭제

-- 2. 테이블 생성
CREATE TABLE dog (
  -- 🐾 [1. 식별 및 연결 정보]
  DOGNO      NUMBER(10)     NOT NULL PRIMARY KEY, -- 강아지 고유 번호 (기본키)
  MEMBERNO   NUMBER(10)     NOT NULL,             -- 주인의 회원 번호 (MEMBER 테이블과 연결되는 외래키)

  -- 🐾 [2. 강아지 상세 정보]
  NAME       VARCHAR2(50)   NOT NULL,             -- 강아지 이름 (필수)
  BREED      VARCHAR2(50)   NOT NULL,             -- 견종 (예: 말티즈, 푸들 등)
  AGE        NUMBER(3)      NOT NULL,             -- 나이 (숫자만 입력)
  GENDER     CHAR(1)        CHECK(GENDER IN ('M', 'F')), -- 성별 (M: 수컷, F: 암컷만 입력 가능하도록 제한)

  -- 🐾 [3. 프로필 사진 첨부 파일 정보]
  -- 스프링에서 MultipartFile로 업로드할 때 필요한 4종 세트입니다.
  FILE1      VARCHAR2(100),                       -- 사용자가 업로드한 원본 파일명 (예: mydog.jpg)
  FILE1SAVED VARCHAR2(100),                       -- 서버에 중복 방지용으로 변경되어 저장된 파일명 (예: 20260409_1234.jpg)
  THUMB1     VARCHAR2(100),                       -- 리스트 출력용으로 작게 줄인 썸네일 파일명
  SIZE1      NUMBER(10)     DEFAULT 0,            -- 사진 파일의 용량 (바이트 단위)

  -- 🐾 [4. DBTI 검사 결과 및 시스템 정보]
  DBTI_TYPE  VARCHAR2(4),                         -- 검사 결과 (예: ESFP). 등록 직후에는 검사 전이므로 NULL을 허용합니다.
  RDATE      DATE           DEFAULT SYSDATE,      -- 등록한 날짜 및 시간 (기본값: 현재 시간 자동 입력)

  -- 🐾 [5. 외래키(FK) 제약 조건]
  -- 주인이 회원 탈퇴를 하면(MEMBER 데이터 삭제), 이 강아지 정보도 DB에서 고아 데이터가 되지 않도록 자동으로 함께 삭제(CASCADE) 됩니다.
  CONSTRAINT FK_DOG_MEMBER FOREIGN KEY (MEMBERNO) REFERENCES member(MEMBERNO) ON DELETE CASCADE
);

-- 3. 자동 증가 시퀀스 생성 (DOGNO에 사용)
CREATE SEQUENCE DOG_SEQ
START WITH 1         -- 1번부터 시작
INCREMENT BY 1       -- 1씩 증가
NOCACHE;             -- 캐시를 사용하지 않아 서버 재시작 시 번호 건너뜀 방지

-- 4. 테이블 및 컬럼 오라클 코멘트 (DB 관리 툴에서 설명으로 보여짐)
COMMENT ON TABLE dog IS '회원 반려견 정보';
COMMENT ON COLUMN DOG.DOGNO IS '강아지 번호(PK)';
COMMENT ON COLUMN DOG.MEMBERNO IS '주인 회원 번호(FK)';
COMMENT ON COLUMN DOG.NAME IS '강아지 이름';
COMMENT ON COLUMN DOG.BREED IS '견종';
COMMENT ON COLUMN DOG.AGE IS '나이';
COMMENT ON COLUMN DOG.GENDER IS '성별(M/F)';
COMMENT ON COLUMN DOG.FILE1 IS '원본 파일명';
COMMENT ON COLUMN DOG.FILE1SAVED IS '서버 저장 파일명';
COMMENT ON COLUMN DOG.THUMB1 IS '썸네일 파일명';
COMMENT ON COLUMN DOG.SIZE1 IS '파일 크기';
COMMENT ON COLUMN DOG.DBTI_TYPE IS 'DBTI 검사 결과(4자리)';
COMMENT ON COLUMN DOG.RDATE IS '등록일';

COMMIT;