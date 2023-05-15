package webserver;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.*;

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
            DataOutputStream dos = new DataOutputStream(out);
            InputStreamReader read = new InputStreamReader(in, "UTF-8");
            BufferedReader br = new BufferedReader(read); // 헤더 값

            String token = br.readLine();
            String url = Utility.splitUrl(token);   // reuqest url 추출
            String ext = url.substring(url.lastIndexOf(".") + 1); // 확장자 추출

            Map<String, String> headers = Utility.readHeaders(br, token);

            Utility.nullCheck(token); // 헤더가 null일 경우 응답x

            if (url.startsWith("/user/create")) {
                CRUDUtils.createUser(dos, br, headers);

            } else if (url.equals("/user/login")) {
                CRUDUtils.loginUser(dos, br, headers);

            } else if (url.startsWith("/user/list")) {
                CRUDUtils.userList(dos, br, headers, ext, url);

            } else {
                // byte array로 변환 후 body에 넣어줌
                byte[] body = Files.readAllBytes(new File("./webapp" + url).toPath());
                HttpResponseUtils.response200Header(dos, body.length, ext);
                HttpResponseUtils.responseBody(dos, body);
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
}