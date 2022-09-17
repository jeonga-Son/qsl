package com.ll.exam.qsl.base;

import com.ll.exam.qsl.user.entity.SiteUser;
import com.ll.exam.qsl.user.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.Arrays;
import java.util.List;

@Configuration
// @Profile 어노테이션을 통해 스프링 환경설정을 할 수 있다.
// 이 클래스 정의된 Bean 들은 test 모드에서만 활성화 된다.
@Profile("test")
public class TestInitData {
    @Bean
    // CommandLineRunner : 주로 앱 실행 직후 초기데이터 세팅 및 초기화에 사용
    CommandLineRunner init(UserRepository userRepository) {
        return args -> {
            SiteUser u1 = SiteUser.builder()
                    .username("user1")
                    .password("{noop}1234")
                    .email("user1@test.com")
                    .build();

            SiteUser u2 = SiteUser.builder()
                    .username("user2")
                    .password("{noop}1234")
                    .email("user2@test.com")
                    .build();

            u1.addInterestKeywordContent("호캉스");
            u1.addInterestKeywordContent("별보러가기");

            u2.addInterestKeywordContent("밤산책");
            u2.addInterestKeywordContent("바다구경");

            userRepository.saveAll(Arrays.asList(u1, u2));
        };
    }
}
