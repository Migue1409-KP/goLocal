package co.uco.golocal.golocalapi.domain.user.favorityrulesdomain;

import co.uco.golocal.golocalapi.repository.usuario.IFavorityRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ExistExperienceIdRule {
    private final IFavorityRepository iFavorityRepository;

    public ExistExperienceIdRule(IFavorityRepository iFavorityRepository) {
        this.iFavorityRepository = iFavorityRepository;
    }

    public void execute(UUID experienceId) {
        if (!iFavorityRepository.existsByExperienceId(experienceId)) {
            throw new IllegalArgumentException("Experience id: " + experienceId + " does not exist");
        }
    }
}
