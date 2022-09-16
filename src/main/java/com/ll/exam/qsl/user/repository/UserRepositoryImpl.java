package com.ll.exam.qsl.user.repository;

import com.ll.exam.qsl.user.entity.QSiteUser;
import com.ll.exam.qsl.user.entity.SiteUser;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.ll.exam.qsl.user.entity.QSiteUser.siteUser;

//@RequiredArgsConstructor 어노테이션은 생성자 주입을 편리하게 도와주는 lombok 어노테이션이다.
//final이나 @NotNull을 필드 앞에 붙이면 생성자를 자동으로 생성해준다.
//의존성이 많아지는 경우 간결한 생성자 주입을 할 수 있도록 도와준다.
@RequiredArgsConstructor
// 꼭 Impl이라고 해야한다! JPA rule이다.
public class UserRepositoryImpl implements UserRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public SiteUser getQslUser(Long id) {
        /*
        SELECT *
        FROM site_user
         WHERE id = 1
         */

        return jpaQueryFactory
                .select(siteUser)
                .from(siteUser)
                .where(siteUser.id.eq(id))
                .fetchOne();
    }

    @Override
    public long getQslCount() {
       return jpaQueryFactory
               .select(siteUser.count())
               .from(siteUser)
               .fetchOne();
    }

    @Override
    public SiteUser getQslUserOrderByIdAscOne() {
        return jpaQueryFactory
                .select(siteUser)
                .from(siteUser)
                .orderBy(siteUser.id.asc())
                .fetchFirst();
    }

    @Override
    public List<SiteUser> getQslUsersOrderByIdAsc() {
        return jpaQueryFactory
                .select(siteUser)
                .from(siteUser)
                .orderBy(siteUser.id.asc())
                .fetch();
    }
}
