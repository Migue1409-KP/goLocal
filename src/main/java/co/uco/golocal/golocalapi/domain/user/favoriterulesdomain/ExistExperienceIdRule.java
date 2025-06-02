package co.uco.golocal.golocalapi.domain.user.favoriterulesdomain;

import co.uco.golocal.golocalapi.repository.usuario.IFavoriteRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ExistExperienceIdRule {
    private final IFavoriteRepository iFavoriteRepository;

    public ExistExperienceIdRule(IFavoriteRepository iFavoriteRepository) {
        this.iFavoriteRepository = iFavoriteRepository;
    }

    public void execute(UUID experienceId) {
        if (!iFavoriteRepository.existsByExperienceId(experienceId)) {
            throw new IllegalArgumentException("Experience id: " + experienceId + " does not exist");
        }
    }
}
