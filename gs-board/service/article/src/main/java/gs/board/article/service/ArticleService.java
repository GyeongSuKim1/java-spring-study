package gs.board.article.service;

import gs.board.article.controller.condition.ArticlePageCondition;
import gs.board.article.entity.Article;
import gs.board.article.repository.ArticleRepository;
import gs.board.article.service.request.ArticleCreateRequest;
import gs.board.article.service.request.ArticleUpdateRequest;
import gs.board.article.service.response.ArticlePageResponse;
import gs.board.article.service.response.ArticleResponse;
import kuke.board.common.snowflake.Snowflake;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class ArticleService {
    private final Snowflake snowflake = new Snowflake();
    private final ArticleRepository articleRepository;

    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }


    @Transactional
    public ArticleResponse create(final ArticleCreateRequest request) {
        final Article article = articleRepository.save(
                Article.create(snowflake.nextId(), request.getTitle(), request.getContent(), request.getWriterId(), request.getBoardId())
        );

        return ArticleResponse.from(article);
    }

    @Transactional
    public ArticleResponse update(final Long articleId, final ArticleUpdateRequest request) {
        final Article article = articleRepository.findById(articleId)
                .orElseThrow();
        article.update(request.getTitle(), request.getContent());

        return ArticleResponse.from(article);
    }

    @Transactional(readOnly = true)
    public ArticleResponse read(final Long articleId) {
        return ArticleResponse.from(articleRepository.findById(articleId).orElseThrow());
    }

    @Transactional
    public void delete(final Long articleId) {
        articleRepository.deleteById(articleId);
    }

    @Transactional(readOnly = true)
    public ArticlePageResponse readAll(final ArticlePageCondition condition) {
        return ArticlePageResponse.of(
                articleRepository.findAllByBoardId(
                                condition.getBoardId(),
                                (condition.getPage() - 1) * condition.getPageSize(),
                                condition.getPageSize()).stream()
                        .map(ArticleResponse::from)
                        .toList(),
                articleRepository.countAllByBoardIdAndLimit(condition.getBoardId(),
                        PageLimitCalculator.calculatePageLimit(condition.getPage(), condition.getPageSize(), 10L))
        );
    }
}
