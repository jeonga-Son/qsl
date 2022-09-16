package com.ll.exam.qsl;

import com.ll.exam.qsl.user.entity.SiteUser;
import com.ll.exam.qsl.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
// 클래스에 @Transactional를 붙이면, 클래스의 각 테스트케이스에 전부 @Transactional이 붙은 것과 동일.
// @Test + @Transactional 조합은 자동으로 롤백을 유발시킨다.
@Transactional
// 테스트 모드 활성화
@ActiveProfiles("test")
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
        SiteUser u3 = SiteUser.builder()
                .username("user3")
                .password("{noop}1234")
                .email("user3@test.com")
                .build();

        SiteUser u4 = SiteUser.builder()
                .username("user4")
                .password("{noop}1234")
                .email("user4@test.com")
                .build();

//        SiteUser u2 = new SiteUser(null, "user2", "{noop}1234", "user2@test.com");

        userRepository.saveAll(Arrays.asList(u3, u4));
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

    @Test
    @DisplayName("모든 회원 수")
    void t4() {
        long count = userRepository.getQslCount();

        assertThat(count).isGreaterThan(0);
    }

    @Test
    @DisplayName("가장 오래된 회원 1명")
    void t5() {
        SiteUser u1 = userRepository.getQslUserOrderByIdAscOne();

        assertThat(u1.getId()).isEqualTo(1L);
        assertThat(u1.getUsername()).isEqualTo("user1");
        assertThat(u1.getEmail()).isEqualTo("user1@test.com");
        assertThat(u1.getPassword()).isEqualTo("{noop}1234");
    }

    @Test
    @DisplayName("전체회원, 오래된 순")
    void t6() {
        List<SiteUser> users = userRepository.getQslUsersOrderByIdAsc();

        SiteUser u1 = users.get(0);

        assertThat(u1.getId()).isEqualTo(1L);
        assertThat(u1.getUsername()).isEqualTo("user1");
        assertThat(u1.getEmail()).isEqualTo("user1@test.com");
        assertThat(u1.getPassword()).isEqualTo("{noop}1234");

        SiteUser u2 = users.get(1);

        assertThat(u2.getId()).isEqualTo(2L);
        assertThat(u2.getUsername()).isEqualTo("user2");
        assertThat(u2.getEmail()).isEqualTo("user2@test.com");
        assertThat(u2.getPassword()).isEqualTo("{noop}1234");
    }

    @Test
    @DisplayName("검색, List 리턴")
    void t7() {
        // 검색대상 : username, email
        // user1 로 검색
        List<SiteUser> users = userRepository.searchQsl("user1");

        assertThat(users.size()).isEqualTo(1);

        SiteUser u = users.get(0);

        assertThat(u.getId()).isEqualTo(1L);
        assertThat(u.getUsername()).isEqualTo("user1");
        assertThat(u.getEmail()).isEqualTo("user1@test.com");
        assertThat(u.getPassword()).isEqualTo("{noop}1234");

        // user2 로 검색
        users = userRepository.searchQsl("user2");

        assertThat(users.size()).isEqualTo(1);

        u = users.get(0);

        assertThat(u.getId()).isEqualTo(2L);
        assertThat(u.getUsername()).isEqualTo("user2");
        assertThat(u.getEmail()).isEqualTo("user2@test.com");
        assertThat(u.getPassword()).isEqualTo("{noop}1234");
    }

    @Test
    @DisplayName("검색, Page 리턴, id Asc, pageSize=1, page=0")
    void t8() {
        long totalCount = userRepository.count();
        int pageSize = 1; // 한 페이지에 보여줄 아이템 개수
        int totalPages = (int)Math.ceil(totalCount / (double)pageSize);
        int page = 1;
        String kw = "user";

        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.asc("id"));
        //Pageable은 각 페이지의 Size, 전체 페이지 수, 전체 게시물 개수 등의 정보를 알 수 있고,
        //이는 페이징 작업할 때 관리자에게 알려주는 지표로 자주 활용된다.
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by(sorts)); // 한 페이지에 itemsInAPage만큼 가능
        Page<SiteUser> usersPage = userRepository.searchQsl(kw, pageable);

        assertThat(usersPage.getTotalPages()).isEqualTo(totalPages);
        assertThat(usersPage.getNumber()).isEqualTo(page);
        assertThat(usersPage.getSize()).isEqualTo(pageSize);

        // get()은 데이터를 가져올 때 Stream 형태로 가져온다.
        // toList()은 리스트 형태로 반환한다.
        List<SiteUser> users = usersPage.get().toList();

        assertThat(users.size()).isEqualTo(pageSize);

        SiteUser u = users.get(0);

        assertThat(u.getId()).isEqualTo(2L);
        assertThat(u.getUsername()).isEqualTo("user2");
        assertThat(u.getEmail()).isEqualTo("user2@test.com");
        assertThat(u.getPassword()).isEqualTo("{noop}1234");

        // 검색어 : user
        // 한 페이지에 나올 수 있는 아이템 수 : 1개
        // 현재 페이지 : 1
        // 정렬 : id 순차

        // 내용 가져오는 SQL
        /*
        SELECT site_user.*
        FROM site_user
        WHERE site_user.username LIKE '%user%'
        OR site_user.email LIKE '%user%'
        ORDER BY site_user.id ASC
        LIMIT 1, 1
         */

        // 전체 개수 계산하는 SQL
         /*
        SELECT count(*)>>>
        FROM site_user
        WHERE site_user.username LIKE '%user%'
        OR site_user.email LIKE '%user%'
         */
    }

    @Test
    @DisplayName("검색, Page 리턴, id DESC, pageSize=1, page=0")
    void t9() {
        long totalCount = userRepository.count();
        int pageSize = 1;
        int totalPages = (int)Math.ceil(totalCount / (double)pageSize);
        int page = 1;
        String kw = "user";

        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("id"));
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by(sorts));
        Page<SiteUser> usersPage = userRepository.searchQsl(kw, pageable);

        assertThat(usersPage.getTotalPages()).isEqualTo(totalPages);
        assertThat(usersPage.getNumber()).isEqualTo(page);
        assertThat(usersPage.getSize()).isEqualTo(pageSize);

        List<SiteUser> users = usersPage.get().toList();

        assertThat(users.size()).isEqualTo(pageSize);

        SiteUser u = users.get(0);

        assertThat(u.getId()).isEqualTo(1L);
        assertThat(u.getUsername()).isEqualTo("user1");
        assertThat(u.getEmail()).isEqualTo("user1@test.com");
        assertThat(u.getPassword()).isEqualTo("{noop}1234");
    }

    @Test
    @Rollback(false)
    @DisplayName("회원에게 관심사를 등록할 수 있다.")
    void v10() {
        SiteUser u2 = userRepository.getQslUser(2L);

        u2.addInterestKeywordContent("여행");
        u2.addInterestKeywordContent("롤토체스");
        u2.addInterestKeywordContent("힐링");
        u2.addInterestKeywordContent("힐링"); // 중복등록은 무시

        userRepository.save(u2);
        // 엔티티클래스 : InterestKeyword(interest_keyword 테이블)
        // 중간테이블도 생성되어야 함, 힌트 : @ManyToMany
        // interest_keyword 테이블에 축구, 롤, 헬스에 해당하는 row 3개 생성
    }
}
