package start_step;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 리팩토링
 */
public class StringCalculator_Refactoring {

    public int add(String text) {
        if(isBlank(text)) {
            return 0;
        }
        return sum(toInts(split(text)));
    }

    // null 또는 빈문자
    private boolean isBlank(String text) {
        return text == null || text.isEmpty();
    }

    // 쉼표, 콜론 분리
    private String[] split(String text) {
        // 정규 표현식을 이용하여 문자열을 분리
        Matcher m = Pattern.compile("//(.)\n(.*)").matcher(text);
        if(m.find()) {
            String customDelimeter = m.group(1);
            return m.group(2).split(customDelimeter);
        }

        return text.split(",|:");
    }

    /**
     * toInts와 sum을 분리하여 두 가지 작업이 아닌 한 가지 작업을 수행하도록 한다.
     * 이 리팩토링의 포인트는 private로 분리한 메소드가 아니라
     * public으로 공개하고 있는 add()메소드가 얼마나 읽기 쉬운지 이다.
     */
    // String => int 로 형변환
    private int[] toInts(String[] values) {
        int[] numbers = new int[values.length];
        for (int i=0; i< values.length; i++) {
            numbers[i] = toPositive(values[i]);
        }
        return numbers;
    }

    // 연산
    private int sum(int[] numbers) {
        int sum = 0;
        for(int number : numbers) {
            sum += number;
        }
        return sum;
    }

    // RuntimeException 처리
    private int toPositive(String value) {
        int number = Integer.parseInt(value);
        if(number < 0) {
            throw new RuntimeException();
        }
        return number;
    }
}