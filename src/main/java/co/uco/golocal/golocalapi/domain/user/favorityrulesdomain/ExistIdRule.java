package co.uco.golocal.golocalapi.domain.user.favorityrulesdomain;

import co.uco.golocal.golocalapi.repository.usuario.IFavorityRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ExistIdRule {
    private final IFavorityRepository iFavorityRepository;

    public ExistIdRule(IFavorityRepository iFavorityRepository) {
        this.iFavorityRepository = iFavorityRepository;
    }

    public void execute(UUID experienceId) {
        if (!iFavorityRepository.existsById(experienceId)) {
            throw new IllegalArgumentException("Favorite id: " + experienceId + " does not exist");
        }
    }
}
