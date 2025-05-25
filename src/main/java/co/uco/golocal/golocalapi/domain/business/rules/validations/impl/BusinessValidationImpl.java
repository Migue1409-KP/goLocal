package co.uco.golocal.golocalapi.domain.business.rules.validations.impl;

import co.uco.golocal.golocalapi.domain.business.BusinessDomain;
import co.uco.golocal.golocalapi.domain.business.rules.validations.BusinessValidateDescription;
import co.uco.golocal.golocalapi.domain.business.rules.validations.BusinessValidateLocation;
import co.uco.golocal.golocalapi.domain.business.rules.validations.BusinessValidateName;
import org.springframework.stereotype.Service;

@Service
public class BusinessValidationImpl {
    private BusinessValidateName businessValidateName;
    private BusinessValidateDescription businessValidateDescription;
    private BusinessValidateLocation businessValidateLocation;

    public BusinessValidationImpl(BusinessValidateName businessValidateName,
                                  BusinessValidateDescription businessValidateDescription,
                                  BusinessValidateLocation businessValidateLocation) {
        this.businessValidateName = businessValidateName;
        this.businessValidateDescription = businessValidateDescription;
        this.businessValidateLocation = businessValidateLocation;
    }

    public void validations(BusinessDomain businessDomain) {
        businessValidateName.execute(businessDomain);
        businessValidateDescription.execute(businessDomain);
        businessValidateLocation.execute(businessDomain);
    }
}
