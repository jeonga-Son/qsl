package com.ll.exam.qsl;

import com.ll.exam.qsl.user.entity.SiteUser;
import com.ll.exam.qsl.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class UserRepositoryTests {
    // @Autowired는 의존관계 주입(DI)을 할 때 사용하는 어노테이션(Annotation)이며, IoC컨테이너에 존재하는 빈(Bean)을 찾아 주입하는 역할을 한다.
    @Autowired
    private UserRepository userRepository;

    @Test
    //@DisplayName 어노테이션을 선언함으로써 테스트 클래스나 메서드명이 아닌 사용자가 정의한 명칭으로 변경할 수 있는 기능을 제공한다.
    @DisplayName("회원 생성")
    void t1() {
        // builder 사용하면 순서 상관없음!
        // 패스워드는 암호화 안하면 {noop} 붙이는 것이 관례
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

//        SiteUser u2 = new SiteUser(null, "user2", "{noop}1234", "user2@test.com");

        userRepository.saveAll(Arrays.asList(u1, u2));
        // Array(배열)을 List로 변경할때 사용한다.
        // asList()를 사용해서 객체를 만들때 새로운 배열 객체를 만드는 것이 아니라, 원본배열의 주소값을 참조한다.
        // asList()를 사용해서 내용을 수정하면 원본 배열도 함께 바뀌게 됨
        // Arrays.asList()로 만든 List에 새로운 원소를 추가하거나 삭제는 할 수 없다.
    }

    @Test
    @DisplayName("1번 회원을 Qsl로 가져오기")
    void t2() {
        SiteUser u1 = userRepository.getQslUser(1L);

        assertThat(u1.getId()).isEqualTo(1L);
        assertThat(u1.getUsername()).isEqualTo("user1");
        assertThat(u1.getEmail()).isEqualTo("user1@test.com");
        assertThat(u1.getPassword()).isEqualTo("{noop}1234");
    }

    @Test
    @DisplayName("2번 회원을 Qsl로 가져오기")
    void t3() {
        SiteUser u2 = userRepository.getQslUser(2L);

        assertThat(u2.getId()).isEqualTo(2L);
        assertThat(u2.getUsername()).isEqualTo("user2");
        assertThat(u2.getEmail()).isEqualTo("user2@test.com");
        assertThat(u2.getPassword()).isEqualTo("{noop}1234");
    }
}
