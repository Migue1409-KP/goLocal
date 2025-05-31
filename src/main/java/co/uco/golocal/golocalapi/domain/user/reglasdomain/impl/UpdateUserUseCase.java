package co.uco.golocal.golocalapi.domain.user.reglasdomain.impl;

import co.uco.golocal.golocalapi.data.entity.user.UserEntity;
import co.uco.golocal.golocalapi.domain.user.UserDomain;
import co.uco.golocal.golocalapi.domain.user.reglasdomain.*;
import org.springframework.stereotype.Service;

@Service
public class UpdateUserUseCase {
    private final UniqueEmailRule uniqueEmailRule;
    private final UniqueTaxIdRule uniqueTaxIdRule;
    private final UniquePhoneRule uniquePhoneRule;
    private final ExistsIdWithReturnRule existsIdRuleWithReturnRule;

    public UpdateUserUseCase(UniqueEmailRule uniqueEmailRule, UniqueTaxIdRule uniqueTaxIdRule,
                             UniquePhoneRule uniquePhoneRule, ExistsIdWithReturnRule existsIdRuleWithReturnRule) {
        this.uniqueEmailRule = uniqueEmailRule;
        this.uniqueTaxIdRule = uniqueTaxIdRule;
        this.uniquePhoneRule = uniquePhoneRule;
        this.existsIdRuleWithReturnRule = existsIdRuleWithReturnRule;
    }

    public void execute(UserDomain user){
        UserEntity actualUser = existsIdRuleWithReturnRule.execute(user.getId());

        if(!actualUser.getEmail().equals(user.getEmail())) {
            uniqueEmailRule.execute(user.getEmail());
        }

        if(!actualUser.getTaxId().equals(user.getTaxId())) {
            uniqueTaxIdRule.execute(user.getTaxId());
        }

        if(!actualUser.getPhone().equals(user.getPhone())) {
            uniquePhoneRule.execute(user.getPhone());
        }
    }
}
