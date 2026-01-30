<div align = "center">
<a href = "https://olympic-course.vercel.app" target="_blank"><img width="2048" height="1546" alt="image" src="https://github.com/user-attachments/assets/e937a3cf-f0d2-42dd-b152-bceb9852f9e1" /></a>
<em>(위 이미지를 클릭하면, 올코 사이트로 이동합니다.)</em>
</div>



## 0. 한 줄 소개
OlympicCourse는 이용자가 추천한 산책·러닝 코스를 공유하고,<br>
코스를 따라 이동하며 편의시설·메모 등 실사용 정보를 함께 확인할 수 있는 올림픽공원 코스 서비스입니다.
<br><br><br>

## 1. 서비스 제작 동기
기존 올림픽공원 코스 안내는 이미지 위주로 제공되어,
코스 흐름과 주변 편의시설 정보를 함께 파악하기 어렵다는 한계가 있었습니다.<br>
또한 방문 시점에 따라 달라지는 공연 일정과 날씨 등 환경 요소를 고려해
동선을 계획할 수 있는 정보 제공이 필요하다고 판단했습니다.


<br><br><br>

## 2. 역할
- 도메인 설계 및 데이터 모델링
- 기능 개발
- 개발 서버 배포 및 운영
<br><br><br>

## 3. 핵심 기능
### 코스 따라가기 및 사용자별 커스텀 메모
다른 이용자가 추천한 코스를 선택해 따라갈 수 있도록, 해당 코스의 경로(스텝 순서)를 기준으로 이동 흐름을 제공합니다.<br>
이 과정에서 사용자는 각 스텝마다 자신만의 메모를 기록해 코스를 유연하게 활용할 수 있습니다.<br><br>


### 편의 시설 위치 조회 및 필터링
코스 주변의 편의시설 정보를 지도 기반으로 조회할 수 있도록 제공하며,<br>
화장실·편의점 등 이용 목적에 따라 원하는 시설만 필터링해 확인할 수 있습니다.<br>
이를 통해 코스를 따라 이동하는 과정에서도 필요한 편의시설을 빠르게 파악할 수 있도록 했습니다.

<div align="center">
  <img width="590" height="1356" alt="image" src="https://github.com/user-attachments/assets/bb6306b7-2b09-4af4-a392-9612275d4567" />
</div>

<br><br><br><br>

### 이달의 베스트 3코스
가장 많은 좋아요를 받은 3개의 코스를 제공하며, 사용자의 코스 선택에 도움을 줄 수 있도록 했습니다.
<div align="center">
  <img width="590" height="315" alt="image" src="https://github.com/user-attachments/assets/dc207571-63c4-491c-941d-896263b1c852" />
</div>

<br><br><br>

## 4. 기술 스택
- Java 17 / Spring Boot 3.4.4
- Spring Security, JWT, Redis
- JPA, QueryDSL, MySQL
- AWS EC2, Nginx, S3, CloudFront
<br><br><br>

## 5. 아키텍처
<img width="3778" height="2677" alt="Frame 3 (1)" src="https://github.com/user-attachments/assets/1cefad9a-46bf-459a-a1df-943a8a87ddc2" />



## 6. 트러블 슈팅
- `운영 환경 시간 기준 이슈`
  - 로컬과 운영 환경의 시스템 시간 기준 차이로 인해 날씨 조회 시 잘못된 시점의 데이터가 출력되는 문제가 발생했습니다.<br>
  서버 및 DB의 시간 기준을 통일해 동일한 결과가 조회되도록 개선했습니다.

- `좋아요 / 베스트 코스 조회 오류`
  - JOIN 조건 설정 미흡으로 의도와 다른 데이터가 조회되는 문제가 발생했고,<br>
  JOIN 방식과 WHERE 조건을 재정의해 정확한 결과가 조회되도록 수정했습니다.
