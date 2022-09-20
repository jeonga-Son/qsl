package com.ll.exam.qsl.interestKeyword.entity;

import com.ll.exam.qsl.user.entity.SiteUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

// @Data 어노테이션을 붙여주면, 모든 필드를 대상으로 접근자와 설정자가 자동으로 생성되고,
// final 또는 @NonNull 필드 값을 파라미터로 받는 생성자가 만들어지며,
// toString, equals, hashCode 메소드가 자동으로 만들어진다.
@Data
@AllArgsConstructor
@NoArgsConstructor
// Serializable을 implements 해주는게 rule! 외워야함.
public class InterestKeywordId implements Serializable {

    private SiteUser user;
    private String content;

}
