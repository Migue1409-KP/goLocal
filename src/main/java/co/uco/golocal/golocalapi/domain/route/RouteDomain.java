package co.uco.golocal.golocalapi.domain.route;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import co.uco.golocal.golocalapi.domain.category.CategoryDomain;
import co.uco.golocal.golocalapi.domain.experiences.ExperienceDomain;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class RouteDomain {

    private UUID id;
    private String name;
    private List<CategoryDomain> category;
    private List<ExperienceDomain> experience;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
