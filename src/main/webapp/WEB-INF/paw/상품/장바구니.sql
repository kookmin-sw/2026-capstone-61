-- ==========================================
--  장바구니 테이블 (MARKET_CART)


-- ==========================================
-- 기존 테이블 및 시퀀스 삭제 (초기화용)
DROP TABLE market_cart CASCADE CONSTRAINTS;
DROP SEQUENCE SEQ_MARKET_CART;

CREATE TABLE market_cart (
    CART_ID     NUMBER PRIMARY KEY,
    MEMBER_ID   VARCHAR2(30) NOT NULL, -- 누가 담았는지
    PRODUCT_ID  NUMBER NOT NULL,       -- 어떤 상품인지
    QUANTITY    NUMBER DEFAULT 1 NOT NULL CHECK (QUANTITY > 0), -- 담은 수량
    REG_DATE    DATE DEFAULT SYSDATE,
    
    -- 제약 조건 1: 회원 정보나 상품이 삭제되면 장바구니에서도 삭제
    CONSTRAINT FK_CART_MEMBER FOREIGN KEY (MEMBER_ID) REFERENCES member(ID) ON DELETE CASCADE,
    CONSTRAINT FK_CART_PRODUCT FOREIGN KEY (PRODUCT_ID) REFERENCES market(PRODUCT_ID) ON DELETE CASCADE,
    
    -- 제약 조건 2: 동일 사용자가 동일 상품을 중복해서 담는 것 방지 (유니크 키)
    CONSTRAINT UQ_MEMBER_PRODUCT UNIQUE (MEMBER_ID, PRODUCT_ID)
);

-- 시퀀스 생성
CREATE SEQUENCE SEQ_MARKET_CART NOCACHE;

-- 테이블 및 컬럼 코멘트
COMMENT ON TABLE market_cart IS '사용자 장바구니 정보';
COMMENT ON COLUMN MARKET_CART.CART_ID IS '장바구니 고유 번호 (PK)';
COMMENT ON COLUMN MARKET_CART.MEMBER_ID IS '회원 아이디 (FK)';
COMMENT ON COLUMN MARKET_CART.PRODUCT_ID IS '상품 번호 (FK)';
COMMENT ON COLUMN MARKET_CART.QUANTITY IS '장바구니에 담은 수량';
COMMENT ON COLUMN MARKET_CART.REG_DATE IS '장바구니 등록 일시';
