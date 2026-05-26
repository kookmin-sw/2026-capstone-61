🐾 PAWS
AI-Powered Integrated Dog Life Platform
AI 기반 반려견 라이프 통합 플랫폼

PAWS is a smart pet lifestyle platform that connects dog owners through AI, data, trust-based matching, real-time communication, and integrated pet services.

PAWS는 AI와 데이터를 기반으로 반려인의 일상, 매칭, 커뮤니티, 거래를 하나의 플랫폼으로 연결하는 스마트 반려견 라이프 플랫폼입니다.

📚 Table of Contents | 목차
📌 Project Overview | 프로젝트 소개
🚨 Problem Definition | 문제 정의
💡 Solution | 해결 아이디어
🖥️ Main Features | 주요 기능
🤖 AI Recommendation System | AI 추천 시스템
🧠 DBTI System | DBTI 시스템
💬 Real-time Chat | 실시간 채팅
🛒 PAWS Market | PAWS 마켓
🗂️ Database Design | DB 설계
🏗️ System Architecture | 시스템 구조
⚙️ Tech Stack | 기술 스택
🚀 Deployment | 배포 주소
📈 Scalability | 확장 가능성
👨‍💻 Developer | 개발자




📌 Project Overview | 프로젝트 소개

PAWS is an integrated platform designed for dog owners. It combines:

📍 Location-based walking matching
🤖 AI-powered recommendation system
🧠 DBTI personality analysis
💬 Real-time messenger
🛒 Pet marketplace
🐶 Dog breed encyclopedia
📝 Community system

into one connected ecosystem.

PAWS는:

위치 기반 산책 매칭
AI 추천 시스템
DBTI 성향 분석
실시간 메신저
반려견 마켓
견종 사전
커뮤니티

기능을 하나의 데이터 기반 플랫폼으로 통합한 서비스입니다.




🚨 Problem Definition | 문제 정의
Existing pet services still have major limitations.
기존 반려 서비스는 여전히 한계가 존재합니다.
Existing Service	Limitation
Walking Apps	Lack of trust-based matching
Communities	Difficult to create real-world connections
Market Services	Reviews and transaction history are separated
Information Sites	No personalized recommendation system
Key Problems
🐾 Difficulty of Walking Matching
Hard to understand dog personalities beforehand
No trust-based matching structure
🛒 Low Trust in Transactions
Seller reviews are disconnected from community activity
Real experiences are not integrated
📚 Fragmented Information
Community, market, and information services are separated
Users repeatedly move between multiple platforms
🤖 Lack of Personalization
Existing services mainly focus on simple bulletin boards
No AI-based recommendation or integrated data structure




💡 Solution | 해결 아이디어
PAWS connects fragmented pet services using AI and integrated data.
PAWS는 분산된 반려 서비스를 AI와 데이터 기반으로 연결합니다.
Core Solutions
🤖 AI-Based Personalized Recommendation
GPT-powered recommendation system
Personalized chatbot interaction
User behavior analysis
📍 Location-Based Walking Matching
Nearby walking partner recommendation
Personality-based matching support
🌡️ Manners Temperature Trust System
Trust score accumulated from activity and reviews
Safer community environment
💬 Automatic Chat Room Creation
Real-time communication after matching
Instant connection between users
🛒 Integrated Community & Market
Community, market, and reviews connected together
Unified pet lifestyle experience
📊 Data-Centered Platform Structure
Matching, reviews, chat, and user activity integrated
AI recommendation quality improves as data accumulates




🖥️ Main Features | 주요 기능
🏠 Main Home | 메인 홈
Features
Daily dog recommendations
Walking index information
Community and gallery integration
Responsive mobile UI
🐕 Walking Matching System | 산책 매칭 시스템
Features
Location-based matching
Apply / Accept / Reject process
Walking status management
Review-based trust system
Automatic chat room creation
📝 Community System | 커뮤니티 시스템
Features
Free / Question / Information boards
Comment and view tracking
User interaction and information sharing
📚 Dog Breed Encyclopedia | 견종 사전
Features
Dog breed information
Size and characteristic classification
Easy breed exploration




🤖 AI Recommendation System | AI 추천 시스템
AI Learning Structure | AI 학습 구조
Input Data
User questions
Dog DBTI
Match history
Review data
Chat history
User activity logs

⬇️

AI Processing
OpenAI GPT analysis
Long-term memory accumulation
Recommendation score calculation
User tendency analysis

⬇️

