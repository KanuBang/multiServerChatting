package org.example.multiserverchattingdemo.member.controller;

import lombok.RequiredArgsConstructor;
import org.example.multiserverchattingdemo.common.auth.JwtTokenProvider;
import org.example.multiserverchattingdemo.member.domain.Member;
import org.example.multiserverchattingdemo.member.dto.MemberListResDto;
import org.example.multiserverchattingdemo.member.dto.MemberLoginReqDto;
import org.example.multiserverchattingdemo.member.dto.MemberSaveReqDto;
import org.example.multiserverchattingdemo.member.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/create")
    public ResponseEntity<?> memberCreate(@RequestBody MemberSaveReqDto memberSaveReqDto){
        Member member = memberService.create(memberSaveReqDto);
        return new ResponseEntity<>(member.getId(), HttpStatus.CREATED);
    }

    @PostMapping("/doLogin")
    public ResponseEntity<?> doLogin(@RequestBody MemberLoginReqDto memberLoginReqDto){
        System.out.println("1231231232");
        //email, password 검증
        Member member = memberService.login(memberLoginReqDto);

        //일치할경우 accessToken 발행
        String jwtToken = jwtTokenProvider.createToken(member.getEmail(), member.getRole().toString());
        Map<String,Object> loginInfo = new HashMap<>();
        loginInfo.put("id", member.getId());
        loginInfo.put("token", jwtToken);
        return new ResponseEntity<>(loginInfo, HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity<?> memberList(){
        List<MemberListResDto> dtos = memberService.findAll();
        return new ResponseEntity<>(dtos, HttpStatus.OK);
     }
}
