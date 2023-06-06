package example.excel.mvc.controller;

import example.excel.mvc.service.ArticleService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@AllArgsConstructor
@Slf4j
public class MainController {

    private ArticleService articleService;

    /**
     * 메인 Page
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView home() {
        ModelAndView mav = new ModelAndView("index");

        mav.addObject("list", articleService.articleList());
        log.info("list : {}", articleService.articleList());


        return mav;
    }

}
