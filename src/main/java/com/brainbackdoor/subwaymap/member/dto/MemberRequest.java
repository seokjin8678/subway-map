package com.brainbackdoor.subwaymap.member.dto;


import com.brainbackdoor.subwaymap.member.domain.Member;

public record MemberRequest(String email, String password, Integer age) {

    public Member toMember() {
        return new Member(email, password, age);
    }
}
