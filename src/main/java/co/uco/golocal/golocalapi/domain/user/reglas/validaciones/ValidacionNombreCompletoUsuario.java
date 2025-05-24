package co.uco.golocal.golocalapi.domain.user.reglas.validaciones;

import co.uco.golocal.golocalapi.domain.user.UserDomain;
import co.uco.golocal.golocalapi.domain.user.reglas.Validacion;
import co.uco.golocal.golocalapi.utils.UtilidadTexto;
import org.springframework.stereotype.Service;

@Service
public class ValidacionNombreCompletoUsuario implements Validacion<UserDomain> {

    @Override
    public void execute(UserDomain usuarioDomain) {

        validarObligatoriedad(usuarioDomain);
        validarLogitud(usuarioDomain);
        validarFormato(usuarioDomain);
    }
    private void validarObligatoriedad(UserDomain usuarioDomain) {
        validarObligatoriedNombre(usuarioDomain.getName());
        validarObligatoriedApellido(usuarioDomain.getLastName());
    }

    private void validarLogitud(UserDomain usuarioDomain) {
       validarLogitudNombre(usuarioDomain.getName());
       validarLongitudApellido(usuarioDomain.getLastName());

    }
    private void validarFormato(UserDomain usuarioDomain) {
        validarFormatoNombre(usuarioDomain.getName());
        validarFormatoApellido(usuarioDomain.getLastName());

    }

    private void validarObligatoriedNombre(String nombre) {
        if(UtilidadTexto.esNuloOVacio(nombre)) {
            throw new RuntimeException("El nombre no puede estar vacio");
        }
    }
    private void validarObligatoriedApellido(String apellido) {
        if(UtilidadTexto.esNuloOVacio(apellido)) {
            throw new RuntimeException("El apellido no puede estar vacio");
        }
    }
    private  void validarLogitudNombre(final String nombre) {
        if(!UtilidadTexto.tieneLongitudMaxima(nombre, 25)) {
            throw new RuntimeException("La logitud maxima del nombre es de 25 caracteres");
        }
    }

    private final void validarLongitudApellido(final String apellido) {
        if(!UtilidadTexto.tieneLongitudMaxima(apellido, 25)) {
            throw new RuntimeException("La logitud maxima del apellido es de 25 caracteres");
        }
    }

    private void validarFormatoNombre(String nombre) {
        if(!UtilidadTexto.contieneSoloLetrasDigitosEspacios(nombre)) {
            throw new RuntimeException("El nombre solo puede tener letras o digitos");
        }
    }
    private void validarFormatoApellido(String apellido) {
        if(!UtilidadTexto.contieneSoloLetrasDigitosEspacios(apellido)) {
            throw new RuntimeException("El apellido solo puede tener letras o digitos");
        }
    }
}
