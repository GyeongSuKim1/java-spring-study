package hello.core.order;

import hello.core.conf.AppConfig;
import hello.core.domain.entity.Member;
import hello.core.domain.entity.Order;
import hello.core.domain.type.Grade;
import hello.core.repository.MemberRepository;
import hello.core.repository.impl.MemberRepositoryImpl;
import hello.core.service.MemberService;
import hello.core.service.OrderService;
import hello.core.service.impl.MemberServiceImpl;
import hello.core.service.impl.OrderServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrderServiceTest {

    MemberService memberService;
    OrderService orderService;

    @BeforeEach
    public void beforeEach() {
        AppConfig appConfig = new AppConfig();
        memberService = appConfig.memberService();
        orderService = appConfig.orderService();
    }


    @Test
    @DisplayName("주문과 할인 정책")
    void createOrder() {
        // given
        long memberId = 1L;
        Member member = new Member(memberId, "memberA", Grade.VIP);

        // when
        memberService.join(member);
        Order order = orderService.createOrder(memberId, "itemA", 10000);

        // then
        assertEquals(order.getDiscountPrice(), 1000);
        System.out.println(order);

    }
}