-- =========================================================
-- 🧠 AI_CONFIG
-- AI Prompt / 규칙 / 스타일 저장 테이블
-- =========================================================

DROP TABLE ai_config CASCADE CONSTRAINTS;
DROP SEQUENCE AI_CONFIG_SEQ;

CREATE TABLE ai_config (

    configno        NUMBER(10)      NOT NULL PRIMARY KEY, -- 설정 번호

    config_key      VARCHAR2(100)   NOT NULL UNIQUE,      -- 설정 KEY

    config_name     VARCHAR2(200)   NOT NULL,             -- 설정 이름

    config_type     VARCHAR2(30)    DEFAULT 'PROMPT',     -- PROMPT / STYLE / RULE

    config_val      CLOB            NOT NULL,             -- Prompt 내용

    description     VARCHAR2(1000),                       -- 설명

    use_yn          CHAR(1) DEFAULT 'Y',                  -- 사용 여부

    rdate           DATE DEFAULT SYSDATE                  -- 생성일
);

COMMENT ON TABLE ai_config IS 'AI Prompt 및 규칙 관리';

COMMENT ON COLUMN ai_config.configno IS '설정 번호';
COMMENT ON COLUMN ai_config.config_key IS '설정 KEY';
COMMENT ON COLUMN ai_config.config_name IS '설정 이름';
COMMENT ON COLUMN ai_config.config_type IS '설정 타입';
COMMENT ON COLUMN ai_config.config_val IS 'Prompt 내용';
COMMENT ON COLUMN ai_config.description IS '설명';
COMMENT ON COLUMN ai_config.use_yn IS '사용 여부';
COMMENT ON COLUMN ai_config.rdate IS '생성일';

CREATE SEQUENCE AI_CONFIG_SEQ
START WITH 1
INCREMENT BY 1
NOCACHE;

COMMIT;

-- =========================================================
-- 💬 CHAT
-- AI 챗봇 대화 로그
-- =========================================================

DROP TABLE chat CASCADE CONSTRAINTS;
DROP SEQUENCE CHAT_SEQ;

CREATE TABLE chat (

    chatno            NUMBER(10)      NOT NULL PRIMARY KEY, -- 채팅 번호

    memberno          NUMBER(10),                           -- 회원 번호(FK)

    dogno             NUMBER(10),                           -- 강아지 번호(FK)

    cat_no            NUMBER(10),                           -- 카테고리 번호(FK)

    question          CLOB            NOT NULL,             -- 사용자 질문

    answer            CLOB            NOT NULL,             -- AI 답변

    question_type     VARCHAR2(50),                         -- MATCH / CHAT / WALK

    emotion           VARCHAR2(30),                         -- HAPPY / WORRY

    ai_model          VARCHAR2(100),                        -- GPT 모델명

    context_data      CLOB,                                 -- GPT 전달 컨텍스트

    prompt_tokens     NUMBER(10) DEFAULT 0,                 -- 입력 토큰

    answer_tokens     NUMBER(10) DEFAULT 0,                 -- 출력 토큰

    response_time     NUMBER(10),                           -- 응답 시간(ms)

    memory_saved_yn   CHAR(1) DEFAULT 'N',                  -- 기억 저장 여부

    rdate             DATE DEFAULT SYSDATE,                 -- 생성일

    CONSTRAINT FK_CHAT_MEMBER
      FOREIGN KEY (memberno)
      REFERENCES member(memberno),

    CONSTRAINT FK_CHAT_DOG
      FOREIGN KEY (dogno)
      REFERENCES dog(dogno),

    CONSTRAINT FK_CHAT_CATEGORY
      FOREIGN KEY (cat_no)
      REFERENCES category(cat_no)
);

COMMENT ON TABLE chat IS 'AI 챗봇 대화 로그';

