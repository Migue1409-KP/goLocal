package co.uco.golocal.golocalapi.data.entity.experience;

import co.uco.golocal.golocalapi.data.entity.business.BusinessEntity;
import co.uco.golocal.golocalapi.data.entity.category.CategoryEntity;
import co.uco.golocal.golocalapi.data.entity.user.FavoriteEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Table(name = "experiences")
@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ExperienceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    private double price;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "business", referencedColumnName = "id", nullable = false)
    private BusinessEntity business;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "category", referencedColumnName = "id", nullable = false)
    private CategoryEntity category;
    @OneToMany(mappedBy = "experience", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FavoriteEntity> favorites;
    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;
}
