package spring.sheet;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import spring.sheet.domain.entity.ArticleEntity;
import spring.sheet.mvc.repository.ArticleRepository;

import static org.assertj.core.api.Assertions.assertThat;

//@DataJpaTest
@SpringBootTest
public class ArticleRepositoryTest {

    @Autowired
    private ArticleRepository articleRepository;

    @Test
    @DisplayName("게시글 저장")
    void save() {
        // given
        ArticleEntity article = new ArticleEntity();
        article.setTitle("Test_제목");
        article.setContent("Test_내용");

        // when
        articleRepository.save(article);

        // then
        ArticleEntity result = articleRepository.findById(article.getIdx()).get();

        assertThat(result.getIdx())
                .isEqualTo(article.getIdx());
    }

    @DisplayName("JPA 게시글 저장")
    @Test
    void jpaSave() {
        // given
        ArticleEntity article = new ArticleEntity();
        article.setTitle("Test_제목_V2");
        article.setContent("Test_내용_V2");

        // when
        ArticleEntity savedArticle = articleRepository.save(article);

        // then
        org.junit.jupiter.api.Assertions.assertNotNull(savedArticle);
        org.junit.jupiter.api.Assertions.assertTrue(article.getIdx().equals(savedArticle.getIdx()));
        org.junit.jupiter.api.Assertions.assertTrue(article.getTitle().equals(savedArticle.getTitle()));
        org.junit.jupiter.api.Assertions.assertTrue(article.getContent().equals(savedArticle.getContent()));
        org.junit.jupiter.api.Assertions.assertTrue(article.getCreated_at().equals(savedArticle.getCreated_at()));
    }

}
