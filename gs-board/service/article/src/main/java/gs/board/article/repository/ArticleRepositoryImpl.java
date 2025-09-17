package gs.board.article.repository;

import gs.board.article.entity.Article;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ArticleRepositoryImpl implements ArticleRepository{
    private final JpaArticleRepository jpaArticleRepository;

    public ArticleRepositoryImpl(JpaArticleRepository jpaArticleRepository) {
        this.jpaArticleRepository = jpaArticleRepository;
    }

    @Override
    public Article save(final Article article) {
        return jpaArticleRepository.save(article);
    }

    @Override
    public Optional<Article> findById(final Long id) {
        return jpaArticleRepository.findById(id);
    }

    @Override
    public void deleteById(final Long id) {
        jpaArticleRepository.deleteById(id);
    }

    @Override
    public List<Article> findAllByBoardId(final Long boardId, final Long offset, final Long limit) {
        return jpaArticleRepository.findAllByBoardId(boardId, offset, limit);
    }

    @Override
    public Long countAllByBoardIdAndLimit(final Long boardId, final Long limit) {
        return jpaArticleRepository.countAllByBoardIdAndLimit(boardId, limit);
    }

    @Override
    public List<Article> findAllInfiniteScroll(final Long boardId, final Long limit) {
        return jpaArticleRepository.findAllInfiniteScroll(boardId, limit);
    }

    @Override
    public List<Article> findAllInfiniteScroll(final Long boardId, final Long limit, final Long lastArticleId) {
        return jpaArticleRepository.findAllInfiniteScroll(boardId, limit, lastArticleId);
    }
}
