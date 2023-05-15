package webserver;

import java.io.*;
import java.net.Socket;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import db.DataBase;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.HttpRequestUtils;
import util.HttpResponseUtil;
import util.IOUtils;

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
            String url = splitUrl(token);   // reuqest url 추출
            String ext = url.substring(url.lastIndexOf(".") + 1); // 확장자 추출

            Map<String, String> headers = readHeaders(br, token);

            nullCheck(token); // 헤더가 null일 경우 응답x

            if (url.startsWith("/user/create")) {
                createUser(dos, br, headers);

            } else if (url.equals("/user/login")) {
                loginUser(dos, br, headers);

            } else if (url.startsWith("/user/list")) {
                userList(dos, br, headers, ext, url);

            } else {
                // byte array로 변환 후 body에 넣어줌
                byte[] body = Files.readAllBytes(new File("./webapp" + url).toPath());
                HttpResponseUtil.response200Header(dos, body.length, ext);
                HttpResponseUtil.responseBody(dos, body);
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private static Boolean nullCheck(String url) {
        if (url == null) return false;

        return true;
    }

    private static String splitUrl(String token) {
        // 헤더 첫번째 줄 기준으로 자름
        String[] splits = token.split(" ");

        // url 값
        String tokens = splits[1];

        return tokens;
    }

    private Map<String, String> readHeaders(BufferedReader br, String token) throws IOException {
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

    /**
     * 회원가입
     */
    private void createUser(DataOutputStream dos, BufferedReader br, Map<String, String> headers) throws IOException {

        String bodyData = IOUtils.readData(br, Integer.parseInt(headers.get("Content-Length")));
//                log.debug("Create Request Content-Length : {}", bodyData);

        // Map으로 파싱해줌
        Map<String, String> params = HttpRequestUtils.parseQueryString(bodyData);

        // params의 key값을 이용하여 User안에 넣어줌
        User user = new User(
                params.get("userId"),
                params.get("password"),
                params.get("name"),
                params.get("email")
        );
        log.info("ㅡ create User : {}", user);
        DataBase.addUser(user);
        HttpResponseUtil.response302Header(dos);
    }

    /**
     * 로그인
     */
    private void loginUser(DataOutputStream dos, BufferedReader br, Map<String, String> headers) throws IOException {

        // 바디를 읽을 수 있게 문자열로 변환
        String bodyData = IOUtils.readData(br, Integer.parseInt(headers.get("Content-Length")));
//                log.debug("Login Request Content-Length : {}", bodyData);

        Map<String, String> params = HttpRequestUtils.parseQueryString(bodyData);
        log.info("ㅡ userId : {}, password : {}", params.get("userId"), params.get("password"));

        // 유저 아이디
        User user = DataBase.findUserById(params.get("userId"));
        String cookie = "";
        String redirectUrl = "";

        // 로그인 실패
        if (user == null || !user.getPassword().equals(params.get("password"))) {

            log.debug("not Found");
            HttpResponseUtil.response302LoginCookie(dos, "/user/login_failed.html", "logined=false");
            cookie = "logined=false";
            redirectUrl = "/user/login_failed.html";

            // 로그인 성공
        } else if (user.getPassword().equals(params.get("password"))) {

            log.debug("sucess");
            cookie = "logined=true";
            redirectUrl = "/index.html";
//                    response302LoginCookie(dos, "index.html", "logined=true");
        }
        HttpResponseUtil.response302LoginCookie(dos, redirectUrl, cookie);
    }

    /**
     * 사용자 리스트
     */
    private void userList(DataOutputStream dos, BufferedReader br, Map<String, String> headers, String ext, String url) throws IOException {

        Map<String, String> cookies = HttpRequestUtils.parseCookies(headers.get("Cookie"));

        // logined
        if (cookies.get("logined") == null || !Boolean.parseBoolean(cookies.get("logined"))) {

            HttpResponseUtil.response302Header(dos);
        } else {

            int idx = 3;

            Collection<User> userList = DataBase.findAll();
            StringBuilder sb = new StringBuilder();

            for (User user : userList) {
                sb.append("<tr>" +
                        "<th scope=\"row\">" + idx + "</th>" +
                        "<td>" + user.getUserId() + "</td> " +
                        "<td>" + user.getName() + "</td> " +
                        "<td>" + user.getEmail() + "</td>" +
                        "<td><a href=\"#\" class=\"btn btn-success\" role=\"button\">수정</a></td>" +
                        "</tr>");
                idx++;
            }

            String fileData = new String(Files.readAllBytes(new File("./webapp" + url).toPath()));
            fileData = fileData.replace("%user_list%", URLDecoder.decode(sb.toString(), "UTF-8"));

            byte[] body = fileData.getBytes();
            HttpResponseUtil.response200Header(dos, body.length, ext);
            HttpResponseUtil.responseBody(dos, body);

        }

    }
}