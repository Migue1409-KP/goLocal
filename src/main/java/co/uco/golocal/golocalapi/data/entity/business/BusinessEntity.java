package co.uco.golocal.golocalapi.data.entity.business;

import co.uco.golocal.golocalapi.data.entity.experience.ExperienceEntity;
import co.uco.golocal.golocalapi.data.entity.location.CityEntity;
import co.uco.golocal.golocalapi.data.entity.user.UserEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Table(name = "Business")
@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class BusinessEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location", referencedColumnName = "id")
    private CityEntity location;
    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "Users", referencedColumnName = "id", nullable = false)
    private UserEntity user;
    @OneToMany(mappedBy = "business", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ExperienceEntity> experiences;
    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;

}
