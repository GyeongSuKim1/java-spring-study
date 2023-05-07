package webserver;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.util.Map;

import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.HttpRequestUtils;

public class RequestHandler extends Thread {
    private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);
    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        log.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {

            // TODO 사용자 요청에 대한 처리는 이 곳에 구현하면 된다.
            DataOutputStream dos = new DataOutputStream(out);
            InputStreamReader read = new InputStreamReader(in, "UTF-8");
            BufferedReader br = new BufferedReader(read); // 헤더 값

            String tokens = br.readLine();

            String token = splitUrl(tokens);

            // 헤더가 null이면
            nullCheck(token);

            // url 체크
            String url = urlCheck(token);

//            String ext = token.substring(url.lastIndexOf(".") + 1); // 확장자 추출
//            log.info("ㅡ ext : {}", ext);

            // byte array로 변환 후 body에 넣어줌
            byte[] body = Files.readAllBytes(new File("./webapp" + url).toPath());
//            response200Header(dos, body.length, ext);
            response200Header(dos, body.length);
            responseBody(dos, body);

        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

//    private void response200Header(DataOutputStream dos, int lengthOfBodyContent, String ext) {
//        try {
//            dos.writeBytes("HTTP/1.1 200 OK \r\n");
//            dos.writeBytes("Content-Type: text/" + ext + ";charset=utf-8\r\n");
//            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
//            dos.writeBytes("\r\n");
//        } catch (IOException e) {
//            log.error(e.getMessage());
//        }
//    }

    private void response200Header(DataOutputStream dos, int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void responseBody(DataOutputStream dos, byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private static String splitUrl(String token) {
        // 헤더 첫번째 줄 기준으로 자름
        String[] splits = token.split(" ");

        // url 값
        String tokens = splits[1];

        return tokens;
    }

    // null 값 check
    private static boolean nullCheck(String url) {
        if (url == null) return false;

        return true;
    }

    /**
     * GET 요청 분리
     */

    // ? 분리
    private static int index(String url) {
        return url.indexOf("?");
    }

    // Map 파싱
    private static User user(Map<String, String> params) {
        User user = new User(
                params.get("userId"),
                params.get("password"),
                params.get("name"),
                params.get("email")
        );
        log.info("ㅡ User : {}", user);

        return user;
    }

    // url 체크 후 user 모델 GET 방식으로 회원가입
    private static String urlCheck(String url) {
        if (url.startsWith("/user/create")) {

            int index = index(url);

            // url 파라미터를 꺼내옴
            // Ex) userId=a&password=a&name=a&email=a%40a.a
            String param = url.substring(index + 1);

            // Map 으로 파싱
            // Ex)
            Map<String, String> params = HttpRequestUtils.parseQueryString(param);

            // params key 값을 이용하여 User 안에 넣어줌
            user(params);

            url = "/index.html";
        }
        return url;
    }
}
