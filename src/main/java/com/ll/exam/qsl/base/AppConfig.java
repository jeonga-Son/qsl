package com.ll.exam.qsl.base;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;

// @Configuration을 달아야 @Bean이 스캔이 된다.
@Configuration
@Slf4j
public class AppConfig {
    // 수동으로 스프링 컨테이너에 빈을 등록. 메소드 이름으로 빈 이름이 결정된다.
    // 따라서 중복된 빈 이름이 존재하면 안된다.
    // 여기서는 JPAQueryFactory를 제공해주는 Bean이다.
    // JPAQueryFactory가 있어야 QueryDSL을쓸 수 있다.
    @Bean
    public JPAQueryFactory jpaQueryFactory(EntityManager entityManager) {
        return new JPAQueryFactory(entityManager);
    }
}
