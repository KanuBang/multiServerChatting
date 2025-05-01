package org.example.multiserverchattingdemo.member.service;

import lombok.RequiredArgsConstructor;
import org.example.multiserverchattingdemo.member.domain.Member;
import org.example.multiserverchattingdemo.member.dto.MemberSaveReqDto;
import org.example.multiserverchattingdemo.member.repository.MemberRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public Member create(MemberSaveReqDto memberSaveReqDto) {
        if (memberRepository.findByEmail(memberSaveReqDto.getEmail()).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }

        Member newMember = Member.builder().name(memberSaveReqDto.getName())
                .email(memberSaveReqDto.getEmail())
                .password(memberSaveReqDto.getPassword())
                .build();
        Member member = memberRepository.save(newMember);
        return member;
    }
}
