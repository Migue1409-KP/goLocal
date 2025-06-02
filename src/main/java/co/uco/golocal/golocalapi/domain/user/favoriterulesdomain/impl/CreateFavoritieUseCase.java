package co.uco.golocal.golocalapi.domain.user.favoriterulesdomain.impl;

import co.uco.golocal.golocalapi.domain.user.FavoriteDomain;
import co.uco.golocal.golocalapi.domain.user.favoriterulesdomain.ExistExperienceRule;
import co.uco.golocal.golocalapi.domain.user.userrulesdomain.ExistsIdRule;
import org.springframework.stereotype.Service;


@Service
public class CreateFavoritieUseCase {
    private final ExistExperienceRule existExperienceRule;
    private final ExistsIdRule existUser;

    public CreateFavoritieUseCase(ExistExperienceRule existExperienceRule, ExistsIdRule existUser) {
        this.existExperienceRule = existExperienceRule;
        this.existUser = existUser;
    }

    public void execute(FavoriteDomain favorite) {
        existUser.execute(favorite.getUser().getId());
        existExperienceRule.execute(favorite.getExperience().getId());
    }
}
