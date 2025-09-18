package gs.board.article.service.response;

import gs.board.article.entity.Article;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
public class ArticleResponse {
    private Long articleId;
    private String title;
    private String content;
    private Long writerId;
    private Long boardId;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public static ArticleResponse from(final Article article) {
        final ArticleResponse response = new ArticleResponse();

        response.articleId = article.getArticleId();
        response.title = article.getTitle();
        response.content = article.getContent();
        response.writerId = article.getWriterId();
        response.boardId = article.getBoardId();
        response.createdAt = article.getCreatedAt();
        response.modifiedAt = article.getModifiedAt();

        return response;
    }
}
