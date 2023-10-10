package hello.core.xml;

import hello.core.service.MemberService;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import static org.assertj.core.api.Assertions.*;

/**
 * 작동x 나중에 수정 예정
 */
public class XmlAppContext {

    @Test
    void xmlAppContext() {
        ApplicationContext ac = new
                GenericXmlApplicationContext("appConfig.xml");

        MemberService memberService = ac.getBean("memberService", MemberService.class);
        assertThat(memberService).isInstanceOf(MemberService.class);
    }
}