COMMENT ON COLUMN chat.chatno IS '채팅 번호';
COMMENT ON COLUMN chat.memberno IS '회원 번호';
COMMENT ON COLUMN chat.dogno IS '강아지 번호';
COMMENT ON COLUMN chat.cat_no IS '카테고리 번호';
COMMENT ON COLUMN chat.question IS '사용자 질문';
COMMENT ON COLUMN chat.answer IS 'AI 답변';
COMMENT ON COLUMN chat.question_type IS '질문 유형';
COMMENT ON COLUMN chat.emotion IS '감정 분석';
COMMENT ON COLUMN chat.ai_model IS 'AI 모델';
COMMENT ON COLUMN chat.context_data IS 'GPT 전달 컨텍스트';
COMMENT ON COLUMN chat.prompt_tokens IS '입력 토큰';
COMMENT ON COLUMN chat.answer_tokens IS '출력 토큰';
COMMENT ON COLUMN chat.response_time IS '응답 속도';
COMMENT ON COLUMN chat.memory_saved_yn IS '기억 저장 여부';
COMMENT ON COLUMN chat.rdate IS '생성일';

CREATE SEQUENCE CHAT_SEQ
START WITH 1
INCREMENT BY 1
NOCACHE
NOCYCLE;

COMMIT;

-- =========================================================
-- 🧠 CHAT_MEMORY
-- 사용자 기억 저장
-- =========================================================

DROP TABLE chat_memory CASCADE CONSTRAINTS;
DROP SEQUENCE CHAT_MEMORY_SEQ;

CREATE TABLE chat_memory (

    memoryno          NUMBER(10)      NOT NULL PRIMARY KEY,

    memberno          NUMBER(10)      NOT NULL,

    dogno             NUMBER(10),

    source_chatno     NUMBER(10),

    memory_type       VARCHAR2(50)    NOT NULL,

    memory_text       VARCHAR2(2000)  NOT NULL,

    importance        NUMBER(3) DEFAULT 1,

    use_count         NUMBER(10) DEFAULT 0,

    last_used         DATE,

    active_yn         CHAR(1) DEFAULT 'Y',

    rdate             DATE DEFAULT SYSDATE,

    CONSTRAINT FK_MEMORY_MEMBER
      FOREIGN KEY (memberno)
      REFERENCES member(memberno),

    CONSTRAINT FK_MEMORY_DOG
      FOREIGN KEY (dogno)
      REFERENCES dog(dogno),

    CONSTRAINT FK_MEMORY_CHAT
      FOREIGN KEY (source_chatno)
      REFERENCES chat(chatno)
);

COMMENT ON TABLE chat_memory IS 'AI 사용자 기억 저장';

COMMENT ON COLUMN chat_memory.memoryno IS '기억 번호';
COMMENT ON COLUMN chat_memory.memberno IS '회원 번호';
COMMENT ON COLUMN chat_memory.dogno IS '강아지 번호';
COMMENT ON COLUMN chat_memory.source_chatno IS '원본 채팅 번호';
COMMENT ON COLUMN chat_memory.memory_type IS '기억 타입';
COMMENT ON COLUMN chat_memory.memory_text IS '기억 내용';
COMMENT ON COLUMN chat_memory.importance IS '중요도';
COMMENT ON COLUMN chat_memory.use_count IS '사용 횟수';
COMMENT ON COLUMN chat_memory.last_used IS '마지막 사용일';
COMMENT ON COLUMN chat_memory.active_yn IS '활성 여부';
COMMENT ON COLUMN chat_memory.rdate IS '생성일';

CREATE SEQUENCE CHAT_MEMORY_SEQ
START WITH 1
INCREMENT BY 1
NOCACHE
NOCYCLE;

COMMIT;

-- =========================================================
-- 🐾 AI_ACTION_LOG
-- GPT 호출 및 추천 실행 로그
-- =========================================================

DROP TABLE ai_action_log CASCADE CONSTRAINTS;
DROP SEQUENCE AI_ACTION_LOG_SEQ;

CREATE TABLE ai_action_log (

    logno              NUMBER(10)      NOT NULL PRIMARY KEY,

    memberno           NUMBER(10),

    dogno              NUMBER(10),

    chatno             NUMBER(10),

    action_type        VARCHAR2(50),   -- CHAT / MATCH / ANALYZE

    input_data         CLOB,

    output_data        CLOB,

    ai_model           VARCHAR2(100),

    success_yn         CHAR(1) DEFAULT 'Y',

    error_message      VARCHAR2(2000),

    response_time      NUMBER(10),

    token_usage        NUMBER(10),

    rdate              DATE DEFAULT SYSDATE,

    CONSTRAINT FK_AI_LOG_MEMBER
      FOREIGN KEY (memberno)
      REFERENCES member(memberno),

    CONSTRAINT FK_AI_LOG_DOG
      FOREIGN KEY (dogno)
      REFERENCES dog(dogno),

    CONSTRAINT FK_AI_LOG_CHAT
      FOREIGN KEY (chatno)
      REFERENCES chat(chatno)
);

