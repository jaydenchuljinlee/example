# 스프링 마이크로 서비스 코딩 공작소

<img src="http://image.yes24.com/goods/110243944/XL" width="400" height="400">

---

## 서비스 구조

---

### 조직 서비스
- 스펙: spring-boot 2.7.7 / spring-cloud-version 2021.0.5 / jdk 11 / gradle 7.0
- 기능
  - Organization Entity 관련 CRUD
  - Entity 변경 시, Kafka 적재
  - Request Header에 상관관계 ID 및 Auth Token 적재
  - zipkin, sleuth를 활용한 로깅 기능 추가
  - resilience4j 라이브러리를 통한 서킷 브레이커 기능 추가
  - 컨피그 서버 변경에 대한 실시간 반영 -> @RefreshScope

### 라이센스 서비스
- 스펙: spring-boot 2.7.7 / spring-cloud-version 2021.0.5 / jdk 11 / gradle 7.0
- 기능
  - License Entity 관련 CRUD
  - 조직 서비스 조회를 위한 Feign Client 생성
  - 조직 서비스 Entity 변경 시, Kafka에서 조회 후 Redis에 적재
  - Redis 병목 제거를 위한 sorted-set 사용
  - Request Header에 상관관계 ID 및 Auth Token 적재
  - zipkin, sleuth를 활용한 로깅 기능 추가
  - Thrid-party Library를 위한 로깅 AOP 추가
  - 컨피그 서버 변경에 대한 실시간 반영 -> @RefreshScope

### 게이트웨이
- 스펙: spring-boot 2.7.7 / spring-cloud-version 2021.0.5 / jdk 11 / gradle 7.0
- 기능
  - 서비스 간의 통신을 위한 게이트웨이 서버
  - 책에서는 zuul 라이브러리를 사용했지만, 스프링 및 클라우드 버전업에 따라 netflix 지원이 종료되고 개발 편의성에 따라 spring-cloud-starter-gateway 사용
  - 상관관계 ID 추적을 위한 TrackingFilter 구현

### 서비스 디스커버리
- 스펙: spring-boot 2.7.7 / spring-cloud-version 2021.0.5 / jdk 11 / gradle 7.0
- 기능
  - 서비스 추적 및 서비스 네임으로 접근 가능하도록 구현

### 컨피그 서버
- 스펙: spring-boot 2.7.7 / spring-cloud-version 2021.0.5 / jdk 11 / gradle 7.0
- 기능
  - 각 서비스에서 사용하기 위한 설정 파일 접근
  - 설정 파일 관련 repository는 기존 저장소가 아닌 새로운 저장소를 통해 접근하도록 구현


