package com.ll.exam.qsl.user.repository;

import com.ll.exam.qsl.user.entity.QSiteUser;
import com.ll.exam.qsl.user.entity.SiteUser;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;
import java.util.function.LongSupplier;

import static com.ll.exam.qsl.interestKeyword.entity.QInterestKeyword.interestKeyword;
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


    //페이지를 리턴할 때는 무조건 쿼리가 두개 있어야 한다. 전체 개수를 가져오는 쿼리, 해당한는 현재 페이지 가져오는 쿼리.
    @Override
    public Page<SiteUser> searchQsl(String kw, Pageable pageable) {
        // fetch 하기 전에 중간에 끊으려면 JPAQuery로 받으면 된다.
            JPAQuery<SiteUser> usersQuery = jpaQueryFactory
                .select(siteUser)
                .from(siteUser)
                .where(
                        siteUser.username.contains(kw)
                                .or(siteUser.email.contains(kw))
                )
                .offset(pageable.getOffset()) // 몇개를 건너 띄어야 하는지 LIMIT {1}, ?
                .limit(pageable.getPageSize()); // 한페이지에 몇개가 보여야 하는지 LIMIT ?, {1}

        for (Sort.Order o : pageable.getSort()) {
            PathBuilder pathBuilder = new PathBuilder(siteUser.getType(), siteUser.getMetadata());
            usersQuery.orderBy(new OrderSpecifier(o.isAscending() ? Order.ASC : Order.DESC, pathBuilder.get(o.getProperty())));
        }

        List<SiteUser> users = usersQuery.fetch();

        JPAQuery<Long> usersCountQuery = jpaQueryFactory
                .select(siteUser.count())
                .from(siteUser)
                .where(
                        siteUser.username.contains(kw)
                                .or(siteUser.email.contains(kw))
                );

        // PageableExecutionUtils.getPage 페이지를 만들어준다.
        // 현재는 List를 Page형태로 바꿔주고 있다.
        // 함수 자체를 넘기고 싶으면 :: 를 써야한다.

        return PageableExecutionUtils.getPage(users, pageable, usersCountQuery::fetchOne);
    }

    @Override
    public List<SiteUser> getQslUsersByInterestKeyword(String keywordContent) {
        /*
        SELECT SU. *
        FROM site_user AS SU
        INNER JOIN site_user_interest_keywords AS SUIK
        ON SU.id = SUIK.site_user_id
        INNER JOIN interest_keyword AS IK
        ON IK.content = SUIK.interest_keywords_content
        WHERE IK.content = "호캉스";
         */

        return jpaQueryFactory
                .selectFrom(siteUser)
                .innerJoin(siteUser.interestKeywords, interestKeyword)
                .where(
                        interestKeyword.content.eq(keywordContent)
                )
                .fetch();
    }

//    @Override
//    public List<String> getKeywordContentsByFollowingsOf(SiteUser user) {
//        QSiteUser siteUser2 = new QSiteUser("siteUser2");
//
//        return jpaQueryFactory
//                .select(interestKeyword.content)
//                .distinct()
//                .from(interestKeyword)
//                .innerJoin(interestKeyword.user, siteUser) // site_user
//                .innerJoin(siteUser.followers, siteUser2)
//                .where(siteUser2.id.eq(user.getId()))
//                .fetch();
//    }


    // 위 코드보다 가독성 좋은 코드
    @Override
    public List<String> getKeywordContentsByFollowingsOf(SiteUser user) {
        return jpaQueryFactory
                .select(interestKeyword.content)
                .distinct()
                .from(interestKeyword)
                .where(interestKeyword.user.in(user.getFollowings()))
                .fetch();
    }
}
