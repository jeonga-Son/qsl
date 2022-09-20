package com.ll.exam.qsl.interestKeyword.entity;

import com.ll.exam.qsl.user.entity.SiteUser;
import lombok.*;

import javax.persistence.*;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
// @EqualsAndHashCode 은 equals, hashCode 자동 생성 해준다.
// equals :  두 객체의 내용이 같은지, 동등성(equality) 를 비교하는 연산자
// hashCode : 두 객체가 같은 객체인지, 동일성(identity) 를 비교하는 연산자
// @EqualsAndHashCode.Include가 붙은 필드만 포함시킬 수 있다.
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
// @IdClass는 복합 Primary Key를 적용할 때 사용한다.
@IdClass(InterestKeywordId.class)
//답변
public class InterestKeyword {

    //복합키 구현
    @Id
    //양방향
    @ManyToOne
    @EqualsAndHashCode.Include
    private SiteUser user; // site_user_id로 바뀜.

    @Id
    @EqualsAndHashCode.Include
    private String content;
}
