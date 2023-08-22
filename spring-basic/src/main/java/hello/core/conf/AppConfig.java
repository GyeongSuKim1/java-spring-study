package hello.core.conf;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.impl.RateDiscountPolicyImpl;
import hello.core.repository.MemberRepository;
import hello.core.repository.impl.MemberRepositoryImpl;
import hello.core.service.MemberService;
import hello.core.service.OrderService;
import hello.core.service.impl.MemberServiceImpl;
import hello.core.service.impl.OrderServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public MemberService memberService() {
        return new MemberServiceImpl(memberRepository());
    }

    @Bean
    public OrderService orderService() {
        return new OrderServiceImpl(
                memberRepository(),
                discountPolicy()
        );
    }

    @Bean
    public MemberRepository memberRepository() {
        return new MemberRepositoryImpl();
    }

    @Bean
    public DiscountPolicy discountPolicy() {
//        return new FixDiscountPolicyImpl();
        return new RateDiscountPolicyImpl();
    }
}
