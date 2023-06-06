package example.excel.db;

import example.excel.domain.entity.Article;
import example.excel.mvc.repository.ArticleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final ArticleRepository articleRepository;

    public DataInitializer(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        for (int i = 1; i <= 53; i++) {
            Article initData = new Article();
            initData.setTitle(i + " 제목 입니다.");
            initData.setContent(i + " 설명 입니다.");
            articleRepository.save(initData);
        }
    }
}
