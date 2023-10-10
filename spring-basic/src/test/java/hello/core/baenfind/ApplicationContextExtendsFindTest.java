package hello.core.baenfind;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.impl.FixDiscountPolicyImpl;
import hello.core.discount.impl.RateDiscountPolicyImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ApplicationContextExtendsFindTest {

    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(TestConfig.class);
    
    @Test
    @DisplayName("부모 타입으로 조회시, 자식이 둘 이상 있으면, 중복 오류가 발생")
    public void findBeanByParentTypeDuplicate() {

        // DiscountPolicy bean = ac.getBean(DiscountPolicy.class);
        assertThrows(NoUniqueBeanDefinitionException.class, () ->
                ac.getBean(DiscountPolicy.class));
    }
    
    @Test
    @DisplayName("부모 타입으로 조회시, 자식이 둘 이상 있으면, 빈 이름을 지정하면 됨")
    public void findBeanByParentTypeBeanName() {

        DiscountPolicy rateDiscountPolicy = ac.getBean("rateDiscountPolicy", DiscountPolicy.class);

        assertThat(rateDiscountPolicy).isInstanceOf(RateDiscountPolicyImpl.class);
    }

    @Test
    @DisplayName("특정 하위 타입으로 조회")
    public void findBeanBySubType() {

        RateDiscountPolicyImpl bean = ac.getBean(RateDiscountPolicyImpl.class);

        assertThat(bean).isInstanceOf(RateDiscountPolicyImpl.class);
    }

    @Test
    @DisplayName("부모 타입으로 모두 조회하기")
    public void findAllBeanByParenType() {

        Map<String, DiscountPolicy> beansOfType = ac.getBeansOfType(DiscountPolicy.class);
        assertEquals(beansOfType.size(), 2);

        for (String key : beansOfType.keySet()) {
            System.out.println("key: " + key + "\n" + "value: " + beansOfType.get(key));
        }
    }

    @Test
    @DisplayName("부모 타입으로 모두 조회하기 - Object")
    public void findAllBeanByObjectType() {

        Map<String, Object> beanOfType = ac.getBeansOfType(Object.class);

        for (String key : beanOfType.keySet()) {
            System.out.println("key: " + key + "\n" + "value: " + beanOfType.get(key));
        }
    }

    @Configuration
    static class TestConfig {

        @Bean
        public DiscountPolicy rateDiscountPolicy() {
            return new RateDiscountPolicyImpl();
        }

        @Bean
        public DiscountPolicy fixDiscountPolicy() {
            return new FixDiscountPolicyImpl();
        }
    }
}