Results
Personalized walking recommendations
AI chatbot responses
User-specific recommendations
Trust-based matching support
AI Data Architecture
Stored AI Data
Table	Purpose
chat	Conversation history
chat_memory	Long-term memory
ai_action_log	GPT recommendation logs
ai_config	Prompt policy management
Future Expansion

As conversation and behavior data accumulate, PAWS is designed to evolve into a personalized 1:1 AI pet platform.

사용자 행동 데이터와 질문 데이터가 누적될수록 AI 추천 정확도와 개인화 기능이 향상되도록 설계했습니다.




🧠 DBTI System | DBTI 시스템
Why DBTI? | 왜 DBTI인가?

Existing walking services do not consider dog personality compatibility.

PAWS analyzes:

Activity level
Social tendency
Aggression tendency
Extroversion

to improve matching quality and compatibility.

기존 산책 서비스는 반려견의 성향을 고려하지 못했습니다.

PAWS는:

활동성
사회성
외향성
공격성

데이터를 기반으로 반려견 간 궁합과 보호자 매칭 정확도를 높이도록 설계했습니다.




💬 Real-time Chat | 실시간 채팅
Features
Automatic chat room creation after acceptance
Real-time messaging
Friend request system
Match-based communication




🛒 PAWS Market | PAWS 마켓
Features
Product registration and management
Cart and order system
Delivery management
Review-based trust accumulation




🗂️ Database Design | DB 설계
Core Design Points
Member-centered data structure
Match status flow tracking
Review-based trust accumulation
AI recommendation data storage structure
Expandable relational database design
Main Entities
MEMBER
DOG
DOG_DBTI
MATCH_POST
MATCH_APPLY
CHAT
MARKET
REVIEW
COMMUNITY




🏗️ System Architecture | 시스템 구조
Spring MVC + MyBatis Layered Architecture
Architecture Structure

Client UI → Controller → Proc Layer → DAO + Mapper → Oracle DB

Design Goals
Separation of concerns
Improved maintainability
Reduced modification scope
Scalable module structure
Efficient SQL management




⚙️ Tech Stack | 기술 스택
Backend
Java
Spring Boot
MyBatis
Oracle DB
Frontend
HTML5
CSS3
JavaScript
JSP
AJAX
AI
OpenAI API
Prompt Engineering
User behavior data analysis
Infra
AWS EC2
Nginx
GitHub
FileZilla
PuTTY
⚙️ Why These Technologies? | 기술 선택 이유
Technology	Reason
Spring Boot	Structured architecture and maintainability
MyBatis	Direct SQL control
Oracle DB	Stable relational database structure
OpenAI API	AI conversation and recommendation
AWS EC2	Real deployment environment
Nginx	Reverse proxy and traffic management




🚀 Deployment | 배포 주소
Live Service
https://smartpaw.duckdns.org
http://100.27.144.120:9091/
GitHub Repository
https://github.com/kookmin-sw/2026-capstone-61
🚀 Getting Started | 실행 방법
Clone Repository
# 프로젝트 클론
# Clone project repository


git clone https://github.com/kookmin-sw/2026-capstone-61.git
Database Configuration
# Oracle DB 설정
# Oracle Database configuration


spring.datasource.url=jdbc:oracle:thin:@localhost:1521:XE
spring.datasource.username=YOUR_USERNAME
spring.datasource.password=YOUR_PASSWORD
Run Project
# 프로젝트 빌드 및 실행
# Build and run project


mvn clean install
mvn spring-boot:run




📈 Scalability | 확장 가능성
Future Expansion
AI health analysis
Walking route recommendation
Hospital reservation integration
Dog behavior analysis
AI-based 신고 탐지 시스템
Real-time nearby matching
Personalized AI companion system
👨‍💻 Solo Development Project | 1인 개발 프로젝트

This project was fully developed by one developer.

본 프로젝트는:

기획
UI/UX 디자인
프론트엔드
백엔드
데이터베이스 설계
AI 기능 구현
서버 배포

전 과정을 1인이 직접 개발했습니다.




👨‍💻 Developer | 개발자
Name	Role
Beomjo Jang (장범조)	Planning / Backend / Frontend / AI / DB Design / Deployment
📈 Expected Effects | 기대 효과
User Perspective
Trust-based walking connections
Personalized dog recommendations
Integrated pet lifestyle experience
Platform Perspective
AI recommendation improvement through accumulated data
Expandable personalized AI services
Long-term pet ecosystem platform scalability
🙌 Demo Video | 시연 영상

Coming Soon

추후 업로드 예정

📄 License

MIT License
