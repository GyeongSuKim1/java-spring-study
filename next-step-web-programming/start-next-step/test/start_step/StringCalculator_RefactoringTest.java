package start_step;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class StringCalculator_RefactoringTest {

    private StringCalculator_Refactoring cal;

    @Before
    public void setup() {
        cal = new StringCalculator_Refactoring();
    }

    @Test
    public void add_null_또는_빈문자() {
        assertEquals(0, cal.add(null));
        assertEquals(0, cal.add(""));
    }

    @Test
    public void add_숫자하나() {
        assertEquals(1, cal.add("1"));
    }

    @Test
    public void add_쉼표구분자() {
        assertEquals(3, cal.add("1,2"));
    }

    @Test
    public void add_쉼표_또는_콜론_구분자() {
        assertEquals(6, cal.add("1,2:3"));
    }

    @Test
    public void add_custom_구분자() {
//        assertEquals(6, cal.add("//;\n1;2;3"));
        Assert.assertEquals(6, cal.add("//;\n1;2;3"));
    }

    // expected 속성에 기대하는 Exception클래스를 전달해 줌
    @Test(expected = RuntimeException.class)
    public void add_negative() {
        cal.add("-1,2,3");
    }

}