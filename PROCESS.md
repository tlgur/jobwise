0. 프로젝트 킥오프 (반나절)

목표: 뼈대 + 공용 규칙 합의
해야 할 일

Git 리포 만들기, 브랜치 전략: main(릴리스), dev(통합), feature/*

코드 규칙: Google Java Style + Spotless/Checkstyle, ktlint 아님

이슈/PR 템플릿 추가, Conventional Commits(feat:, fix:…)

README에 로컬 실행 목표 명시
완료 기준

README.md, CONTRIBUTING.md, .editorconfig, .gitignore 머지

1. 로컬 개발 환경 (Docker Compose) (반~1일)

목표: docker compose up 한 방으로 API/DB 뜨게
해야 할 일

docker-compose.yml: api, postgres, pgadmin(or pgweb), redis

Spring Boot 3.x 초기화(web, data-jpa, validation, actuator, springdoc-openapi)

application-local.yml 분리, SPRING_PROFILES_ACTIVE=local
완료 기준

GET /actuator/health 200

pgweb 접속되어 테이블 조회 가능

2. DB 스키마 v1 + 마이그레이션 (Flyway) (반~1일)

목표: 최소 Q/A 도메인과 직업 카테고리
해야 할 일

엔티티: JobCategory, Question, Answer

Flyway: V1__init.sql (PK/외래키/인덱스 기본)

JPA 리포지토리 & 간단 통합 테스트(Testcontainers 권장)
완료 기준

./gradlew test 통과, 로컬 부팅 시 Flyway가 테이블 생성

3. MVP API (CRUD + 검증 + 문서) (1~2일)

목표: 질문 등록/조회, 답변 등록/조회
해야 할 일

DTO + Bean Validation(title 길이, 금칙어 간단 룰)

컨트롤러:

POST /questions {title, body, jobText}

GET /questions?job=&sort=recent&page=

POST /answers {questionId, body}

예외/에러 응답 표준화(문제 세부코드)

Swagger UI 노출(/swagger-ui)
완료 기준

Postman 컬렉션으로 시나리오 테스트 통과

최소 통합 테스트 5개 이상

4. 직업 정규화 v0(룰/사전) (반~1일)

목표: “미용사/헤어디자이너/이발사” → 헤어디자이너로 매핑
해야 할 일

job_category 시드 데이터(aliases 포함)

서비스 레이어에 간단 매칭(정규화, 공백 제거, 별칭 비교)

컨피던스 미달 시 raw_job_text만 저장(보류)
완료 기준

POST /questions 시 job_category_id 자동 세팅(사전 히트 시)

5. 검색/피드 성능 최소화 (반~1일)

목표: 기본 인덱스와 캐시로 피드 빠르게
해야 할 일

DB 인덱스: question(job_category_id, created_at), answer(question_id, created_at)

Redis: 최신 피드 키(직업별 리스트) 캐시(유효시간 30~120초)

score 필드(조회·채택 시 증가) 골격만
완료 기준

1만 건 시드 넣고 최근 피드 200ms 내 응답(로컬 기준)

6. 임베딩 사이드카 v1(API 계약만) (반~1일)

목표: Python 서버 없이도 계약/호출부 준비
해야 할 일

EmbeddingClient 인터페이스 + FakeEmbeddingClient(무작위 벡터 반환)

question_embedding(question_id, vector) 테이블(추가 Flyway V2__embedding.sql)

질문 등록 시 임베딩 호출(비동기) 후 저장
완료 기준

로컬에서 Fake로도 파이프라인 정상 동작

7. 유사 질문 제안 v1 (반~1일)

목표: 질문 작성 시 상단에 3~5개 유사 질문 반환
해야 할 일

pgvector 설치/적용(로컬): vector(768) 컬럼

SELECT ... ORDER BY embedding <-> :vec LIMIT 5

API: POST /questions/similar {title, body, jobText}
완료 기준

더미 데이터 기준 유의미한 유사 문서가 반환

8. ML 사이드카 실제 도입 (1일)

목표: 실제 임베딩 모델 연결
해야 할 일

FastAPI로 /embed 구현(sentence-transformers: ko 멀티태스크 모델)

Spring WebClient로 호출, 타임아웃/재시도 설정

사이드카/DB/API 모두 Compose로 기동
완료 기준

실제 임베딩 값이 저장되고 유사도 품질이 향상됨

9. 운영 준비(기본) (1일)

목표: AWS 단일 인스턴스 배포
해야 할 일

Docker 이미지 빌드/푸시, EC2(또는 Lightsail)에 Compose 배포

Secrets 분리(AWS Secrets Manager or .env)

액세스 로그/애플리케이션 로그 CloudWatch 에이전트(초기엔 파일 로테이션도 OK)

헬스체크 엔드포인트/레이트리밋 간단 적용
완료 기준

외부에서 HTTPS로 API 접근 가능(ALB/ACM는 2차로)

10. 품질 보강 루프 (상시 반복, 각 반~1일)

테스트: 컨트롤러 슬라이스, 통합, 리포지토리 테스트 늘리기

보안: Spring Security 기본 세션/JWT, 입력 필터(PII/금칙어)

관측: Actuator metrics(+Prometheus/Grafana는 추후), 슬로우 쿼리 로깅

배치: 야간 재임베딩/인기 피드 스냅샷(Spring Batch or 단순 크론)
