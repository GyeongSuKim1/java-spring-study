package spring.sheet.domain.entity;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@MappedSuperclass
@Getter
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long idx;

    @Column(name = "create_at", updatable = false, nullable = false)
    @CreatedDate
    private LocalDateTime created_at;

    @Column(name = "update_at", updatable = false, nullable = false)
    @LastModifiedDate
    private LocalDateTime update_at;
}
