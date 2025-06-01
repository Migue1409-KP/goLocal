package co.uco.golocal.golocalapi.domain.user;

import co.uco.golocal.golocalapi.domain.experiences.ExperienceDomain;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FavorityDomain {
    private UUID id;
    private UserDomain user;
    private ExperienceDomain experience;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

