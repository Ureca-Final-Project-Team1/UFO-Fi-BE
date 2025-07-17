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
- **확장 가능한 코드 구조** : 프론트 팀의 기획을 위한 `확장에 용이한 구조` 설계 고민
- **거래 게시물-ZET(재화 단위) 간 정합성 보장** : `트랜잭션과 ERD 구조/도메인 분리`로 정합성을 위한 더 나은 구조 고민
- **동시 클릭과 같은 경쟁 상태 고려** : `DB락`과 `MySQL 격리수준`을 고려

--- 

## 4. ERD

<img width="2942" height="1200" alt="image" src="https://github.com/user-attachments/assets/3607d425-ace1-411e-838f-e4e44e294f53" />

- 초기 빠른 기능 구현(1차 MVP)를 위한 테이블 구조
- 추후 기능 추가에 따른 ERD 수정 예정
- Table 추가 가능
- 기능 확장으로 인한 FK 연관 관계 주인 변경 가능

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
| **Backend**                        | ![Java](https://img.shields.io/badge/Java_17-ED8B00?style=flat&logo=openjdk&logoColor=white) ![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?style=flat&logo=spring-boot&logoColor=white) ![Spring Security](https://img.shields.io/badge/Spring_Security-6DB33F?style=flat&logo=springsecurity&logoColor=white) ![OAuth2](https://img.shields.io/badge/OAuth2-4285F4?style=flat&logo=oauth&logoColor=white) ![JWT](https://img.shields.io/badge/JWT-000000?style=flat&logo=JSON%20web%20tokens&logoColor=white) ![JPA](https://img.shields.io/badge/JPA-59666C?style=flat&logo=hibernate&logoColor=white) ![QueryDSL](https://img.shields.io/badge/QueryDSL-0078D4?style=flat&logo=java&logoColor=white) ![MySQL](https://img.shields.io/badge/MySQL-4479A1?style=flat&logo=mysql&logoColor=white) ![FCM](https://img.shields.io/badge/FCM-FFCA28?style=flat&logo=firebase&logoColor=black)                                                                                                                                              
| **Backend Testing & Code Quality** | ![JUnit5](https://img.shields.io/badge/JUnit5-25A162?style=flat&logo=junit5&logoColor=white) ![Mockito](https://img.shields.io/badge/Mockito-25A162?style=flat&logo=mockito&logoColor=white) ![H2](https://img.shields.io/badge/H2-0078D4?style=flat&logo=h2&logoColor=white) ![Jacoco](https://img.shields.io/badge/Jacoco-DC382D?style=flat&logo=jacoco&logoColor=white)                                                                                                       

---

## 7. ETC

### 7-1. 코드 스타일 가이드

[CODE-STYLE.md](./CODE-STYLE.md)

### 7-2. 지라-깃허브 연동 가이드

[지라 깃허브 사용법.pdf](https://github.com/user-attachments/files/21259209/default.pdf)

---

## 8. To Do(추후 계획)

### 8-1. 리팩토링

현재까지 완성된 `주먹 구구식의 그저 돌아가는 프로그램`의 설계의 단점을 분석하며, 리팩토링해본다.

1. 기능 별 각 도메인의 상호작용 이해 하기

2. 도메인 별 ERD의 수정 사항 정리 ERD 수정 및 엔티티 수정(연관관계 주인, 쓸모 없는 외래키)

3. 각 도메인의 루트 엔티티에서 JOIN을 통해 JPA의 여러 번의 쿼리 방지

4. 각 도메인 별 기능 정리 후 락을 통한 이상 현상 해결

5. Controller <-> Service <-> Manager <-> Repository의 4개의 레이어를 통한 관심사의 분리

<details>

Controller: 뷰(API Spec/end Point)
Service: 트랜잭션과 로직 흐름(UseCase)
Manager: 순수 자바코드, 엔티티를 다루는 코드(Domain)
Repository: DB 영속 관리

```java

   /**
     * MyPageUserPlanController 유저의 요금제 정보를 업데이트하는 메서드
     * 1. fetch join을 통해 userPlan까지 다 받아온다.
     * 2. 유저가 게시물을 올린 상태(sellableMobileDate)와 조금이라도 사용한 상태일 시 예외를 던진다.
     * 3. 요금제 테이블에서 요금제 정보를 받아온다.
     * 4. 영속화된 userPlan을 업데이트하고 return
*/
    @Transactional
    public UserPlanUpdateRes updateUserPlan(Long userId, UserPlanUpdateReq userPlanUpdateReq) {
        User user = userRepository.findUserWithUserPlan(userId)
            .orElseThrow(() -> new GlobalException(UserErrorCode.NO_USER));
        UserPlan userPlan = user.getUserPlan();

        if (tradePostRepository.existsByUser(user)) {
            throw new GlobalException(UserErrorCode.CANT_UPDATE_USER_PLAN);
        }

        Plan plan = planRepository.findById(userPlanUpdateReq.getPlanId())
            .orElseThrow(() -> new GlobalException(UserErrorCode.NO_UPDATE_PLAN));

        if (!Objects.equals(userPlan.getSellableDataAmount(), plan.getSellMobileDataCapacityGb())) {
            throw new GlobalException(UserErrorCode.CANT_UPDATE_USER_PLAN);
        }

        userPlan.update(plan);

        return UserPlanUpdateRes.from(userPlan);
    }
```

여러 곳에서 사용될 수 있는 아래의 코드

```java
        User user = userRepository.findUserWithUserPlan(userId)
            .orElseThrow(() -> new GlobalException(UserErrorCode.NO_USER));
        UserPlan userPlan = user.getUserPlan();
```

이러한 로직들을 묶어, UserManager의 findUserPlan()이라는 메서드로 묶기


</details>

6. HttpStatus의 상태 코드를 확인 후 적절한 상태 코드 사용

7. 거래 게시물, ZET 등 민감한 데이터를 가진 도메인 서비스 레이어 트랜잭션 테스트 코드로 체크
   

### 8-2. 결제 프로세스

<img width="500" height="800" alt="image" src="https://github.com/user-attachments/assets/72492d1a-f15e-41b0-9d8b-98a8bd749e7f" />

1. 클라이언트 UFO_FI 서버로 요청 -> 클라이언트에게 sendRedirect()
2. 클라이언트 결제 수행
3. 외부 결제 서비스 결제
4. 결제 성공, 결제 서버에서 우리 서비스로 요청(웹 훅)
5. 결제 성공 로직(ZET 충전) 실행

