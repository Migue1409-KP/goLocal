package co.uco.golocal.golocalapi.domain.usuario.reglas.validaciones.impl;

import co.uco.golocal.golocalapi.domain.usuario.UserDomain;
import co.uco.golocal.golocalapi.domain.usuario.reglas.validaciones.*;
import org.springframework.stereotype.Service;

@Service
public class ValidacionesUsuarioImpl {


    private ValidacionNombreCompletoUsuario validacionNombreCompletoUsuario;
    private ValidacionIdentificacionUsuario validacionIdentificacionUsuario;
    private ValidacionCorreoUsuario validacionCorreoUsuario;
    private ValidacionContaseniaUsuario validacionContaseniaUsuario;
    private ValidacionDireccionUsuario validacionDireccionUsuario;
    private ValidacionNumeroTelefonoUsuario validacionNumeroTelefonoUsuario;

    public ValidacionesUsuarioImpl(ValidacionNombreCompletoUsuario validacionNombreCompletoUsuario,
                                   ValidacionIdentificacionUsuario validacionIdentificacionUsuario,
                                   ValidacionCorreoUsuario validacionCorreoUsuario,
                                   ValidacionContaseniaUsuario validacionContaseniaUsuario,
                                   ValidacionDireccionUsuario validacionDireccionUsuario,
                                   ValidacionNumeroTelefonoUsuario validacionNumeroTelefonoUsuario) {
        this.validacionNombreCompletoUsuario = validacionNombreCompletoUsuario;
        this.validacionIdentificacionUsuario = validacionIdentificacionUsuario;
        this.validacionCorreoUsuario = validacionCorreoUsuario;
        this.validacionContaseniaUsuario = validacionContaseniaUsuario;
        this.validacionDireccionUsuario = validacionDireccionUsuario;
        this.validacionNumeroTelefonoUsuario = validacionNumeroTelefonoUsuario;
    }

    public void validaciones(UserDomain usuarioDomain){

        validacionNombreCompletoUsuario.execute(usuarioDomain);

        validacionIdentificacionUsuario.execute(usuarioDomain.getIdentificacion());

        validacionCorreoUsuario.execute(usuarioDomain.getCorreo());

        validacionContaseniaUsuario.execute(usuarioDomain.getContrasena());

        validacionDireccionUsuario.execute(usuarioDomain.getDireccion());

        validacionNumeroTelefonoUsuario.execute(usuarioDomain.getNumeroContacto());

        validacionCorreoUsuario.execute(usuarioDomain.getCorreoEmergencia());
    }
}
