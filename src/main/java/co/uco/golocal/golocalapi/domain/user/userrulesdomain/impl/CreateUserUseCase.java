package co.uco.golocal.golocalapi.domain.user.userrulesdomain.impl;

import co.uco.golocal.golocalapi.domain.user.UserDomain;
import co.uco.golocal.golocalapi.domain.user.userrulesdomain.UniqueEmailRule;
import co.uco.golocal.golocalapi.domain.user.userrulesdomain.UniquePhoneRule;
import co.uco.golocal.golocalapi.domain.user.userrulesdomain.UniqueTaxIdRule;
import org.springframework.stereotype.Service;

@Service
public class CreateUserUseCase {
    private final UniqueEmailRule uniqueEmailRule;
    private final UniqueTaxIdRule uniqueTaxIdRule;
    private final UniquePhoneRule uniquePhoneRule;

    public CreateUserUseCase(UniqueEmailRule uniqueEmailRule, UniqueTaxIdRule uniqueTaxIdRule, UniquePhoneRule uniquePhoneRule) {
        this.uniqueEmailRule = uniqueEmailRule;
        this.uniqueTaxIdRule = uniqueTaxIdRule;
        this.uniquePhoneRule = uniquePhoneRule;
    }

    public void execute(UserDomain user){
        uniqueEmailRule.execute(user.getEmail());
        uniqueTaxIdRule.execute(user.getTaxId());
        uniquePhoneRule.execute(user.getPhone());
    }
}
