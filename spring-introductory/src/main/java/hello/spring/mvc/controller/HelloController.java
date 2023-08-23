package hello.spring.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String hello(Model model) {

        model.addAttribute("data", "hello~!");
        return "hello";
    }

    /**
     * MVC 템플릿 엔진
     */
    @RequestMapping(value = "/hello-mvc", method = RequestMethod.GET)
    public String helloMvc(@RequestParam(value = "name") String name, Model model) {

        model.addAttribute("name", name);

        return "hello-template";
    }

    /**
     * @ResponseBody 문자 반환
     */
    @RequestMapping(value = "/hello-string", method = RequestMethod.GET)
    @ResponseBody
    public String helloString(@RequestParam("name") String name) {
        return "hello [ " + name + " ]";
    }

    /**
     * @ResponseBody 객체 반환
     */
    @RequestMapping(value = "/hello-api", method = RequestMethod.GET)
    @ResponseBody
    public Hello helloApi(@RequestParam("name") String name) {
        Hello hello = new Hello();
        hello.setName(name);

        return hello;
    }


    static class Hello {
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
