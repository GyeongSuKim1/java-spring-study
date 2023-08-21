package hello.core.discount.impl;

import hello.core.discount.DiscountPolicy;
import hello.core.domain.entity.Member;
import hello.core.domain.type.Grade;

public class FixDiscountPolicyImpl implements DiscountPolicy {

    private int discountFixAmount = 1000; // 할인 금액

    @Override
    public int discount(Member member, int price) {

        if (member.getGrade() == Grade.VIP) {
            return discountFixAmount;
        } else {
            return 0;
        }
    }
}
