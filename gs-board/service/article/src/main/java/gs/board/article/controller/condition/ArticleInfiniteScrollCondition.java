package gs.board.article.controller.condition;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ArticleInfiniteScrollCondition {
    private Long boardId;
    private Long pageSize;
    private Long lastArticleId;

    public ArticleInfiniteScrollCondition(Long boardId, Long pageSize, Long lastArticleId) {
        this.boardId = boardId;
        this.pageSize = pageSize;
        this.lastArticleId = lastArticleId;
    }
}
