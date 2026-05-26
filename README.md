# 🐾 PAWS

## AI 기반 반려견 라이프 통합 플랫폼

> PAWS는 AI와 데이터를 기반으로  
> 반려인의 일상, 매칭, 커뮤니티, 거래를 연결하는 스마트 반려 라이프 플랫폼입니다.

---

# 📚 목차

- [📌 프로젝트 소개](#project)
- [🖥️ 주요 기능](#features)
- [🤖 AI 추천 시스템](#ai)
- [💬 실시간 채팅](#chat)
- [🛒 PAWS 마켓](#market)
- [⚙️ 기술 스택](#stack)
- [🌐 배포 주소](#deploy)
- [👨‍💻 개발자](#developer)

---

<a id="project"></a>

# 📌 프로젝트 소개

PAWS는 반려견 보호자를 위한:

- 📍 위치 기반 산책 매칭
- 🤖 AI 기반 추천 시스템
- 🧠 DBTI 성향 검사
- 💬 실시간 메신저
- 🛒 반려견 마켓
- 🐶 견종 사전
- 📝 커뮤니티

기능을 하나의 플랫폼에서 제공하는 통합 반려견 서비스입니다.

---

<a id="features"></a>

# 🖥️ 주요 기능

## 🏠 메인 홈

<img src="./images/home.png" width="300"/>

- 오늘의 반려견 추천
- 산책 지수 제공
- 갤러리 및 커뮤니티 연결
- 반응형 모바일 UI

---

## 🧠 DBTI 성향 검사

<img src="./images/dbti.png" width="300"/>

- 반려견 성향 분석
- 활동 데이터 기반 추천
- AI 매칭 데이터 활용

---

## 🐕 산책 매칭 시스템

<img src="./images/matching.png" width="300"/>

- 위치 기반 산책 모집
- 신청 / 수락 / 거절
- 매너온도 기반 신뢰 시스템
- 모집 상태 관리

---

<a id="ai"></a>

# 🤖 AI 추천 시스템

<img src="./images/chatbot.png" width="300"/>

- GPT 기반 반려견 상담
- 장기 메모리 기반 대화 구조
- 사용자 데이터 기반 맞춤 추천

---

<a id="chat"></a>

# 💬 실시간 채팅

<img src="./images/chat.png" width="300"/>

- 매칭 수락 시 자동 채팅방 생성
- 실시간 메시지 관리
- 친구 및 요청 시스템

---

<a id="market"></a>

# 🛒 PAWS 마켓

<img src="./images/market.png" width="300"/>

- 반려견 상품 판매
- 장바구니 및 주문 기능
- 리뷰 및 신뢰도 관리

---

# 📚 견종 사전

<img src="./images/breed.png" width="300"/>

- 다양한 견종 정보 제공
- 크기 및 특징 분류
- 반려견 정보 탐색 지원

---

# 📝 커뮤니티

<img src="./images/community.png" width="300"/>

- 자유 / 질문 / 정보 게시판
- 댓글 및 조회 기능
- 사용자 간 정보 공유

---

# 🗂️ ERD 설계

<img src="./images/erd.png" width="100%"/>

- 회원
- 반려견
- 매칭
- 채팅
- 리뷰
- 커뮤니티
- 마켓

데이터를 통합 관리하도록 설계했습니다.

---

<a id="stack"></a>

# ⚙️ 기술 스택

## Backend

- Java
- Spring Boot
- MyBatis
- Oracle DB

## Frontend

- HTML5
- CSS3
- JavaScript
- JSP
- AJAX

## AI

- OpenAI API
- Prompt Engineering
- 사용자 행동 데이터 분석

## Infra

- AWS EC2
- Nginx
- GitHub
- FileZilla
- PuTTY

---

# 📊 핵심 특징

## 🤖 AI 기반 추천 구조

- DBTI 성향 분석
- 매너온도 기반 신뢰도
- 사용자 활동 데이터 분석
- GPT 기반 챗봇 연동

---

## 📍 위치 기반 서비스

- 사용자 위치 기반 산책 추천
- 지역 기반 커뮤니티 연결

---

## 📱 모바일 최적화

- 모바일 중심 UI 설계
- 카드형 레이아웃
- 반응형 인터페이스
- 부드러운 인터랙션

---

# 🚀 실행 방법

## 프로젝트 Clone

```bash
git clone https://github.com/kookmin-sw/2026-capstone-61.git
```

---

## DB 설정

```properties
spring.datasource.url=jdbc:oracle:thin:@localhost:1521:XE
spring.datasource.username=아이디
spring.datasource.password=비밀번호
```

---

## 실행

```bash
mvn clean install
mvn spring-boot:run
```

---

<a id="deploy"></a>

# 🌐 배포 주소

- https://smartpaw.duckdns.org
- http://100.27.144.120:9091/

- [📂 GitHub Repository](https://github.com/kookmin-sw/2026-capstone-61)

---

<a id="developer"></a>

# 👨‍💻 개발자

| 이름 | 역할 |
|---|---|
| 장범조 | 기획 / 백엔드 / 프론트엔드 / AI / DB 설계 / 배포 |

---

# 📈 기대 효과

- 반려인 간 신뢰 기반 연결
- AI 기반 맞춤 추천 서비스
- 통합 반려 라이프 플랫폼 구축
- 데이터 기반 서비스 확장 가능

---

# 🙌 프로젝트 소개 영상

추후 업로드 예정

---

# 📄 License

MIT License
