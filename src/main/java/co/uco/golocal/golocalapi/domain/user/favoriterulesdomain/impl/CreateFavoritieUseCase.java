package co.uco.golocal.golocalapi.domain.user.favoriterulesdomain.impl;

import co.uco.golocal.golocalapi.domain.user.FavoriteDomain;
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

    public void execute(FavoriteDomain favorite) {
        existUserIdRule.execute(favorite.getUser().getId());
        existExperienceIdRule.execute(favorite.getExperience().getId());
    }
}
