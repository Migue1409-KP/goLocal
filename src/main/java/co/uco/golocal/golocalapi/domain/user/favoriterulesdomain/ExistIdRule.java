package co.uco.golocal.golocalapi.domain.user.favoriterulesdomain;

import co.uco.golocal.golocalapi.repository.usuario.IFavoriteRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ExistIdRule {
    private final IFavoriteRepository iFavoriteRepository;

    public ExistIdRule(IFavoriteRepository iFavoriteRepository) {
        this.iFavoriteRepository = iFavoriteRepository;
    }

    public void execute(UUID experienceId) {
        if (!iFavoriteRepository.existsById(experienceId)) {
            throw new IllegalArgumentException("Favorite id: " + experienceId + " does not exist");
        }
    }
}
