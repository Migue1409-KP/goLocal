package co.uco.golocal.golocalapi.data.entity.route;

import co.uco.golocal.golocalapi.data.entity.category.CategoryEntity;
import co.uco.golocal.golocalapi.data.entity.experience.ExperienceEntity;
import co.uco.golocal.golocalapi.data.entity.user.UserEntity;
import co.uco.golocal.golocalapi.domain.user.UserDomain;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "routes")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RouteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false, length = 50)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @ManyToMany
    @JoinTable(
            name = "route_category",
            joinColumns = @JoinColumn(name = "route_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private List<CategoryEntity> category;

    @ManyToMany
    @JoinTable(
            name = "route_experience",
            joinColumns = @JoinColumn(name = "route_id"),
            inverseJoinColumns = @JoinColumn(name = "experience_id")
    )
    private List<ExperienceEntity> experience;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
