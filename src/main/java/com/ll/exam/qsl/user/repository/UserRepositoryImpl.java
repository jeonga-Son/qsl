package com.ll.exam.qsl.user.repository;

import com.ll.exam.qsl.user.entity.QSiteUser;
import com.ll.exam.qsl.user.entity.SiteUser;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;
import java.util.function.LongSupplier;

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

    @Override
    public List<SiteUser> searchQsl(String kw) {
        return jpaQueryFactory
                .select(siteUser)
                .from(siteUser)
                .where(
                        siteUser.username.contains(kw)
                                .or(siteUser.email.contains(kw))
                )
                .orderBy(siteUser.id.desc())
                .fetch();
    }

    @Override
    public Page<SiteUser> searchQsl(String kw, Pageable pageable) {
            List<SiteUser> users = jpaQueryFactory
                .select(siteUser)
                .from(siteUser)
                .where(
                        siteUser.username.contains(kw)
                                .or(siteUser.email.contains(kw))
                )
                .offset(pageable.getOffset()) // 몇개를 건너 띄어야 하는지 LIMIT {1}, ?
                .limit(pageable.getPageSize()) // 한 페이지에 몇개가 보여야 하는지 LIMIT ?, {1}
                .orderBy(siteUser.id.asc())
                .fetch();

        LongSupplier totalSupplier = () -> 2;

        // PageableExecutionUtils.getPage 페이지를 만들어준다.
        // 현재는 List를 Page형태로 바꿔주고 있다.
        return PageableExecutionUtils.getPage(users, pageable, totalSupplier);
    }
}
