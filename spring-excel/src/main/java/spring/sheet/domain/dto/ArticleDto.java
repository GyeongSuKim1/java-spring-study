package spring.sheet.domain.dto;

import lombok.Getter;
import lombok.Setter;

public class ArticleDto {

    @Getter @Setter
    static public class add {
        private String title;
        private String content;

        public add(String title, String content) {
            this.title = title;
            this.content = content;
        }
    }
}
