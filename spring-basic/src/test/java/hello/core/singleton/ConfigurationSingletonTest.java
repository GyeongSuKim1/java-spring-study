package hello.core.singleton;

import hello.core.conf.AppConfig;
import hello.core.repository.MemberRepository;
import hello.core.service.impl.MemberServiceImpl;
import hello.core.service.impl.OrderServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.junit.jupiter.api.Assertions.assertSame;

public class ConfigurationSingletonTest {

    @Test
    @DisplayName("")
    public void configurationTest() {

        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

        MemberServiceImpl memberService = ac.getBean("memberService", MemberServiceImpl.class);
        OrderServiceImpl orderService = ac.getBean("orderService", OrderServiceImpl.class);
        MemberRepository memberRepository = ac.getBean("memberRepository", MemberRepository.class);

        // 모두 같은 인스턴스를 참고하고 있다.
        System.out.println("memberService -> memberRepository: " + memberService.getMemberRepository());
        System.out.println("orderService -> memberRepository: " + orderService.getMemberRepository());
        System.out.println("memberRepository: " + memberRepository);

        // 모두 같은 인스턴스를 참고하고 있다.
        assertSame(memberService.getMemberRepository(), memberRepository);
        assertSame(orderService.getMemberRepository(), memberRepository);
    }

    @Test
    @DisplayName("")
    public void configurationDeep() {

        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

        // AppConfig도 스프링 빈으로 등록된다.
        AppConfig bean = ac.getBean(AppConfig.class);

        System.out.println("bean: " + bean.getClass());
    }
}
