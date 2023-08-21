package hello.core.discount;

import hello.core.discount.impl.DiscountPolicyImpl;
import hello.core.domain.entity.Member;
import hello.core.domain.type.Grade;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DiscountPolicyTest {

    DiscountPolicy discountPolicy = new DiscountPolicyImpl();

    @Test
    @DisplayName("VIP는 10% 할인 적용")
    void vip_o() {
        // given
        Member member = new Member(1L, "memberA", Grade.VIP);

        // when
        int discount = discountPolicy.discount(member, 10000);

        // then
        assertEquals(discount, 1000);
        System.out.println(discount);
    }

    @Test
    @DisplayName("VIP가 아니면 할인 미적용")
    public void vip_x() {
        // given
        Member member = new Member(2L, "memberB", Grade.BASIC);

        // when
        int discount = discountPolicy.discount(member, 10000);

        // then
        assertEquals(discount, 0);
        System.out.println(discount);
    }
}