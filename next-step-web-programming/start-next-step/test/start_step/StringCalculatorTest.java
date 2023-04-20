package start_step;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class StringCalculatorTest {

    private StringCalculator cal;

    @Before
    public void setup() {
        cal = new StringCalculator();
    }

    //    @Test(expected = NullPointerException.class)
    @Test
    public void add_null() {
//        assertEquals(0, cal.add(null));
        Assert.assertEquals(0, cal.add(null));
        System.out.println(cal.add(null));
    }

    @Test
    public void add_빈값() {
        assertEquals(0, cal.add(""));
        System.out.println(cal.add(""));
    }

    @Test
    public void add_한글자() {
        assertEquals(1, cal.add("1"));
        System.out.println(cal.add("1"));
    }

    @Test
    public void add_쉼표(){
        assertEquals(6, cal.add("1,2,3"));
        System.out.println(cal.add("1,2,3"));
    }

    @Test
    public void add_콜론(){
        assertEquals(6, cal.add("1:2:3"));
        System.out.println(cal.add("1:2:3"));
    }

    @Test
    public void add_쉼표콜론(){
        assertEquals(12, cal.add("1,2,3:1:2:3"));
        System.out.println(cal.add("1,2,3:1:2:3"));
    }

    @Test
    public void add_정규화() {
        assertEquals(6, cal.add("//;\n1;2;3"));
        System.out.println(cal.add("//;\n1;2;3"));
    }

    @Test
    public void add_정규화_커스텀() {
        assertEquals(6, cal.add("//;\n1;2;3"));
        System.out.println(cal.add("//;\n1;2;3"));
    }

    //    @Test
    @Test(expected = RuntimeException.class)
    public void add_RuntimeException() {
        System.out.println(cal.add("-1,5:2"));
    }

    @Test
    public void add_Runtime_확인() {
        try {
            cal.add("-6,3");
        } catch (RuntimeException e) {
            assertEquals("음수는 입력할 수 없습니다.", e.getMessage());
            System.out.println("RuntimeException : " + e.getMessage());
        }
    }

}