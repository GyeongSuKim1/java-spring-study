package spring.sheet.mvc.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import spring.sheet.domain.entity.ArticleEntity;
import spring.sheet.mvc.service.ArticleService;

@Controller
@Slf4j
public class ArticleController {

    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    /**
     * 메인 Page
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView home() {
        ModelAndView mav = new ModelAndView("index");

        mav.addObject("list", articleService.articleList());

        return mav;
    }

    /**
     * 메인 PageV2
     */
//    @RequestMapping(value = "/", method = RequestMethod.GET)
//    public ModelAndView homeV2(String keyword) {
//        ModelAndView mav = new ModelAndView("index");
//
//        if (keyword == null || keyword.isEmpty()) mav.addObject("list", articleService.articleList());
//        else mav.addObject("list", articleService.serchPosts(keyword));
//
//        return mav;
//    }

    /**
     * 게시글 작성 Page
     */
    @RequestMapping(value = "add", method = RequestMethod.GET)
    public ModelAndView addView() {
        ModelAndView mav = new ModelAndView("add");

        return mav;
    }

    /**
     * 게시글 작성
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ModelAndView add(@ModelAttribute ArticleEntity param) {

        try {
            articleService.articleWrite(param);
        } catch (Exception e) {
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
