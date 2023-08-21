package hello.core.service.impl;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.impl.DiscountPolicyImpl;
import hello.core.domain.entity.Member;
import hello.core.domain.entity.Order;
import hello.core.repository.MemberRepository;
import hello.core.repository.impl.MemberRepositoryImpl;
import hello.core.service.OrderService;

public class OrderServiceImpl implements OrderService {

    private final MemberRepository memberRepository = new MemberRepositoryImpl();
    private final DiscountPolicy discountPolicy = new DiscountPolicyImpl();

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {

        Member member = memberRepository.findById(memberId);
        int discountPrice = discountPolicy.discount(member, itemPrice);

        return new Order(memberId, itemName, itemPrice, discountPrice);
    }
}
