package gs.board.article.repository;

import gs.board.article.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface JpaArticleRepository extends JpaRepository<Article, Long> {
    @Query(
            value = """
                    select article.article_id, article.title, article.content, article.board_id, article.writer_id, article.created_at, article.modified_at 
                    from ( 
                                           select article_id from article
                                           where board_id = :boardId  
                                           order by article_id desc 
                                           limit :limit offset :offset  
                                    ) t left join article on t.article_id = article.article_id
                                    """,
            nativeQuery = true)
    List<Article> findAllByBoardId(@Param("boardId") final Long boardId, @Param("offset") final Long offset, @Param("limit") final Long limit);

    @Query(
            value = """
                    select count(*) 
                    from (
                        select article_id from article
                                          where board_id = :boardId
                                          limit :limit
                    ) t
                    """,
            nativeQuery = true)
    Long countAllByBoardIdAndLimit(@Param("boardId") final Long boardId, @Param("limit") final Long limit);

    @Query(
            value = """
                    select article.article_id, article.title, article.content, article.board_id, article.writer_id, article.created_at, article.modified_at
                    from article 
                    where board_id = :boardId 
                    order by article_id desc 
                    limit :limit
                    """,
            nativeQuery = true
    )
    List<Article> findAllInfiniteScroll(@Param("boardId") final Long boardId, @Param("limit") final Long limit);

    @Query(
            value = """
                    select article.article_id, article.title, article.content, article.board_id, article.writer_id, article.created_at, article.modified_at 
                    from article 
                    where board_id = :boardId 
                      and article_id < :lastArticleId 
                    order by article_id desc 
                    limit :limit
                    """,
            nativeQuery = true
    )
    List<Article> findAllInfiniteScroll(@Param("boardId") final Long boardId, @Param("limit") final Long limit, @Param("lastArticleId") final Long lastArticleId);
}
