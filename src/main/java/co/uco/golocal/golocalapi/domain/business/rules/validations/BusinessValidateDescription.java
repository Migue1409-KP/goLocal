package co.uco.golocal.golocalapi.domain.business.rules.validations;

import co.uco.golocal.golocalapi.domain.business.BusinessDomain;
import co.uco.golocal.golocalapi.domain.business.rules.Validation;
import co.uco.golocal.golocalapi.utils.UtilidadTexto;
import org.springframework.stereotype.Service;

@Service
public class BusinessValidateDescription implements Validation<BusinessDomain>{

    @Override
    public void execute(BusinessDomain businessDomain) {
        validateMandatory(businessDomain);
        validateLength(businessDomain);
    }


    private void validateMandatory(BusinessDomain businessDomain) {
        validateMandatoryName(businessDomain.getDescription());
    }
    private void validateLength(BusinessDomain businessDomain) {
        validateLengthName(businessDomain.getDescription());
    }


    private void validateLengthName(final String description) {
        if(!UtilidadTexto.tieneLongitudValida(description,1,500)){
            throw new RuntimeException("La descripción del negocio debe tener entre 1 y 500 caracteres.");
        }
    }
    private void validateMandatoryName(String description) {
        if (UtilidadTexto.esNuloOVacio(description)) {
            throw new RuntimeException("La descripción del negocio es obligatoria.");
        }
    }

}
