package com.ll.exam.qsl.user.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

// @Entity는 JPA를 사용해 테이블과 매핑할 클래스에 붙여주는 어노테이션이다. 객체와 테이블 매핑.
// 이 어노테이션을 붙임으로써 JPA가 해당 클래스를 관리하게 된다.
@Entity
// @Getter , @Setter (접근자/설정자 생성)
// @Getter @Setter 어노테이션을 붙이주면 명클래스 멤버 변수들의 getter, setter 메소드를 이용 할 수 있다.
@Getter
@Setter
// @AllArgsConstructor 어노테이션은 모든 필드 값을 파라미터로 받는 생성자를 만들어준다.
@AllArgsConstructor
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
}
