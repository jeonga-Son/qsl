package com.ll.exam.qsl.user.repository;

import com.ll.exam.qsl.user.entity.SiteUser;

public interface UserRepositoryCustom {
    SiteUser getUser(Long id);
}
