package co.uco.golocal.golocalapi.domain.business.businessrulesdomain.impl;

import co.uco.golocal.golocalapi.domain.business.BusinessDomain;
import co.uco.golocal.golocalapi.domain.business.businessrulesdomain.UnicNameBusiness;
import org.springframework.stereotype.Service;

@Service
public class CreateBusinesUseCase {

    private final UnicNameBusiness unicNameBusiness;


    public CreateBusinesUseCase(UnicNameBusiness unicNameBusiness) {
        this.unicNameBusiness = unicNameBusiness;
    }

    public void execute(BusinessDomain businessDomain) {
        unicNameBusiness.execute(businessDomain.getName());
    }
}
