package spring.sheet.domain.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "e_article")
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@Setter
@Getter
@Builder
public class ArticleEntity extends BaseEntity {

    @Column(name = "title", columnDefinition = "VARCHAR(20)")
    @NotBlank(message = "제목을 입력해주세요.") @NotNull
    private String title;

    @Column(name = "content", columnDefinition = "VARCHAR(200)")
    @NotBlank(message = "내용을 입력해주세요.") @NotNull
    private String content;

//    public Article(String title, String content) {
//        this.title = title;
//        this.content = content;
}
