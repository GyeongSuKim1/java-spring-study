package gs.board.article.repository;

import gs.board.article.entity.Article;

import java.util.List;
import java.util.Optional;

public interface ArticleRepository {
    Article save(final Article article);

    Optional<Article> findById(final Long id);

    void deleteById(final Long id);

    List<Article> findAllByBoardId(final Long boardId, final Long offset, final Long limit);

    Long countAllByBoardIdAndLimit(final Long boardId, final Long limit);

    List<Article> findAllInfiniteScroll(final Long boardId, final Long limit);

    List<Article> findAllInfiniteScroll(final Long boardId,final Long limit, final Long lastArticleId);
}
