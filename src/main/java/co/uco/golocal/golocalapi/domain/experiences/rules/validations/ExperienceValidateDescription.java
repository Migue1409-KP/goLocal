package co.uco.golocal.golocalapi.domain.experiences.rules.validations;

import co.uco.golocal.golocalapi.domain.business.BusinessDomain;
import co.uco.golocal.golocalapi.domain.business.rules.Validation;
import co.uco.golocal.golocalapi.domain.experiences.ExperienceDomain;
import co.uco.golocal.golocalapi.utils.UtilidadTexto;
import org.springframework.stereotype.Service;

@Service
public class ExperienceValidateDescription implements Validation<ExperienceDomain>{

    @Override
    public void execute(ExperienceDomain experienceDomain) {
        validateMandatory(experienceDomain);
        validateLength(experienceDomain);
    }


    private void validateMandatory(ExperienceDomain businessDomain) {
        validateMandatoryName(businessDomain.getDescription());
    }
    private void validateLength(ExperienceDomain businessDomain) {
        validateLengthName(businessDomain.getDescription());
    }


    private void validateLengthName(final String description) {
        if(!UtilidadTexto.tieneLongitudValida(description,1,1000)){
            throw new RuntimeException("La descripción de la experiencia debe tener entre 1 y 1000 caracteres.");
        }
    }
    private void validateMandatoryName(String description) {
        if (UtilidadTexto.esNuloOVacio(description)) {
            throw new RuntimeException("La descripción de la experiencia es obligatoria.");
        }
    }

}
