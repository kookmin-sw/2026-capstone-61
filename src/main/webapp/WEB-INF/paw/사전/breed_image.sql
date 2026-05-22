-- 1. 기존 테이블 및 시퀀스 삭제 (초기화)
DROP TABLE breed_image CASCADE CONSTRAINTS;
DROP SEQUENCE breed_image_seq;

-- 2. BREED_IMAGE 테이블 생성
CREATE TABLE breed_image (
    img_no         NUMBER(10)          PRIMARY KEY,     -- 이미지 고유 식별 번호 (PK)
    breedno        NUMBER(10)          NOT NULL,        -- 연결된 견종 번호 (FK)
    
    original_name  VARCHAR2(200)       NOT NULL,        -- 사용자가 올린 원본 파일명 (예: mydog.jpg)
    saved_name     VARCHAR2(200)       NOT NULL,        -- 서버에 저장된 실제 파일명 (예: dog_12345.jpg)
    file_path      VARCHAR2(500)       NOT NULL,        -- 파일 저장 경로
    thumb_path     VARCHAR2(500)       NULL,            -- 썸네일 파일 경로 (선택 사항)
    f_size         NUMBER(10) DEFAULT 0 NULL,           -- 파일 크기 (Byte 단위)
    
    -- 외래키 설정: breed 테이블의 breedno를 참조함
    -- ON DELETE CASCADE: 견종 정보가 지워지면 관련된 사진 데이터도 자동으로 지워짐
    CONSTRAINT fk_breed_image_breedno FOREIGN KEY (breedno) 
    REFERENCES breed (breedno) ON DELETE CASCADE
);

-- 3. 주석 추가 (COMMENT)
COMMENT ON TABLE  breed_image               IS '견종별 다중 이미지 관리 테이블';
COMMENT ON COLUMN breed_image.img_no        IS '이미지 식별 번호 (PK)';
COMMENT ON COLUMN breed_image.breedno       IS '연결된 견종 번호 (FK)';
COMMENT ON COLUMN breed_image.original_name IS '원본 파일명';
COMMENT ON COLUMN breed_image.saved_name    IS '서버 저장 파일명';
COMMENT ON COLUMN breed_image.file_path     IS '파일 저장 경로';
COMMENT ON COLUMN breed_image.f_size        IS '파일 크기';

-- 4. 시퀀스 생성
CREATE SEQUENCE breed_image_seq 
    START WITH 1 
    INCREMENT BY 1 
    MAXVALUE 9999999999 
    CACHE 2 
    NOCYCLE;