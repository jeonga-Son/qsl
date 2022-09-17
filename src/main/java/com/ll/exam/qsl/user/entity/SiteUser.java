package com.ll.exam.qsl.user.entity;

import com.ll.exam.qsl.interestKeyword.entity.InterestKeyword;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

// @Entity는 JPA를 사용해 테이블과 매핑할 클래스에 붙여주는 어노테이션이다. 객체와 테이블 매핑.
// 이 어노테이션을 붙임으로써 JPA가 해당 클래스를 관리하게 된다.
@Entity
// @Getter , @Setter (접근자/설정자 생성)
// @Getter @Setter 어노테이션을 붙이주면 명클래스 멤버 변수들의 getter, setter 메소드를 이용 할 수 있다.
@Getter
@Setter
// @NoArgsConstructor 어노테이션은 파라미터가 없는 기본 생성자를 생성해준다.
@NoArgsConstructor
// @AllArgsConstructor 어노테이션은 모든 필드 값을 파라미터로 받는 생성자를 만들어준다.
@AllArgsConstructor
// @Builder는 @AllArgsConstructor가 꼭 있어야 한다.
//클래스 레벨에서 @Builder 어노테이션을 붙이면 모든 요소를 받는 package-private 생성자가 자동으로 생성되며 이 생성자에 @Builder 어노테이션을 붙인 것과 동일하게 동작한다.
// 즉 Builder는 내부적으로 전체인자생성자를 호출한다.
@Builder
public class SiteUser {
    //기본키 매핑
    // 테이블 상의 Primary Key 와 같은 의미를 가지며 @Id 어노테이션으로 표기
    @Id
    // 기본 키 생성을 데이터베이스에 위임.
    // 즉, id 값을 null로 하면 DB가 알아서 AUTO_INCREMENT 해준다.
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 필드와 컬럼 매핑
    // DB에 영향을 주는 것
    //해당 필드는 unique 함을 의미
    @Column(unique = true)
    private String username;

    private String password;

    @Column(unique = true)
    private String email;

    // @Builder default 어노테이션을 붙이게 되면 초기화를 시키지 않더라도 기본값을 지정할 수 있다.
    @Builder.Default
    // CascadeType.ALL: 모든 Cascade를 적용
    @ManyToMany(cascade = CascadeType.ALL)
    private Set<InterestKeyword> interestKeywords = new HashSet<>();

    @Builder.Default
    @ManyToMany(cascade = CascadeType.ALL)
    private Set<SiteUser> followers = new HashSet<>();

    public void addInterestKeywordContent(String keywordContent) {
        interestKeywords.add(new InterestKeyword(keywordContent));
    }

    public void follow(SiteUser following) {
        following.getFollowers().add(this);
    }
}
