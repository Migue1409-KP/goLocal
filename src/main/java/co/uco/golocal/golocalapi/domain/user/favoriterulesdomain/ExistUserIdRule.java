package co.uco.golocal.golocalapi.domain.user.favoriterulesdomain;

import co.uco.golocal.golocalapi.repository.usuario.IFavoriteRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ExistUserIdRule {
    private final IFavoriteRepository iFavoriteRepository;

    public ExistUserIdRule(IFavoriteRepository iFavoriteRepository) {
        this.iFavoriteRepository = iFavoriteRepository;
    }

    public void execute(UUID experienceId) {
        if (!iFavoriteRepository.existsByUserId(experienceId)) {
            throw new IllegalArgumentException("User id: " + experienceId + " does not exist");
        }
    }
}
