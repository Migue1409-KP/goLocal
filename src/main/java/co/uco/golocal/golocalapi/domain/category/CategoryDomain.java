package co.uco.golocal.golocalapi.domain.category;

import lombok.*;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryDomain {
    private UUID id;
    private String name;
    private String createdAt;
    private String updatedAt;
}
