package example.excel.mvc.service;

import example.excel.domain.dto.ArticleRequest;
import example.excel.domain.entity.Article;

import java.util.List;

public interface ArticleService {

    public void articleWrite(Article request);

    public List<Article> articleList();

    public Article articleView(Long idx);

    public void articleDelete(Long idx);

}
