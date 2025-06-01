package co.uco.golocal.golocalapi.domain.business.businessrulesdomain.impl;

import co.uco.golocal.golocalapi.domain.business.businessrulesdomain.ExisBussinestIdRule;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DeleteBusinesUseCase {
    private final ExisBussinestIdRule exisBussinestIdRule;

    public DeleteBusinesUseCase(ExisBussinestIdRule exisBussinestIdRule) {
        this.exisBussinestIdRule = exisBussinestIdRule;
    }

    public void execute(UUID id) {
        exisBussinestIdRule.execute(id);
    }
}
