package co.uco.golocal.golocalapi.domain.user.userrulesdomain.impl;

import co.uco.golocal.golocalapi.domain.user.userrulesdomain.*;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DeleteUserUseCase {
    private final ExistsIdRule existsIdRule;

    public DeleteUserUseCase(ExistsIdRule existsIdRule) {
        this.existsIdRule = existsIdRule;
    }

    public void execute(UUID id){
        existsIdRule.execute(id);
    }
}
