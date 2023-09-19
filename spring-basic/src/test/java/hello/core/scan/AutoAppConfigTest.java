package hello.core.scan;

import hello.core.AutoAppConfig;
import hello.core.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.assertj.core.api.Assertions.*;

public class AutoAppConfigTest {

@Test
@DisplayName("기본 스캔")
public void basicScan() {

    ApplicationContext ac = new AnnotationConfigApplicationContext(AutoAppConfig.class);
    MemberService memberService = ac.getBean(MemberService.class);

    assertThat(memberService).isInstanceOf(MemberService.class);
}

}
