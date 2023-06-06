package example.excel.mvc.service.impl;

import example.excel.domain.entity.Article;
import example.excel.mvc.repository.ArticleRepository;
import example.excel.mvc.service.ArticleService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;

    public ArticleServiceImpl(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @Override
    public void articleWrite(Article request) {
        articleRepository.save(request);
    }

    @Override
    public List<Article> articleList() {
        return articleRepository.findAll();
    }

    @Override
    public Article articleView(Long idx) {
        return articleRepository.findById(idx).get();
    }

    @Override
    public void articleDelete(Long idx) {
        articleRepository.deleteById(idx);
    }
}
