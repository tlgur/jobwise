# 1. 빌드 단계 (Gradle로 JAR 생성)
FROM eclipse-temurin:21-jdk AS builder

# 작업 디렉토리
WORKDIR /app

# Gradle Wrapper 및 설정 파일만 먼저 복사 (캐시 활용)
COPY gradlew .
COPY gradle gradle
COPY build.gradle settings.gradle ./

# Gradle 의존성 미리 다운로드 (빌드 속도 최적화)
RUN ./gradlew dependencies --no-daemon

# 소스코드 복사 후 빌드
COPY src src
RUN ./gradlew bootJar --no-daemon

# 2. 실행 단계 (경량 JRE로 실행)
FROM eclipse-temurin:21-jre

WORKDIR /app

# 빌드 단계에서 만든 JAR 복사
COPY --from=builder /app/build/libs/*.jar app.jar

# 컨테이너 시작 시 실행할 명령
ENTRYPOINT ["java", "-jar", "app.jar"]

# 참고용 포트 (Spring Boot 기본 8080)
EXPOSE 8080
