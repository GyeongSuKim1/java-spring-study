package gs.board.article.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Table(name = "article")
@Entity
@Getter @Setter @ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Article {
    @Id
    @Column(name = "article_id")
    private Long articleId;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "board_id")
    private Long boardId;

    @Column(name = "writer_id")
    private Long writerId;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "modified_at")
    private LocalDateTime modifiedAt;

    public static Article create(final Long articleId, final String title, final String content, final Long boardId, final Long writerId) {
        final Article article = new Article();

        article.articleId = articleId;
        article.title = title;
        article.content = content;
        article.boardId = boardId;
        article.writerId = writerId;
        article.createdAt = LocalDateTime.now();
        article.modifiedAt = article.createdAt;

        return article;
    }

    public void update(final String title, final String content) {
        this.title = title;
        this.content = content;
        this.modifiedAt = LocalDateTime.now();
    }
}
