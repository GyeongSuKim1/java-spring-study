package gs.board.article.repository;

import gs.board.article.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaArticleRepository extends JpaRepository<Article, Long> {
}
