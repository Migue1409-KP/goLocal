package co.uco.golocal.golocalapi.domain.user.reglas.validaciones;

import co.uco.golocal.golocalapi.domain.user.reglas.Validacion;
import co.uco.golocal.golocalapi.utils.UtilidadTexto;
import org.springframework.stereotype.Service;

@Service
public class ValidacionIdentificacionUsuario implements Validacion<String> {

    @Override
    public void execute(String identificacion) {

        validarObligatoriedad(identificacion);
        validarLogitud(identificacion);
        validarFormato(identificacion);
    }
    private final void validarObligatoriedad(final String identificacion) {
        if(UtilidadTexto.esVacio(identificacion)) {
            throw new RuntimeException("El número de identificación no puede esatr vacio");
        }
    }

    private final void validarLogitud(final String identificacion) {
        if(!UtilidadTexto.tieneLongitudExacta(identificacion, 10)) {
            throw new RuntimeException("La longitud debe ser igual a 10");
        }
    }

    private final void validarFormato(final String identificacion) {
        if(!UtilidadTexto.contieneSoloDigitos(identificacion)) {
            throw new RuntimeException("El número de identificación debe  contener solo digitos");
        }
    }


}
