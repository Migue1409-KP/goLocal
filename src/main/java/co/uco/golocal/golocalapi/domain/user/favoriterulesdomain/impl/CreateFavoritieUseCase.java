package co.uco.golocal.golocalapi.domain.user.favoriterulesdomain.impl;

import co.uco.golocal.golocalapi.domain.user.favoriterulesdomain.ExistExperienceIdRule;
import co.uco.golocal.golocalapi.domain.user.favoriterulesdomain.ExistUserIdRule;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CreateFavoritieUseCase {
    private final ExistExperienceIdRule existExperienceIdRule;
    private final ExistUserIdRule existUserIdRule;

    public CreateFavoritieUseCase(ExistExperienceIdRule existExperienceIdRule, ExistUserIdRule existUserIdRule) {
        this.existExperienceIdRule = existExperienceIdRule;
        this.existUserIdRule = existUserIdRule;
    }

    public void execute(UUID userId, UUID experienceId) {
        existUserIdRule.execute(userId);
        existExperienceIdRule.execute(experienceId);
    }
}
