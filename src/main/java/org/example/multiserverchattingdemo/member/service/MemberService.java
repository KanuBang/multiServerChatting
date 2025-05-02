package org.example.multiserverchattingdemo.member.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.multiserverchattingdemo.member.domain.Member;
import org.example.multiserverchattingdemo.member.dto.MemberListResDto;
import org.example.multiserverchattingdemo.member.dto.MemberLoginReqDto;
import org.example.multiserverchattingdemo.member.dto.MemberSaveReqDto;
import org.example.multiserverchattingdemo.member.repository.MemberRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public Member create(MemberSaveReqDto memberSaveReqDto) {
        if (memberRepository.findByEmail(memberSaveReqDto.getEmail()).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }

        Member newMember = Member.builder().name(memberSaveReqDto.getName())
                .email(memberSaveReqDto.getEmail())
                .password(passwordEncoder.encode(memberSaveReqDto.getPassword()))
                .build();
        Member member = memberRepository.save(newMember);
        return member;
    }

    public Member login(MemberLoginReqDto memberLoginReqDto){
        Member member = memberRepository.findByEmail(memberLoginReqDto.getEmail())
                .orElseThrow(() -> new EntityNotFoundException("해당 이메일을 가진 사용자가 없습니다."));
        System.out.println(member.getPassword());
        System.out.println(memberLoginReqDto.getPassword());
        if(passwordEncoder.matches(passwordEncoder.encode(memberLoginReqDto.getPassword()), member.getPassword())){
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        return member;
    }

    public List<MemberListResDto> findAll(){
        List<Member> members = memberRepository.findAll();
        List<MemberListResDto> memberListResponse = new ArrayList<>();
        for (Member member : members) {
            MemberListResDto memberListResDto = new MemberListResDto();
            memberListResDto.setId(member.getId());
            memberListResDto.setEmail(member.getEmail());
            memberListResDto.setName(member.getName());
            memberListResponse.add(memberListResDto);
        }
        return memberListResponse;

    }
}