COMMENT ON TABLE ai_action_log IS 'AI 실행 로그';

COMMENT ON COLUMN ai_action_log.logno IS '로그 번호';
COMMENT ON COLUMN ai_action_log.memberno IS '회원 번호';
COMMENT ON COLUMN ai_action_log.dogno IS '강아지 번호';
COMMENT ON COLUMN ai_action_log.chatno IS '채팅 번호';
COMMENT ON COLUMN ai_action_log.action_type IS '실행 유형';
COMMENT ON COLUMN ai_action_log.input_data IS '입력 데이터';
COMMENT ON COLUMN ai_action_log.output_data IS '결과 데이터';
COMMENT ON COLUMN ai_action_log.ai_model IS 'AI 모델';
COMMENT ON COLUMN ai_action_log.success_yn IS '성공 여부';
COMMENT ON COLUMN ai_action_log.error_message IS '에러 메시지';
COMMENT ON COLUMN ai_action_log.response_time IS '응답 시간';
COMMENT ON COLUMN ai_action_log.token_usage IS '토큰 사용량';
COMMENT ON COLUMN ai_action_log.rdate IS '생성일';

CREATE SEQUENCE AI_ACTION_LOG_SEQ
START WITH 1
INCREMENT BY 1
NOCACHE
NOCYCLE;

COMMIT;

-- =========================================================
-- 🧠 기본 Prompt 데이터
-- =========================================================

-- 1. 일반 챗봇 Prompt
INSERT INTO ai_config (
    configno,
    config_key,
    config_name,
    config_type,
    config_val,
    description
)
VALUES (
    AI_CONFIG_SEQ.NEXTVAL,
    'CHAT_SYSTEM_PROMPT',
    '일반 챗봇 시스템 Prompt',
    'PROMPT',

'
너는 Paws의 AI 반려견 상담사다.

[역할]
- 반려견 고민 상담
- 행동 분석
- 사회화 조언
- 산책 습관 조언

[규칙]
- 실제 DB 데이터를 기반으로 답변
- 존재하지 않는 정보 생성 금지
- 최근 대화를 참고
- 보호자의 감정을 공감
- 너무 기계적으로 답변하지 말 것

[말투]
- 따뜻한 전문가 느낌
- 귀엽지만 과하지 않게
',

    '일반 챗봇 Prompt'
);

COMMIT;

-- =========================================================
-- 🐾 DMTI 매칭 추천 Prompt
-- =========================================================

