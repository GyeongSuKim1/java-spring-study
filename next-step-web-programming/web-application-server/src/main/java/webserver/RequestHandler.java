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

            String token = br.readLine();
            if (token == null) return; // 헤더가 null일 경우 응답x

            String url = splitUrl(token);
            log.info("ㅡ url : {}", url);

            // user/create로 시작하면 조건문 실행
            if (url.startsWith("/user/create")) {

                // index 12
                int index = url.indexOf("?");
//                log.info("ㅡ index : {}", index);

                // url뒤 파라미터를 꺼내옴
                // Ex) userId=a&password=a&name=a&email=a%40a.a
                String param = url.substring(index + 1);
//                log.info("ㅡ param : {}", param);

                // Map으로 파싱해줌
                Map<String, String> params = HttpRequestUtils.parseQueryString(param);
//                log.info("ㅡ params : {}", params);

                // params의 key값을 이용하여 User안에 넣어줌
                User user = new User(
                        params.get("userId"),
                        params.get("password"),
                        params.get("name"),
                        params.get("email")
                );
                log.info("ㅡ User : {}", user);

                // 회원가입을 하였으니 index.html로 return
                url = "/index.html";
            }


            String ext = url.substring(url.lastIndexOf(".") + 1); // 확장자 추출
            log.info("ㅡ ext : {}", ext);

            // byte array로 변환 후 body에 넣어줌
            byte[] body = Files.readAllBytes(new File("./webapp" + url).toPath());
            response200Header(dos, body.length, ext);
            responseBody(dos, body);

        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void response200Header(DataOutputStream dos, int lengthOfBodyContent, String ext) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: text/" + ext + ";charset=utf-8\r\n");
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

//        log.debug("\n0 : {}\n1 : {}\n2 : {}\n", splits[0], splits[1], splits[2]);
//        log.debug("ㅡ token {}", tokens);

        return tokens;
    }
}
