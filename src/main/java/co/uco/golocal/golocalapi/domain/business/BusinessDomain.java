package co.uco.golocal.golocalapi.domain.business;

import lombok.*;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
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
    private UUID location;
    private UUID userId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