-- =========================================================
-- 🐾 DMTI 매칭 추천 Prompt
-- =========================================================
SET DEFINE OFF;
INSERT INTO ai_config (
    configno,
    config_key,
    config_name,
    config_type,
    config_val,
    description
)
VALUES (
    AI_CONFIG_SEQ.NEXTVAL,
    'MATCH_SYSTEM_PROMPT',
    'DMTI 매칭 추천 Prompt',
    'PROMPT',

'
너는 Paws의 반려견 산책 매칭 전문가다.

[역할]
- DMTI 기반 궁합 분석
- 산책 친구 추천
- 사회화 안정성 분석
- 반려견 성향 비교 분석

[출력 규칙]

1. 첫 문장은 반드시:
"{반려견이름}와(과) 찰떡궁합인 친구들을 분석해봤어요!🐾"

2. 반려견 성향 설명 추가
예:
"몽이는 에너지가 넘치고 사교적인 다정한 친구예요!"

3. 추천 형식:
[반려견이름 | 매칭점수% | 상대 DMTI | 추천 이유] (ID: 번호)

4. 추천 이유는
두 반려견의 성향이 어떻게 조화를 이루는지 설명

5. 말투는 다정하고 따뜻하게

6. 사용자가 요청하지 않은 데이터는 출력 금지

[DMTI 해석 기준]

E (에너지):
- low: 느긋한
- mid: 활발한
- high: 에너자이저

S (사교성):
- low: 신중한
- mid: 사교적인
- high: 슈퍼인싸

A (애착도):
- low: 독립적인
- mid: 다정한
- high: 껌딱지

O (유연성):
- low: 고집있는
- mid: 영리한
- high: 모범생

[매칭 점수 참고 규칙]

긍정 요소:

1.
조건:
abs(my.E - target.E) <= 15

점수:
+30

이유:
산책 속도와 활동량이 비슷함

2.
조건:
target.O >= 80

점수:
+40

이유:
상대방의 매너가 매우 훌륭함

3.
조건:
my.S >= 70 && target.S >= 70

점수:
+20

이유:
둘 다 사교적이라 금방 친해짐

4.
조건:
my.A < 40 && target.A >= 70

점수:
+50

이유:
다정한 친구가 소심한 아이를 잘 이끌어줌

[부정 요소]

1.
조건:
abs(my.E - target.E) >= 50

점수:
-50

이유:
활동량 차이로 인한 체력 부담

2.
조건:
my.O < 30 && target.E >= 85

점수:
-100

이유:
활발한 친구가 소심한 아이에게 위협이 될 수 있음

3.
조건:
my.S < 30 && target.S < 30

점수:
-20

이유:
서로 무관심하여 매칭 효율이 낮음

[중요]

- 실제 점수 계산은 시스템(Java)에서 수행한다.
- 너는 계산된 점수를 기반으로 자연스럽게 설명한다.
- 존재하지 않는 추천 결과를 생성하지 않는다.
',

    'DMTI 기반 산책 매칭 추천 Prompt'
);

COMMIT;

-- =========================================================
-- 🧠 기억 추출 Prompt
-- =========================================================

INSERT INTO ai_config (
    configno,
    config_key,
    config_name,
    config_type,
    config_val,
    description
)
VALUES (
    AI_CONFIG_SEQ.NEXTVAL,
    'MEMORY_EXTRACTION_PROMPT',
    '사용자 기억 추출 Prompt',
    'PROMPT',

'
너는 사용자 대화에서
장기 기억할 정보를 추출하는 AI다.

[저장 대상]
- 반려견 성격
- 산책 습관
- 사회성 문제
- 건강 고민
- 보호자 고민

[출력 형식]

[
 {
   "memory_type":"PERSONALITY",
   "memory_text":"몽이는 낯가림이 심함"
 }
]

JSON 배열만 출력.
',

    '기억 추출 Prompt'
);

COMMIT;

-- =========================================================
-- 🎨 응답 스타일 Prompt
-- =========================================================

INSERT INTO ai_config (
    configno,
    config_key,
    config_name,
    config_type,
    config_val,
    description
)
VALUES (
    AI_CONFIG_SEQ.NEXTVAL,
    'RESPONSE_STYLE',
    '응답 스타일',
    'STYLE',

'
- 따뜻하고 자연스럽게
- 공감 표현 포함
- 너무 기계적이지 않게
- 반려동물 상담사 느낌

예시:
"걱정이 많으셨겠어요🥺"
"몽이는 조금 조심성이 있는 친구 같아요🐾"
',

    '응답 스타일'
);

COMMIT;

-- =========================================================
-- 🚫 안전 정책 Prompt
-- =========================================================

INSERT INTO ai_config (
    configno,
    config_key,
    config_name,
    config_type,
    config_val,
    description
)
VALUES (
    AI_CONFIG_SEQ.NEXTVAL,
    'SAFETY_RULE',
    'AI 안전 정책',
    'RULE',

'
- 존재하지 않는 데이터 생성 금지
- 의료 진단 확정 금지
- 공격적 표현 금지
- 보호자 비난 금지
- 건강 문제는 병원 상담 권장
',

    'AI 안전 정책'
);

COMMIT;
SET DEFINE ON;


SELECT matchno, title, status
FROM match_post;
SELECT chatno,
       memberno,
       question,
       answer,
       rdate
FROM chat
ORDER BY chatno DESC;

SELECT matchno, title, cat_no, status
FROM match_post;