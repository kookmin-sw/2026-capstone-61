-- 기존 테이블 및 시퀀스 삭제 (필요 시 실행)
DROP TABLE market_order CASCADE CONSTRAINTS;
DROP SEQUENCE SEQ_MARKET_ORDER;
ALTER TABLE market_order ADD (REASON_DETAIL VARCHAR2(1000));
CREATE TABLE market_order (
    ORDER_ID        NUMBER PRIMARY KEY,        -- 주문 번호 (PK)
    MEMBERNO        NUMBER(10,0) NOT NULL,     -- 회원 번호 (FK)
    USER_ID         VARCHAR2(30) NOT NULL,     -- 주문자 아이디

    TOTAL_PRICE     NUMBER NOT NULL,           -- 총 결제 금액
    PAYMENT_METHOD  VARCHAR2(50) NOT NULL,     -- 결제 수단

    ORDER_STATUS    VARCHAR2(20) DEFAULT 'PAID' NOT NULL, 
    -- PAID / SHIPPED / DELIVERED / CANCEL

    REASON          VARCHAR2(200),             -- 주문 취소 사유

    RECEIVER_NAME   VARCHAR2(30) NOT NULL,     -- 수령인 이름
    RECEIVER_TEL    VARCHAR2(14) NOT NULL,     -- 수령인 연락처

    ZIPCODE         VARCHAR2(5),               -- 우편번호
    ADDRESS1        VARCHAR2(80),              -- 기본 주소
    ADDRESS2        VARCHAR2(50),              -- 상세 주소

    ORDER_DATE      DATE DEFAULT SYSDATE,      -- 주문 일시

    CONSTRAINT FK_ORDER_MEMBER 
    FOREIGN KEY (MEMBERNO) REFERENCES member(MEMBERNO)
);

-- 시퀀스 생성
CREATE SEQUENCE SEQ_MARKET_ORDER
    START WITH 1
    INCREMENT BY 1
    NOCACHE;

--------------------------------------------------
-- 📌 테이블 코멘트
--------------------------------------------------
COMMENT ON TABLE market_order IS '상품 주문 마스터 정보 (결제, 배송지, 상태, 취소 사유 포함)';

--------------------------------------------------
-- 📌 컬럼 코멘트
--------------------------------------------------
COMMENT ON COLUMN market_order.ORDER_ID IS '주문 고유 번호 (PK)';
COMMENT ON COLUMN market_order.MEMBERNO IS '회원 번호 (MEMBER 테이블 참조 FK)';
COMMENT ON COLUMN market_order.USER_ID IS '주문자 아이디 (조회 편의용)';

COMMENT ON COLUMN market_order.TOTAL_PRICE IS '최종 결제 금액 (상품가 + 배송비)';
COMMENT ON COLUMN market_order.PAYMENT_METHOD IS '결제 수단 (CARD:신용카드, BANK:무통장입금, PAY:간편결제)';

COMMENT ON COLUMN market_order.ORDER_STATUS IS 
'주문 상태 (PAID:결제완료, SHIPPED:배송중, DELIVERED:배송완료, CANCEL:주문취소)';

COMMENT ON COLUMN market_order.REASON IS '주문 취소 사유 (취소 시 입력)';

COMMENT ON COLUMN market_order.RECEIVER_NAME IS '수령인 성명';
COMMENT ON COLUMN market_order.RECEIVER_TEL IS '수령인 연락처 (010-0000-0000)';

COMMENT ON COLUMN market_order.ZIPCODE IS '배송지 우편번호 (5자리)';
COMMENT ON COLUMN market_order.ADDRESS1 IS '배송지 기본 주소';
COMMENT ON COLUMN market_order.ADDRESS2 IS '배송지 상세 주소';

COMMENT ON COLUMN market_order.ORDER_DATE IS '주문 일시 (기본값 현재시간)';