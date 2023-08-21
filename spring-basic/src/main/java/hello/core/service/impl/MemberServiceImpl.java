package hello.core.service.impl;

import hello.core.domain.entity.Member;
import hello.core.repository.MemberRepository;
import hello.core.repository.impl.MemberRepositoryImpl;
import hello.core.service.MemberService;

public class MemberServiceImpl implements MemberService {

    private MemberRepository memberRepository = new MemberRepositoryImpl();

    @Override
    public void join(Member member) {
        memberRepository.save(member);
    }

    @Override
    public Member findMember(Long memberId) {
        return memberRepository.findById(memberId);
    }
}
