package co.uco.golocal.golocalapi.domain.usuario.reglas.validaciones;

import co.uco.golocal.golocalapi.domain.usuario.reglas.Validacion;
import co.uco.golocal.golocalapi.utils.UtilidadTexto;
import org.springframework.stereotype.Service;

@Service
public class ValidacionDireccionUsuario implements Validacion<String> {

    @Override
    public void execute(String direccion) {

        validarObligatoriedad(direccion);
        validarLogitud(direccion);
        validarFormato(direccion);
    }
    private void validarObligatoriedad(final String direccion) {
        if(UtilidadTexto.esVacio(direccion)) {
            throw new RuntimeException("La direccion no puede esatar vacio");
        }
    }

    private void validarLogitud(final String direccion) {
        if(!UtilidadTexto.tieneLongitudValida(direccion, 4,40)) {
            throw new RuntimeException("La longitud debe estar entre 4 a 40 caracteres");
        }
    }

    private void validarFormato(final String direccion) {
        if(!UtilidadTexto.contieneSoloLetrasDigitosEspaciosEspeciales(direccion)) {
            throw new RuntimeException("El formato de la direccion no es valido");
        }
    }


}
