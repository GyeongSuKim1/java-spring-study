package spring.sheet.mvc.service.impl;

import org.springframework.stereotype.Service;
import spring.sheet.domain.entity.ArticleEntity;
import spring.sheet.mvc.repository.ArticleRepository;
import spring.sheet.mvc.service.ArticleService;

import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;

    public ArticleServiceImpl(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @Override
    public void articleWrite(ArticleEntity request) {
        articleRepository.save(request);
    }

    @Override
    public List<ArticleEntity> articleList() {
        return articleRepository.findAll();
    }

    @Override
    public ArticleEntity articleView(Long idx) {
        return articleRepository.findById(idx).get();
    }

    @Override
    public void articleDelete(Long idx) {
        articleRepository.deleteById(idx);
    }
}
