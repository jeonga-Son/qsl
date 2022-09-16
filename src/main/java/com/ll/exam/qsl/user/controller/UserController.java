package com.ll.exam.qsl.user.controller;

import com.ll.exam.qsl.user.entity.SiteUser;
import com.ll.exam.qsl.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// @RestController를 쓰면 ResponseBody를 안붙여도 된다.
// @Controller + @ResponseBody = @RestController
@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserRepository userRepository;

    // @RequestMapping 요청 URL을 어떤 method가 처리할지 mapping해주는 Annotation이다.
   @RequestMapping("/user/{id}")
    // @PathVariable 어노테이션은 매핑의 URL에 { } 로 들어가는 패스 변수(path variable)를 받는다.
    public SiteUser user(@PathVariable Long id) {
       return userRepository.getQslUser(id);
    }
}
