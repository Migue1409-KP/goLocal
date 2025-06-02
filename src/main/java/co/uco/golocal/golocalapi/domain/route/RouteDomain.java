package co.uco.golocal.golocalapi.domain.route;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RouteDomain {

    private UUID id;
    private String name;
    private String category;
    private String experience;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
