package co.uco.golocal.golocalapi.domain.business.rules.validations;

import co.uco.golocal.golocalapi.domain.business.BusinessDomain;
import co.uco.golocal.golocalapi.domain.business.rules.Validation;
import co.uco.golocal.golocalapi.utils.UtilidadTexto;
import org.springframework.stereotype.Service;

@Service
public class BusinessValidateName  implements Validation<BusinessDomain> {

    @Override
    public void execute(BusinessDomain businessDomain) {
        validateMandatory(businessDomain);
        validateLength(businessDomain);
        validateFormat(businessDomain);
    }


    private void validateMandatory(BusinessDomain businessDomain) {
        validateMandatoryName(businessDomain.getName());
    }
    private void validateLength(BusinessDomain businessDomain) {
        validateLengthName(businessDomain.getName());
    }

    private void validateFormat(BusinessDomain businessDomain) {
        validateFormatName(businessDomain.getName());
    }


    private void validateLengthName(final String name) {
        if(!UtilidadTexto.tieneLongitudValida(name,1,30)){
            throw new RuntimeException("El nombre del negocio debe tener entre 1 y 30aracteres.");
        }
    }
    private void validateMandatoryName(String name) {
        if (UtilidadTexto.esNuloOVacio(name)) {
            throw new RuntimeException("El nombre del negocio es obligatorio.");
        }
    }

    private void validateFormatName(String name) {
        if(!UtilidadTexto.contieneSoloLetrasDigitosEspaciosEspeciales(name)){
            throw new RuntimeException("El nombre del negocio solo puede contener letras, dígitos, espacios y caracteres especiales.");
        }
    }
}
