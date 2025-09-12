package gs.board.article.repository;

import gs.board.article.entity.Article;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class ArticleRepositoryImpl implements ArticleRepository{
    private final JpaArticleRepository jpaArticleRepository;

    public ArticleRepositoryImpl(JpaArticleRepository jpaArticleRepository) {
        this.jpaArticleRepository = jpaArticleRepository;
    }

    public Article save(final Article article) {
        return jpaArticleRepository.save(article);
    }

    public Optional<Article> findById(final Long id) {
        return jpaArticleRepository.findById(id);
    }

    public void deleteById(final Long id) {
        jpaArticleRepository.deleteById(id);
    }
}
