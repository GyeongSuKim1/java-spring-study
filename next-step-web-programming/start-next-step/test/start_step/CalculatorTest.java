package start_step;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Junit 테스팅 연습
 */
import static org.junit.Assert.*;

public class CalculatorTest {

    private Calculator cal;

    /**
     * 각 단위 테스트를 실행할때 마다 setup이 생성됨
     */
    @Before
    public void setup() {
        cal = new Calculator();
        System.out.println("Before");
    }

    @Test
    public void add() {

        assertEquals(9, cal.add(6, 3));
        System.out.println("add : " + cal.add(6, 3));
    }

    @Test
    public void subtract() {

        assertEquals(3, cal.subtract(6, 3));
        System.out.println("subtract : " + cal.subtract(6, 3));
    }

    @After
    public void teardown() {
        System.out.println("teardown");
        System.out.println();
    }

}