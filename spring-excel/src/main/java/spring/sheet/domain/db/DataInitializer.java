package spring.sheet.domain.db;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import spring.sheet.domain.entity.ArticleEntity;
import spring.sheet.mvc.repository.ArticleRepository;

@Component
public class DataInitializer implements CommandLineRunner {

    private final ArticleRepository articleRepository;

    public DataInitializer(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        for (int i = 1; i <= 53; i++) {
            ArticleEntity initData = new ArticleEntity();
            initData.setTitle(i + " 제목 입니다.");
            initData.setContent(i + " 설명 입니다.");
            articleRepository.save(initData);
        }
    }
}
