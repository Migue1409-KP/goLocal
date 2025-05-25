package co.uco.golocal.golocalapi.domain.business.businessrulesdomain.impl;

import co.uco.golocal.golocalapi.domain.business.BusinessDomain;
import co.uco.golocal.golocalapi.domain.business.businessrulesdomain.UnicNameBusiness;
import org.springframework.stereotype.Service;

@Service
public class BusinesRulesDomain {
    private UnicNameBusiness unicNameBusiness;

    public BusinesRulesDomain(UnicNameBusiness unicNameBusiness) {
        this.unicNameBusiness = unicNameBusiness;
    }

    public void BusinessvalidationDomainRules(BusinessDomain businessDomain) {
        unicNameBusiness.validationRuleName(businessDomain.getName());
    }
}
