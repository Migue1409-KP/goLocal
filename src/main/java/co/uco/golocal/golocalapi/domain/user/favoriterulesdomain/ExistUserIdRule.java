package co.uco.golocal.golocalapi.domain.user.favoriterulesdomain;

import co.uco.golocal.golocalapi.repository.usuario.IFavorityRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ExistUserIdRule {
    private final IFavorityRepository iFavorityRepository;

    public ExistUserIdRule(IFavorityRepository iFavorityRepository) {
        this.iFavorityRepository = iFavorityRepository;
    }

    public void execute(UUID experienceId) {
        if (!iFavorityRepository.existsByUserId(experienceId)) {
            throw new IllegalArgumentException("User id: " + experienceId + " does not exist");
        }
    }
}
