package com.ll.exam.qsl.user.repository;

import com.ll.exam.qsl.user.entity.SiteUser;
import org.springframework.data.jpa.repository.JpaRepository;

// extends를 통해 JpaRepository<> 상속받고 <> 안에 레포지토리에 연결할 엔티티 클래스명, 엔티티의 PK 자료형을 적어준다.
public interface UserRepository extends JpaRepository<SiteUser, Long> {
}
