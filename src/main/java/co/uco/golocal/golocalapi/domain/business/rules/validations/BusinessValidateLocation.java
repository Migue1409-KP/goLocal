package co.uco.golocal.golocalapi.domain.business.rules.validations;

import co.uco.golocal.golocalapi.domain.business.BusinessDomain;
import co.uco.golocal.golocalapi.domain.business.rules.Validation;
import co.uco.golocal.golocalapi.utils.UtilidadObjecto;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class BusinessValidateLocation implements Validation<BusinessDomain> {

    @Override
    public void execute(BusinessDomain businessDomain){
        validateMandatory(businessDomain);
    }

    private void validateMandatory(BusinessDomain businessDomain) {
        validateMandatoryLocation(businessDomain.getLocation().getId());
    }

    private void validateMandatoryLocation(UUID location) {
        if(UtilidadObjecto.isEmptyOrNull(location)) {
            throw new RuntimeException("La ubicación del negocio es obligatoria.");
    }
    }
}
