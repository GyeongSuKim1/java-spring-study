package util;

import db.DataBase;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.RequestHandler;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.util.Collection;
import java.util.Map;

public class CRUDUtils {

    private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);

    /**
     * 회원가입
     */
    public static void createUser(DataOutputStream dos, BufferedReader br, Map<String, String> headers) throws IOException {

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
        HttpResponseUtils.response302Header(dos);
    }

    /**
     * 로그인
     */
    public static void loginUser(DataOutputStream dos, BufferedReader br, Map<String, String> headers) throws IOException {

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
            HttpResponseUtils.response302LoginCookie(dos, "/user/login_failed.html", "logined=false");
            cookie = "logined=false";
            redirectUrl = "/user/login_failed.html";

            // 로그인 성공
        } else if (user.getPassword().equals(params.get("password"))) {

            log.debug("sucess");
            cookie = "logined=true";
            redirectUrl = "/index.html";
//                    response302LoginCookie(dos, "index.html", "logined=true");
        }
        HttpResponseUtils.response302LoginCookie(dos, redirectUrl, cookie);
    }

    /**
     * 사용자 리스트
     */
    public static void userList(DataOutputStream dos, BufferedReader br, Map<String, String> headers, String ext, String url) throws IOException {

        Map<String, String> cookies = HttpRequestUtils.parseCookies(headers.get("Cookie"));

        // logined
        if (cookies.get("logined") == null || !Boolean.parseBoolean(cookies.get("logined"))) {

            HttpResponseUtils.response302Header(dos);
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
            HttpResponseUtils.response200Header(dos, body.length, ext);
            HttpResponseUtils.responseBody(dos, body);

        }
    }
}
