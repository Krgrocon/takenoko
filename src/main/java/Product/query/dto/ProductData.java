package Product.query.dto;

import Member.query.dto.MemberData;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "product")
public class ProductData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private MemberData user_id;


    @Column(nullable = false)
    private String title;

    @Lob
    private String description;

    @Column(nullable = false)
    private Integer price;

    private String category;

    @Column(nullable = false, columnDefinition = "VARCHAR(20) DEFAULT '판매중'")
    private String status;

    @Column(nullable = false, columnDefinition = "INT DEFAULT 0")
    private Integer viewCount;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        LocalDateTime now = LocalDateTime.now();
        this.createAt = now;
        this.updatedAt = now;
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}