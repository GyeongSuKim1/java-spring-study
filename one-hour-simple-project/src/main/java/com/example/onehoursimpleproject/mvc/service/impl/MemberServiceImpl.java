package com.example.onehoursimpleproject.mvc.service.impl;

import com.example.onehoursimpleproject.domain.entity.Member;
import com.example.onehoursimpleproject.mvc.repository.MemberRepository;
import com.example.onehoursimpleproject.mvc.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    @Override
    public String join(String id, String name, String phone) {

        Member member = Member.builder()
                .id(id)
                .name(name)
                .phone(phone)
                .build();

        memberRepository.save(member);

        return "success";
    }
}
