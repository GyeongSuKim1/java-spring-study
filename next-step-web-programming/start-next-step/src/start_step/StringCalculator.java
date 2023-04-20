package start_step;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 메소드가 한 가지 책임만 가지도록 구현
 * 인덴트(indent, 들여쓰기) 깊이를 1단계로 유지
 * else사용 자제
 */
public class StringCalculator {

    public int add(String text) {

//        try {
//            numBlank(text);
//            return 0;
//        } catch (RuntimeException e) {
//            System.out.println(e.getMessage());
//        }
//        return result(text);

        if (numBlank(text)) return 0;

        return result(text);
    }

    /**
     * null or empty
     */
    private boolean numBlank(String text) {

        return text == null || text.isEmpty();
//        try {
//        return text.isEmpty() || text == null;
//        } catch (NullPointerException e) {
//            throw new NullPointerException("null을 입력할 수 없습니다.");
//        }
    }

    /**
     * 플러스 연산
     */
    private int result(String text) {

        String[] nums = splitText(text);
        int sum = 0;

        for (String num : nums) {
            sum += checkNegative(Integer.parseInt(num));
//            sum += Integer.parseInt(num);

        }
        return sum;
    }

    /**
     * 문자열 분리
     */
    private String[] splitText(String text) {

        Matcher m = Pattern.compile("//(.)\n(.*)").matcher(text);
        if (m.find()) {
            String customDelimeter = m.group(1);
            return m.group(2).split(customDelimeter);
        }
        return text.split("[,|:]");
    }

    /**
     * RuntimeException 처리
     */
    private int checkNegative(int text) {

//        int num = Integer.parseInt(text);
        if (text < 0) throw new RuntimeException("음수는 입력할 수 없습니다.");

        return text;
    }
}