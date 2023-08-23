package spring.sheet.mvc.service;


import spring.sheet.domain.entity.ArticleEntity;

import java.util.List;

public interface ArticleService {

    public void articleWrite(ArticleEntity request);

    public List<ArticleEntity> articleList();

    public ArticleEntity articleView(Long idx);

    public void articleDelete(Long idx);

}
