package co.uco.golocal.golocalapi.domain.review;

import co.uco.golocal.golocalapi.domain.experiences.ExperienceDomain;
import co.uco.golocal.golocalapi.domain.route.RouteDomain;
import co.uco.golocal.golocalapi.domain.user.UserDomain;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReviewDomain {

    private UUID id;
    private Double rating;
    private UserDomain user;
    private Date publicationDate;
    private String description;
    private ExperienceDomain experience;
    private RouteDomain route;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public UUID getRouteId() {
        // Retorna el ID de la ruta si existe, null en caso contrario
        return route != null ? route.getId() : null;
    }

    public UUID getExperienceId() {
        // Retorna el ID de la experiencia si existe, null en caso contrario
        return experience != null ? experience.getId() : null;
    }


    public UUID getUserId() {
        // Retorna el ID del usuario si existe, null en caso contrario
        return user != null ? user.getId() : null;
    }
}
