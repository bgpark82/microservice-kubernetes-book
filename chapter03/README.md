# Chapter 3

### 1. Directory Structure

```java
├── api
├── microservices
│   ├── **product-composite-service**
│   ├── product-service
│   ├── recommendation-service
│   └── review-service
└── util
```

**api** 

도메인, 인터페이스 정의 

- **composite** (통합)
    - Domain
    - Service Interface
- **core** (개별)
    - Domain
    - Service Interface

**microservice**

인터페이스 구현

1. **product-composite-service** (통합)
    - Service 구현체
2. **product-service** (개별)
    - Service 구현체
3. **recommendation-service** (개별)
    - Service 구현체
4. **review-service** (개별)
    - Service 구현체

**util**

- exception
- util

### 1. Directory Structure Details

```java
├── api
│    ├── composite
│    │   └── product
│    │       ├── domain
│    │       │   ├── ProductAggregate.java
│    │       │   ├── RecommendationSummary.java
│    │       │   ├── ReviewSummary.java
│    │       │   └── ServiceAddresses.java
│    │       └── service
│    │           ├── ProductAggregateService.java
│    │           └── ProductCompositeService.java
│    └── core
│        ├── product
│        │   ├── Product.java
│        │   └── ProductService.java
│        ├── recommendation
│        │   ├── Recommendation.java
│        │   └── RecommendationService.java
│        └── review
│            ├── Review.java
│            └── ReviewService.java
│
├── microservices
│   ├── **product-composite-service**
│   │    └── composite
│   │        └── product
│   │            └── service
│   │                ├── ProductAggregateServiceImpl.java
│   │                ├── ProductCompositeServiceImpl.java
│   │                └── ProductCompositeServiceIntegration.java
│   ├── product-service
│   │    └── core
│   │        └── product
│   │            ├── ProductServiceApplication.java
│   │            └── service
│   │                └── ProductServiceImpl.java
│   ├── recommendation-service
│   │    └── core
│   │        └── recommendation
│   │            ├── RecommendationServiceApplication.java
│   │            └── service
│   │                └── RecommendationServiceImpl.java
│   └── review-service
│        └── core
│            └── review
│                ├── ReviewServiceApplication.java
│                └── service
│                    └── ReviewServiceImpl.java
└── util
    ├── exception
    │   ├── InvalidInputException.java
    │   └── NotFoundException.java
    ├── http
    │   ├── GlobalControllerExceptionHandler.java
    │   └── HttpError.java
    └── util
        └── ServiceUtil.java
```

### 리뷰

- Domain과 Service의 **정의부**(api)와 **구현부**(microservice)를 다른 프로젝트로 분리
- **통합서비스**(composite)와 **개별서비스**(core)를 분리
    - **통합 테스트**와 **단위 테스트** 구분의 용의
    - **예외 처리**를 분리
- api와 util의 의존성을 각 서비스에 제공
    - `project(':api')`
    - `project(':util')`
- 통합서비스에서 **개별 서비스의 url과 port를 미리 정의**
    - Gateway
- **@SpringBootTest** 사용
    - Mock Request를 호출하여 서비스 동작 확인
- **로깅 레벨 조정**
    - root : **INFO**
    - se.magnus : **DEBUG**
    - exception : **WARN**
- **통합 테스트 자동화**
    - shell 스크립트를 사용하여 API를 직접 호출
    - `curl`