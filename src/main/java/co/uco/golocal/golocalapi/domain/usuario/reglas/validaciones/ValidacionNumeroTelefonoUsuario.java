package co.uco.golocal.golocalapi.domain.usuario.reglas.validaciones;

import co.uco.golocal.golocalapi.domain.usuario.reglas.Validacion;
import co.uco.golocal.golocalapi.utils.UtilidadTexto;
import org.springframework.stereotype.Service;

@Service
public class ValidacionNumeroTelefonoUsuario implements Validacion<String> {

    @Override
    public void execute(String numeroTelefono) {

        validarObligatoriedad(numeroTelefono);
        validarLogitud(numeroTelefono);
        validarFormato(numeroTelefono);
    }
    private final void validarObligatoriedad(final String numeroTelefono) {
        if(UtilidadTexto.esVacio(numeroTelefono)) {
            throw new RuntimeException("El número de telefono no puede esatr vacio");
        }
    }

    private final void validarLogitud(final String numeroTelefono) {
        if(!UtilidadTexto.tieneLongitudExacta(numeroTelefono, 10)) {
            throw new RuntimeException("La longitud debe ser igual a 10");
        }
    }

    private final void validarFormato(final String numeroTelefono) {
        if(!UtilidadTexto.contieneSoloDigitos(numeroTelefono)) {
            throw new RuntimeException("El numero de telefono debe tener solo digiros");
        }
    }


}
