package com.brainbackdoor.subwaymap.auth.application;

import com.brainbackdoor.subwaymap.auth.domain.LoginMember;
import com.brainbackdoor.subwaymap.auth.dto.TokenRequest;
import com.brainbackdoor.subwaymap.auth.dto.TokenResponse;
import com.brainbackdoor.subwaymap.auth.infrastructure.JwtTokenProvider;
import com.brainbackdoor.subwaymap.member.domain.Member;
import com.brainbackdoor.subwaymap.member.domain.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AuthService {

    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthService(MemberRepository memberRepository, JwtTokenProvider jwtTokenProvider) {
        this.memberRepository = memberRepository;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public TokenResponse login(TokenRequest request) {
        Member member = memberRepository.findByEmail(request.email()).orElseThrow(AuthorizationException::new);
        member.checkPassword(request.password());

        String token = jwtTokenProvider.createToken(request.email());
        return new TokenResponse(token);
    }

    public LoginMember findMemberByToken(String credentials) {
        if (!jwtTokenProvider.validateToken(credentials)) {
            throw new AuthorizationException();
        }

        String email = jwtTokenProvider.getPayload(credentials);
        Member member = memberRepository.findByEmail(email).orElseThrow(RuntimeException::new);
        return new LoginMember(member.getId(), member.getEmail(), member.getAge());
    }
}
