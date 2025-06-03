package co.uco.golocal.golocalapi.data.entity.review;

import co.uco.golocal.golocalapi.data.entity.experience.ExperienceEntity;
import co.uco.golocal.golocalapi.data.entity.route.RouteEntity;
import co.uco.golocal.golocalapi.data.entity.user.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "reviews")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReviewEntity {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private Double rating;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @Column(name = "publicationDt")
    @Temporal(TemporalType.DATE)
    private Date publicationDate;

    @Column(length = 500)
    private String description;

    @ManyToOne
    @JoinColumn(name = "experience_id")  // Cambiado de "experiences" a "experience_id"
    private ExperienceEntity experience;

    @ManyToOne
    @JoinColumn(name = "route_id")  // Cambiado de "routes" a "route_id"
    private RouteEntity route;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        if (publicationDate == null) {
            publicationDate = new Date();
        }
    }
}