package hello.core.discount.impl;

import hello.core.discount.DiscountPolicy;
import hello.core.domain.entity.Member;
import hello.core.domain.type.Grade;

public class DiscountPolicyImpl implements DiscountPolicy {

    private int discountFixAmount = 10; // 10% 할인 금액

    @Override
    public int discount(Member member, int price) {

        if (member.getGrade() == Grade.VIP) {
            return price * discountFixAmount / 100;
        } else {
            return 0;
        }
    }
}
