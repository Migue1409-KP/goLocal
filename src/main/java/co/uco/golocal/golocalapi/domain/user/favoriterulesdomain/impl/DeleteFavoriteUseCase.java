package co.uco.golocal.golocalapi.domain.user.favoriterulesdomain.impl;

import co.uco.golocal.golocalapi.domain.user.favoriterulesdomain.ExistIdRule;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DeleteFavoriteUseCase {
    private final ExistIdRule existIdRule;

    public DeleteFavoriteUseCase(ExistIdRule existIdRule) {
        this.existIdRule = existIdRule;

    }

    public void execute(UUID id) {
        existIdRule.execute(id);
    }
}
