package example.excel.mvc.controller;

import example.excel.domain.entity.Article;
import example.excel.mvc.service.ArticleService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/article")
@AllArgsConstructor
@Slf4j
public class ArticleController {

    private final ArticleService articleService;

    /**
     * 게시글 작성 Page
     */
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public ModelAndView addView() {
        ModelAndView mav = new ModelAndView("add");

        return mav;
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model mav) {

        mav.addAttribute("list", articleService.articleList());
        log.info("list : {}", articleService.articleList());

        return "index";

    }

    /**
     * 게시글 작성
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ModelAndView add(@ModelAttribute Article param) {

        try {
            articleService.articleWrite(param);
        } catch (Exception e) {
            log.debug("", e);
            return new ModelAndView("redirect:/");
        }

        log.info("ㅡㅡㅡㅡ");
        log.info("idx : " + param.getIdx());
        log.info("title : " + param.getTitle());
        log.info("content : " + param.getContent());
        log.info("create : " + param.getCreated_at());
        log.info("ㅡㅡㅡㅡ");

        return new ModelAndView("redirect:/");
    }

}
