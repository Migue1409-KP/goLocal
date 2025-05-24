package co.uco.golocal.golocalapi.domain.user.reglas.validaciones;

import co.uco.golocal.golocalapi.domain.user.reglas.Validacion;
import co.uco.golocal.golocalapi.utils.UtilidadTexto;
import org.springframework.stereotype.Service;

@Service
public class ValidacionContaseniaUsuario implements Validacion<String> {

    @Override
    public void execute(String contrasenia) {

        validarObligatoriedad(contrasenia);
        validarLogitud(contrasenia);
        validarFormato(contrasenia);
    }

    private final void validarObligatoriedad(final String contrasenia) {
        if(UtilidadTexto.esVacio(contrasenia)) {
            throw new RuntimeException("La contrasenia no puede esatar vacio");
        }
    }

    private final void validarLogitud(final String contrasenia) {
        if(!UtilidadTexto.tieneLongitudValida(contrasenia, 8,20)) {
            throw new RuntimeException("La longitud debe estar entre 8 a 20 caracteres");
        }
    }

    private final void validarFormato(final String contrasenia) {
        if(!UtilidadTexto.esContrasenaValida(contrasenia)) {
            throw new RuntimeException("La contrasenia debe tener mayusculas, minusculas, y caracteres especiales");
        }
    }


}
