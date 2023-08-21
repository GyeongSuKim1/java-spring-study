package hello.core.conf;

import hello.core.discount.impl.FixDiscountPolicyImpl;
import hello.core.repository.MemberRepository;
import hello.core.repository.impl.MemberRepositoryImpl;
import hello.core.service.MemberService;
import hello.core.service.OrderService;
import hello.core.service.impl.MemberServiceImpl;
import hello.core.service.impl.OrderServiceImpl;

public class AppConfig {

    public MemberService memberService() {
        return new MemberServiceImpl(new MemberRepositoryImpl());
    }
    
    public OrderService orderService() {
        return new OrderServiceImpl(
                new MemberRepositoryImpl(),
                new FixDiscountPolicyImpl()
        );
    }
}
