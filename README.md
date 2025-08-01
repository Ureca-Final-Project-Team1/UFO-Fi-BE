# UFO_FI 
# 무선 요금제 거래 웹 어플리케이션

<img width="204" height="204" alt="image" src="https://github.com/user-attachments/assets/a051f112-2ff9-4b87-8489-a842c9fe4b5a" />

---

## 1. 백엔드 파트 소개
백엔드 파트 팀원 소개 표
<table>
<td align="center">
<a href="https://github.com/songhyeongu">
<img src="https://avatars.githubusercontent.com/u/165022381?v=4" width="80px;" alt="songhy"/><br/>
<b>songhyeongyu</b><br/>
송현규
</a>
</td>
<td align="center">
<a href="https://github.com/Jeong-Minkyeong">
<img src="https://avatars.githubusercontent.com/Jeong-Minkyeong?s=80" width="80px;" alt="Jeong-Minkyeong"/><br/>
<b>Jeong-Minkyeong</b><br/>
정민경
</a>
</td>
<td align="center">
<a href="https://github.com/chungjeongsu">
<img src="https://avatars.githubusercontent.com/chungjeongsu?s=80" width="80px;" alt="chungjeongsu"/><br/>
<b>chungjeongsu</b><br/>
정지호
</a>
</td>
</tr>
</table>
</div>

---

## 2. 서비스 소개

## 무선 데이터 거래 플랫폼

### `서비스 개요`

국내 이동 통신 시장의 

- **후불 요금제 확산과 유휴 데이터 과잉**, 

- **통신사 데이터 선물 기능의 실효성 부족**, 

- **공식 거래 서비스 플랫폼 부재**

에 따른 `사용자가 안심하고, 거래할 수 있는 투명한 플랫폼을 제공하고, 유휴 데이터의 실질적 가치 회복을 목표`로 한다.

### `타겟층`
본 플랫폼은 유연한 서비스 개발을 목표로 한다.

- **데이터 구매 수요층**

- **데이터 공급 의향층**

- **핵심 얼리어답터층**

### `백엔드 개발 핵심 목표`

- **충전 시스템의 안전성** : 충전 시스템의 `안전성`
- **확장 가능한 코드 구조** : `확장에 용이한 구조` 설계 고민
- **거래 게시물-ZET(재화 단위) 간 정합성 보장** : `거래하기 기능`에 대한 정합성을 위한 더 나은 구조 고민
- **동시 클릭과 같은 경쟁 상태 고려** : `DB락`과 `MySQL 격리수준`을 고려

--- 

## 4. ERD

<img width="1617" height="885" alt="image" src="https://github.com/user-attachments/assets/03956fea-8326-42e7-9831-085b48e50fac" />

### 도메인

user, userplan, plan, tradepost, order, bannedword, follow, notification, payment, report, statisttics, auth

---

## 5. 기능 소개


| **기능 영역** | **설명** |
| --- | --- |
| **1. 회원 인증 및 등급** | - Kakao 소셜 로그인<br>- JWT + Refresh Token 인증<br>- 통신사 · 계좌 연동 시 본인인증 등급 부여 |
| **2. 데이터 판매 기능** | - 통신사/용량/가격 입력 기반 판매글 등록<br>- 계좌 등록 여부에 따른 거래 제한 |
| **3. 일괄 구매 & 사용자 추천** | - 용량·예산 기준 최적 매물 자동 추천<br>- 거래 이력을 Qdrant 벡터화를 통한 신뢰도 가중치 기반 판매자 추천 |
| **4. ZET 재화 시스템** | - ZET ↔ 원 환전 기능<br>- 단가 정책, 패키지 유도, 수수료 모델 설계<br>- 자체 재화로 통합된 거래 구조 구현 |
| **5. 간편 인증 & 계좌 연동** | - 6자리 간편비밀번호로 충전·환급 인증<br>- 본인 명의 계좌 등록 필수 |
| **6. 푸시 알림 시스템 (FCM)** | - 거래, 신고, 제재 등 실시간 알림<br>- 브라우저 권한 UI 및 백그라운드 수신 지원 |
| **7. 신고 및 제재 관리** | - 판매글 신고 및 자동 제재 처리<br>- 백오피스를 통한 관리자 수동 해제, 공지/금칙어 관리 가능 |
| **8. 마이페이지 기능** | - 요금제, 계좌, 알림 설정 등 프로필 수정<br>- 팔로우 및 업적, 거래 내역 조회 가능 |
| **9. 거래 평판 업적 기능** | - 거래 내역 기반 종족/등급/업적 뱃지 부여<br>- 메인 페이지에서 평판에 따른 행성 시각화 |
| **10. 전파 스토리텔링 시스템** | - 거래 흐름 기반 전파 거리 추적<br>- AI 감성 스토리 생성 및 행성 서사 구조<br>- 전파 거리 따라 뱃지·스탬프 보상 |


