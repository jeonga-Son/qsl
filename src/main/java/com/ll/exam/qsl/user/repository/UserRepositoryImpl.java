package com.ll.exam.qsl.user.repository;

import com.ll.exam.qsl.user.entity.SiteUser;
import org.springframework.data.jpa.repository.JpaRepository;

// 꼭 Impl이라고 해야한다! JPA rule이다.
public class UserRepositoryImpl implements UserRepositoryCustom {

    @Override
    public SiteUser getUser(Long id) {
        return null;
    }

}
