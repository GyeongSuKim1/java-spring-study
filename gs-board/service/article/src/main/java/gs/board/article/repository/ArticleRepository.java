package gs.board.article.repository;

import gs.board.article.entity.Article;

import java.util.Optional;

public interface ArticleRepository {
    Article save(final Article article);

    Optional<Article> findById(final Long id);

    void deleteById(final Long id);
}
