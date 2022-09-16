package com.ll.exam.qsl;

import com.ll.exam.qsl.user.entity.SiteUser;
import com.ll.exam.qsl.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

@SpringBootTest
class UserRepositoryTests {
    // @Autowired는 의존관계 주입(DI)을 할 때 사용하는 어노테이션(Annotation)이며, IoC컨테이너에 존재하는 빈(Bean)을 찾아 주입하는 역할을 한다.
    @Autowired
    private UserRepository userRepository;

    @Test
    //@DisplayName 어노테이션을 선언함으로써 테스트 클래스나 메서드명이 아닌 사용자가 정의한 명칭으로 변경할 수 있는 기능을 제공한다.
    @DisplayName("회원 생성")
    void t1() {
        SiteUser u1 = new SiteUser(null, "user1", "{noop}1234", "user1@test.com"); // 패스워드는 암호화 안하면 {noop} 붙이는 것이 관례
        SiteUser u2 = new SiteUser(null, "user2", "{noop}1234", "user2@test.com");

        userRepository.saveAll(Arrays.asList(u1, u2));
        // Array(배열)을 List로 변경할때 사용한다.
        // asList()를 사용해서 객체를 만들때 새로운 배열 객체를 만드는 것이 아니라, 원본배열의 주소값을 참조한다.
        // asList()를 사용해서 내용을 수정하면 원본 배열도 함께 바뀌게 됨
        // Arrays.asList()로 만든 List에 새로운 원소를 추가하거나 삭제는 할 수 없다.
    }

}
