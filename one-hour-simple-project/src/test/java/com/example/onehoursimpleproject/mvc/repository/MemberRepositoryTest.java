package com.example.onehoursimpleproject.mvc.repository;

import com.example.onehoursimpleproject.domain.entity.Member;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach
    void beforeEach() {
        Member member = Member.builder()
                .name("닉네임")
                .phone("010-1111-2222")
                .build();

        memberRepository.save(member);
    }

    @Test
    public void crudTest() {
        Member foundMember = memberRepository.findById(1L).get();
    }

}