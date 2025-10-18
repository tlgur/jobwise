package com.jobwise.api.repository;

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;

import java.lang.reflect.Field;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public abstract class RepositoryTest {

    protected static final MySQLContainer<?> MYSQL =
            new MySQLContainer<>("mysql:8.0")
                    .withDatabaseName("jobwise")
                    .withUsername("root")
                    .withPassword("root")
                    .withReuse(true);

    static{
        MYSQL.start();
    }

    @DynamicPropertySource
    static void overrideProps(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", MYSQL::getJdbcUrl);
        registry.add("spring.datasource.username", MYSQL::getUsername);
        registry.add("spring.datasource.password", MYSQL::getPassword);

        // Hikari: 끊김 방지(컨테이너 공유 시 안정화)
        registry.add("spring.datasource.hikari.maximumPoolSize", () -> "5");
        registry.add("spring.datasource.hikari.minimumIdle", () -> "0");
        registry.add("spring.datasource.hikari.connectionTimeout", () -> "30000");
        registry.add("spring.datasource.hikari.validationTimeout", () -> "5000");
        registry.add("spring.datasource.hikari.maxLifetime", () -> "120000"); // DB wait_timeout보다 짧게
        registry.add("spring.datasource.hikari.keepaliveTime", () -> "30000");

        // 테스트에서 2차 캐시 비활성화(독립성)
        registry.add("spring.jpa.properties.hibernate.cache.use_second_level_cache", () -> "false");
    }

    protected void setId(Object target, String fieldName, Object value) {
        try {
            Field field = target.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);   // private 필드 접근 허용
            field.set(target, value);    // 값 주입
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
