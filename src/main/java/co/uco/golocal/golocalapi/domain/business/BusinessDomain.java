package co.uco.golocal.golocalapi.domain.business;

import co.uco.golocal.golocalapi.domain.category.CategoryDomain;
import co.uco.golocal.golocalapi.domain.location.CityDomain;
import lombok.*;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Component
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BusinessDomain {
    private UUID id;
    private String name;
    private String description;
    private CityDomain location;
    private UUID userId;
    private List<CategoryDomain> categories;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}