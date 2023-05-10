package com.example.onehoursimpleproject.mvc.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MemberController {

    @RequestMapping(value = "/check", method = RequestMethod.GET)
    public String check() {
        return "Check";
    }

    @RequestMapping(value = "/join", method = RequestMethod.POST)
    public String join() {
        return "string";
    }
}
