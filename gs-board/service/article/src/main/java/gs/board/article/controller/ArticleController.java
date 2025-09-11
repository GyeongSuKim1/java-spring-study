package gs.board.article.controller;

import gs.board.article.service.ArticleService;
import gs.board.article.service.request.ArticleCreateRequest;
import gs.board.article.service.request.ArticleUpdateRequest;
import gs.board.article.service.response.ArticleResponse;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ArticleController {
    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("/v1/article/{articleId}")
    public ArticleResponse read(@PathVariable(name = "articleId") final Long articleId) {
        return articleService.read(articleId);
    }

    @PostMapping("/v1/article")
    public ArticleResponse create(@RequestBody final ArticleCreateRequest request) {
        return articleService.create(request);
    }

    @PutMapping("/v1/article/{articleId}")
    public ArticleResponse update(@PathVariable final Long articleId,
                                  @RequestBody final ArticleUpdateRequest request) {
        return articleService.update(articleId, request);
    }

    @DeleteMapping("/v1/article/{articleId}")
    public ArticleResponse delete(@PathVariable final Long articleId) {
        return delete(articleId);
    }
}
