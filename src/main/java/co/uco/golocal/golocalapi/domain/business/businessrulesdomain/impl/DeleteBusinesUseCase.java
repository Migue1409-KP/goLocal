package co.uco.golocal.golocalapi.domain.business.businessrulesdomain.impl;

import co.uco.golocal.golocalapi.domain.business.businessrulesdomain.ExistBussinestIdRule;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DeleteBusinesUseCase {
    private final ExistBussinestIdRule exisBussinestIdRule;

    public DeleteBusinesUseCase(ExistBussinestIdRule exisBussinestIdRule) {
        this.exisBussinestIdRule = exisBussinestIdRule;
    }

    public void execute(UUID id) {
        exisBussinestIdRule.execute(id);
    }
}
