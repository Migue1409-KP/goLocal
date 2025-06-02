package co.uco.golocal.golocalapi.domain.experiences.rules.validations;

import co.uco.golocal.golocalapi.domain.experiences.ExperienceDomain;
import co.uco.golocal.golocalapi.domain.experiences.rules.Validation;
import co.uco.golocal.golocalapi.utils.UtilidadTexto;
import org.springframework.stereotype.Service;

@Service
public class ExperienceValidateName implements Validation<ExperienceDomain> {

    @Override
    public void execute(ExperienceDomain experienceDomain) {
        validateMandatory(experienceDomain);
        validateLength(experienceDomain);
        validateFormat(experienceDomain);
    }


    private void validateMandatory(ExperienceDomain experienceDomain) {validateMandatoryName(experienceDomain.getName());}
    private void validateLength(ExperienceDomain experienceDomain) {
        validateLengthName(experienceDomain.getName());
    }
    private void validateFormat(ExperienceDomain experienceDomain) {
        validateFormatName(experienceDomain.getName());
    }


    private void validateLengthName(final String name) {
        if(!UtilidadTexto.tieneLongitudValida(name,1,50)){
            throw new RuntimeException("El nombre de la experiencia debe tener entre 1 y 50 caracteres.");
        }
    }
    private void validateMandatoryName(String name) {
        if (UtilidadTexto.esNuloOVacio(name)) {
            throw new RuntimeException("El nombre de la experinecia es obligatorio.");
        }
    }

    private void validateFormatName(String name) {
        if(!UtilidadTexto.contieneSoloLetrasDigitosEspaciosEspeciales(name)){
            throw new RuntimeException("El nombre de la experiencia solo puede contener letras, dígitos, espacios y caracteres especiales.");
        }
    }
}
