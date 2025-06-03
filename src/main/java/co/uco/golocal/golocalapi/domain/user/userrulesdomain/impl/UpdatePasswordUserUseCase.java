package co.uco.golocal.golocalapi.domain.user.userrulesdomain.impl;

import co.uco.golocal.golocalapi.domain.user.userrulesdomain.ExistsIdRule;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UpdatePasswordUserUseCase {
    private final ExistsIdRule existsIdRule;

    public UpdatePasswordUserUseCase(ExistsIdRule existsIdRule) {
        this.existsIdRule = existsIdRule;
    }

    public void execute(UUID id) {
        existsIdRule.execute(id);
    }
}
