package co.uco.golocal.golocalapi.data.entity.route;


import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.UUID;

@Table(name="Routes")
@Entity
@EntityListeners(AuditingEntityListener.class)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class RouteEntity {
    @Id
    private UUID id;
    private String name;
    private String category;
    private String experience;
    private String createdAt;
    private String updatedAt;
}
