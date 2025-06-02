package co.uco.golocal.golocalapi.domain.user.favoriterulesdomain.impl;

import co.uco.golocal.golocalapi.domain.user.FavoriteDomain;
import co.uco.golocal.golocalapi.domain.user.favoriterulesdomain.ExistExperienceRule;
import co.uco.golocal.golocalapi.domain.user.favoriterulesdomain.NotExistFavoriteRule;
import co.uco.golocal.golocalapi.domain.user.userrulesdomain.ExistsIdRule;
import org.springframework.stereotype.Service;


@Service
public class CreateFavoriteUseCase {
    private final ExistExperienceRule existExperienceRule;
    private final ExistsIdRule existUser;
    private final NotExistFavoriteRule notExistFavoriteRule;

    public CreateFavoriteUseCase(ExistExperienceRule existExperienceRule, ExistsIdRule existUser, NotExistFavoriteRule notExistFavoriteRule) {
        this.existExperienceRule = existExperienceRule;
        this.existUser = existUser;
        this.notExistFavoriteRule = notExistFavoriteRule;
    }

    public void execute(FavoriteDomain favorite) {
        existUser.execute(favorite.getUser().getId());
        existExperienceRule.execute(favorite.getExperience().getId());
        notExistFavoriteRule.execute(favorite.getExperience().getId(), favorite.getUser().getId());
    }
}
