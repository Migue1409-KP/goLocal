package co.uco.golocal.golocalapi.domain.experiences.experiencesrulesdomain.impl;

import co.uco.golocal.golocalapi.domain.business.BusinessDomain;
import co.uco.golocal.golocalapi.domain.business.businessrulesdomain.UnicNameBusiness;
import org.springframework.stereotype.Service;

@Service
public class ExperienceRulesDomain {
    private UnicNameBusiness unicNameBusiness;

    public ExperienceRulesDomain(UnicNameBusiness unicNameBusiness) {
        this.unicNameBusiness = unicNameBusiness;
    }

    public void BusinessvalidationDomainRules(BusinessDomain businessDomain) {
        unicNameBusiness.execute(businessDomain.getName());
    }
}
