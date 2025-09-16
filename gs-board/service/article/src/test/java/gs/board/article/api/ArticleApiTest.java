package gs.board.article.api;

import gs.board.article.service.response.ArticlePageResponse;
import gs.board.article.service.response.ArticleResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestClient;

public class ArticleApiTest {
    RestClient restClient = RestClient.create("http://localhost:9000");

    @Test
    void createTest() {
        final ArticleResponse response = create(
                new ArticleCreateRequest("TEST Title", "TEST Content", 1L, 1L));
        System.out.println("response = " + response);
    }

    @Test
    void readTest() {
        final ArticleResponse response = read(224806577060999168L);
        System.out.println("response = " + response);
    }

    @Test
    void updateTest() {
        final ArticleResponse response = update(224426311831617536L, new ArticleUpdateRequest("Change Title2", "Change Content2"));
        System.out.println("response = " + response);
    }

    @Test
    void deleteTest() {
        delete(224790700499861504L);
    }

    @Test
    void readAllTest() {
        final ArticlePageCondition condition = new ArticlePageCondition(1L, 50000L, 30L);
        final ArticlePageResponse response = readAll(condition);
        System.out.println("response.getArticleCount() = " + response.getArticleCount());

        System.out.println();
        for (ArticleResponse article : response.getArticles()) {
            System.out.println("article.getArticleId() = " + article.getArticleId());
        }
    }

    ArticleResponse create(final ArticleCreateRequest request) {
        return restClient.post()
                .uri("/v1/article")
                .body(request)
                .retrieve()
                .body(ArticleResponse.class);
    }

    ArticleResponse read(final Long articleId) {
        return restClient.get()
                .uri("/v1/article/{articleId}", articleId)
                .retrieve()
                .body(ArticleResponse.class);
    }

    ArticleResponse update(final Long articleId, final ArticleUpdateRequest request) {
        return restClient.put()
                .uri("/v1/article/{articleId}", articleId)
                .body(request)
                .retrieve()
                .body(ArticleResponse.class);
    }

    void delete(final Long articleId) {
        restClient.delete()
                .uri("/v1/article/{articleId}", articleId)
                .retrieve();
    }

    ArticlePageResponse readAll(final ArticlePageCondition condition) {
        return restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/v1/article")
                        .queryParam("boardId", condition.getBoardId())
                        .queryParam("page", condition.getPage())
                        .queryParam("pageSize", condition.getPageSize())
                        .build()
                ).retrieve()
                .body(ArticlePageResponse.class);
    }

    @Getter @AllArgsConstructor
    static class ArticleCreateRequest {
        private String title;
        private String content;
        private Long writerId;
        private Long boardId;
    }

    @Getter @AllArgsConstructor
    static class ArticleUpdateRequest {
        private String title;
        private String content;
    }

    @Getter @AllArgsConstructor
    public class ArticlePageCondition {
        private Long boardId;
        private Long page;
        private Long pageSize;
    }
}