--- 

## 6. 기술 스택                                                                                                                                                                                                                                                                                                   
| **Backend**                        | <br>
| ![Java](https://img.shields.io/badge/Java_17-ED8B00?style=flat&logo=openjdk&logoColor=white) | <br>
! ![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?style=flat&logo=spring-boot&logoColor=white) | 생산성과 구조화된 개발을 위한 선택. 내장 서버와 자동 구성 지원 |<br>
| ![Spring Security](https://img.shields.io/badge/Spring_Security-6DB33F?style=flat&logo=springsecurity&logoColor=white) | 인증/인가 처리, 세션/쿠키 기반 보안 관리에 사용. 필터 기반 세분화된 보안 설정 가능 |<br>
| ![OAuth2](https://img.shields.io/badge/OAuth2-4285F4?style=flat&logo=oauth&logoColor=white) |	카카오 등 소셜 로그인 연동을 위해 OAuth 2.0 프로토콜 기반 인증 플로우 구현 | <br>
| ![JWT](https://img.shields.io/badge/JWT-000000?style=flat&logo=JSON%20web%20tokens&logoColor=white) | 무상태(stateless) 인증을 위해 Access/Refresh Token 기반 구조 구성 | <br>
| ![JPA](https://img.shields.io/badge/JPA-59666C?style=flat&logo=hibernate&logoColor=white) | 도메인 중심 설계를 위한 ORM 사용. 트랜잭션 처리와 연관관계 매핑 지원 | <br>
| ![QueryDSL](https://img.shields.io/badge/QueryDSL-0078D4?style=flat&logo=java&logoColor=white) | 복잡한 조건의 동적 쿼리 작성을 위해 도입. 컴파일 타임 안정성과 가독성 확보 | <br>
| ![MySQL](https://img.shields.io/badge/MySQL-4479A1?style=flat&logo=mysql&logoColor=white) | 안정성과 성능이 검증된 관계형 데이터베이스. 다양한 인덱싱과 트랜잭션 지원 | <br>
| ![FCM](https://img.shields.io/badge/FCM-FFCA28?style=flat&logo=firebase&logoColor=black) | 사용자 이벤트 기반 알림 전송 (예: 결제 상태, 신고 처리 결과 등) |     
| ![slack](https://img.shields.io/badge/slack-purple)| 결제 실패, 이상 요청, 외부 API 응답 지연 등 주요 이벤트에 대한 운영 알림 시스템 구축. 실시간 모니터링과 장애 인지 시간 최소화  | <br>
| ![Prometheus](https://img.shields.io/badge/Prometheus-red)| 메트릭 수집 | <br>
| ![Grafana](https://img.shields.io/badge/grafana-orange)| 대시보드 시각화 |

---

## 7. Domain Function (주 기능)

### 7-1. 결제 프로세스

<img width="750" height="630" alt="image" src="https://github.com/user-attachments/assets/b67767fc-9e8f-4a1b-a8bd-ebfaef9f0b12" />

<img width="750" height="650" alt="image" src="https://github.com/user-attachments/assets/e23493e7-4afd-4ae2-b85c-74da110cb6ad" />

- **1. 토스 API를 통한 PG 결제**
- **2. 토스의 PaymentKey를 통한 멱등성 보장, 안전한 재시도 가능**
- **3. FailState는 토스 응답이 Exception일 경우를 처리하는 상태**
- **4. TimeoutState는 토스 응답이 오지 않을 시 처리하는 상태**
- **5. Slack 모니터링 및 command를 통한 TimeoutState에 대해 복구 가능**

## 7-2. 관리자 대시보드(백오피스)
- **1. 전체 거래 게시물 조회 및 관리**
- **2. 사용자 계정 정지 및 신고 게시물 관리**
- **3. 금칙어 관리 시스템 - 아호코라식 알고리즘을 활용한 금칙어 전/후 처리**

## 7-3. 데이터 거래
- **1. 중복 거래 방지를 위한 락 기반 업데이트**

## 7-4. 소셜 로그인
- **1. Kakao OAuth2.0을 이용한 소셜 로그인**
- **2. Jwt를 이용한 Stateless 상태 유지**
- **3. 스프링 시큐리티를 통한 인증, 인가 관리**

---

## 8. ETC

### 8-1. 코드 스타일 가이드

[CODE-STYLE.md](./CODE-STYLE.md)

### 8-2. 지라-깃허브 연동 가이드

[지라 깃허브 사용법.pdf](https://github.com/user-attachments/files/21259209/default.pdf)

---
