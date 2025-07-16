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

### 2-1. 무선 데이터 거래 플랫폼

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
- **확장 가능한 코드 구조** : 프론트 팀의 기획을 위한 `확장에 용이한 구조` 설계 고민. 
- **거래 게시물-ZET(재화 단위) 간 정합성 보장** : `트랜잭션과 ERD 구조/도메인 분리`로 정합성을 위한 더 나은 구조 고민
- **동시 클릭과 같은 경쟁 상태 고려** : `DB락`과 `MySQL 격리수준`을 통한 동시성/이상 현상 해결

<`많지 않은` 초기 사용자, 결제라는 `실거래` 시스템을 위함>

---

## 3. 시스템 아키텍처

<img width="441" height="337" alt="image" src="https://github.com/user-attachments/assets/221beed4-f7e7-44cf-81d4-ee50b27b8f83" />

- 추후 결제 서버 분리로 인한 시스템 아키텍처 수정 예정

--- 

## 4. ERD

<img width="1658" height="706" alt="image" src="https://github.com/user-attachments/assets/fb0352e7-2135-408f-b6db-06d96d2845a1" />

- 초기 빠른 기능 구현(1차 MVP)를 위한 테이블 구조
- 추후 기능 추가에 따른 ERD 수정 예정
- Table 추가 가능
- 기능 확장으로 인한 FK 연관 관계 주인 변경 가능

---

## 5. 기능 소개



---

## 6. 기능 세부 구조도



--- 

## 7. 기술 스택



---

## 8. ETC

### 8-1. 커밋 컨벤션

### 8-2. 코딩 컨벤션

---

## 9. To Do(추후 계획)
