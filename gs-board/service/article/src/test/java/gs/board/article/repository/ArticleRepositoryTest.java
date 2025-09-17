package gs.board.article.repository;

import gs.board.article.entity.Article;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@Slf4j
class ArticleRepositoryTest {
    @Autowired
    ArticleRepository articleRepository;

    @Test
    void findAllByBoardIdTest() {
        final List<Article> articles = articleRepository.findAllByBoardId(1L, 1499970L, 30L);
        log.info("article.size = {}", articles.size());
        for (Article article : articles) {
            log.info("article = {}", article);
        }
    }

    @Test
    void countAllByBoardIdAndLimitTest() {
        final Long count = articleRepository.countAllByBoardIdAndLimit(1L, 10000L);

        log.info("count = {}", count);
    }

    @Test
    void findAllInfiniteScrollTest() {
        final Long boardId = 1L;
        final Long limit = 30L;

        final List<Article> articles = articleRepository.findAllInfiniteScroll(boardId, limit);
        for (Article article : articles) {
            log.info("article = {}", article.getArticleId());
        }

        final Long lastArticleId = articles.getLast().getArticleId();
        final List<Article> articles2 = articleRepository.findAllInfiniteScroll(boardId, limit, lastArticleId);
        for (Article article : articles2) {
            log.info("article2 = {}", article.getArticleId());
        }
    }
}