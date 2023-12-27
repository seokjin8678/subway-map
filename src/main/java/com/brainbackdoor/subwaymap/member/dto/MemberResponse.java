package com.brainbackdoor.subwaymap.member.dto;


import com.brainbackdoor.subwaymap.member.domain.Member;

public record MemberResponse(Long id, String email, Integer age) {

    public static MemberResponse of(Member member) {
        return new MemberResponse(
            member.getId(),
            member.getEmail(),
            member.getAge()
        );
    }
}
