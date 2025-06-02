package co.uco.golocal.golocalapi.domain.user.userrulesdomain.impl;

import co.uco.golocal.golocalapi.data.entity.user.UserEntity;
import co.uco.golocal.golocalapi.domain.user.UserDomain;
import co.uco.golocal.golocalapi.domain.user.userrulesdomain.*;
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

    public UserEntity execute(UserDomain user){
        UserEntity actualUser = existsIdRuleWithReturnRule.execute(user.getId());

        if(user.getEmail() != null && !user.getEmail().isEmpty() && !actualUser.getEmail().equals(user.getEmail())) {
            uniqueEmailRule.execute(user.getEmail());
            actualUser.setEmail(user.getEmail());
        }

        if(user.getTaxId() != null && !user.getTaxId().isEmpty() && !actualUser.getTaxId().equals(user.getTaxId())) {
            uniqueTaxIdRule.execute(user.getTaxId());
            actualUser.setTaxId(user.getTaxId());
        }

        if(user.getPhone() != null && !user.getPhone().isEmpty() && !actualUser.getPhone().equals(user.getPhone())) {
            uniquePhoneRule.execute(user.getPhone());
            actualUser.setPhone(user.getPhone());
        }

        if(user.getName() != null && !user.getName().isEmpty() && !actualUser.getName().equals(user.getName())) {
            actualUser.setName(user.getName());
        }

        if(user.getLastName() != null && !user.getLastName().isEmpty() && !actualUser.getLastName().equals(user.getLastName())) {
            actualUser.setLastName(user.getLastName());
        }

        if(user.getRole() != null && !user.getRole().isEmpty() && !actualUser.getRole().equals(user.getRole())) {
            actualUser.setRole(user.getRole());
        }

        return actualUser;
    }
}
