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
    private ValidacionNumeroTelefonoUsuario validacionNumeroTelefonoUsuario;

    public ValidacionesUsuarioImpl(ValidacionNombreCompletoUsuario validacionNombreCompletoUsuario,
                                   ValidacionIdentificacionUsuario validacionIdentificacionUsuario,
                                   ValidacionCorreoUsuario validacionCorreoUsuario,
                                   ValidacionContaseniaUsuario validacionContaseniaUsuario,
                                   ValidacionNumeroTelefonoUsuario validacionNumeroTelefonoUsuario) {
        this.validacionNombreCompletoUsuario = validacionNombreCompletoUsuario;
        this.validacionIdentificacionUsuario = validacionIdentificacionUsuario;
        this.validacionCorreoUsuario = validacionCorreoUsuario;
        this.validacionContaseniaUsuario = validacionContaseniaUsuario;
        this.validacionNumeroTelefonoUsuario = validacionNumeroTelefonoUsuario;
    }

    public void validaciones(UserDomain usuarioDomain){

        validacionNombreCompletoUsuario.execute(usuarioDomain);

        validacionIdentificacionUsuario.execute(usuarioDomain.getTaxId());

        validacionCorreoUsuario.execute(usuarioDomain.getEmail());

        validacionContaseniaUsuario.execute(usuarioDomain.getPassword());

        validacionNumeroTelefonoUsuario.execute(usuarioDomain.getPhone());
    }
}
