package spring.sheet.mvc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import spring.sheet.domain.entity.ArticleEntity;

import java.util.List;

@Repository
public interface ArticleRepository extends JpaRepository<ArticleEntity, Long> {

    @Query("SELECT a FROM ArticleEntity a WHERE a.title LIKE %:keyword% OR a.content LIKE %:keyword%")
    List<ArticleEntity> findByTitleOrContentContaining(@Param("keyword") String param);
}
