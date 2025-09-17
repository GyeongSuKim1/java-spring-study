package gs.board.article.controller.condition;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ArticlePageCondition {
    private Long boardId;
    private Long page;
    private Long pageSize;

    public ArticlePageCondition(Long boardId, Long page, Long pageSize) {
        this.boardId = boardId;
        this.page = page;
        this.pageSize = pageSize;
    }
}
