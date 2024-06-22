package Member.query.dto;

import Product.query.dto.ProductData;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "user")
public class MemberData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String password;

    private  String nickname;

    private String phone;

    private String address;

    @CreationTimestamp
    @Column(name = "create_at")
    private LocalDateTime insDate;

    @UpdateTimestamp
    @Column(name = "update_at")
    private LocalDateTime updDate;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<ProductData> products = new ArrayList<>();
};
