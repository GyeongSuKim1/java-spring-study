package com.example.onehoursimpleproject.mvc.controller;

import com.example.onehoursimpleproject.domain.dto.JoinRequest;
import com.example.onehoursimpleproject.mvc.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @RequestMapping(value = "/check", method = RequestMethod.GET)
    public String check() {
        return "Check";
    }

    @RequestMapping(value = "/join", method = RequestMethod.POST)
    public String join(@RequestBody JoinRequest request) {

        String name = request.getName();
        String phone = request.getPhone();

        String result = memberService.join(name, phone);

        if (result.equalsIgnoreCase("success")) {
            return "success";
        } else {
            return "fail";
        }
    }
}
