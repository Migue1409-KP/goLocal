package co.uco.golocal.golocalapi.domain.user.favoriterulesdomain;

import co.uco.golocal.golocalapi.repository.usuario.IFavoriteRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class NotExistFavoriteRule {
    private final IFavoriteRepository iFavoriteRepository;

    public NotExistFavoriteRule(IFavoriteRepository iFavoriteRepository) {
        this.iFavoriteRepository = iFavoriteRepository;
    }

    public void execute(UUID experienceId, UUID userId) {
        if (iFavoriteRepository.existsByExperienceIdAndUserId(experienceId, userId)) {
            throw new IllegalArgumentException("Favorite with experienceId " + experienceId + " and userId " + userId + " exist yet.");
        }
    }
}
