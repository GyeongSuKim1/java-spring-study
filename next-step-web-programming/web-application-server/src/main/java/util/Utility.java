package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Utility {

    public static Boolean nullCheck(String url) {
        if (url == null) return false;

        return true;
    }

    public static String splitUrl(String token) {
        // 헤더 첫번째 줄 기준으로 자름
        String[] splits = token.split(" ");

        // url 값
        String tokens = splits[1];

        return tokens;
    }

    public static Map<String, String> readHeaders(BufferedReader br, String token) throws IOException {
        Map<String, String> headers = new HashMap<String, String>();

        boolean logined = false;
        while (!"".equals(token)) {

            token = br.readLine();
            // 문자열 ": " 기준으로 분리
            String[] headerTokens = token.split(": ");

            // 헤더 바디를 Map 파싱
            if (headerTokens.length == 2) headers.put(headerTokens[0], headerTokens[1]);
        }
//        log.debug("ㅡㅡ Content-Length : {}", headers.get("Content-Length"));
        return headers;
    }
}
