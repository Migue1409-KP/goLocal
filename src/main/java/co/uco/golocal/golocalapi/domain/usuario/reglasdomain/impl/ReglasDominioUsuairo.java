package co.uco.golocal.golocalapi.domain.usuario.reglasdomain.impl;

import co.uco.golocal.golocalapi.domain.usuario.UserDomain;
import co.uco.golocal.golocalapi.domain.usuario.reglasdomain.UnicoEmailRegla;
import co.uco.golocal.golocalapi.domain.usuario.reglasdomain.UnicoNumeroContactoRegla;
import co.uco.golocal.golocalapi.domain.usuario.reglasdomain.UnicoTipoNumeroIdRegla;
import org.springframework.stereotype.Service;

@Service
public class ReglasDominioUsuairo {
    private UnicoEmailRegla unicoEmailRegla;
    private UnicoTipoNumeroIdRegla unicoTipoNumeroIdRegla;
    private UnicoNumeroContactoRegla unicoNumeroContactoRegla;

    public ReglasDominioUsuairo(UnicoEmailRegla unicoEmailRegla, UnicoTipoNumeroIdRegla unicoTipoNumeroIdRegla,UnicoNumeroContactoRegla unicoNumeroContactoRegla) {
        this.unicoEmailRegla = unicoEmailRegla;
        this.unicoTipoNumeroIdRegla = unicoTipoNumeroIdRegla;
        this.unicoNumeroContactoRegla=unicoNumeroContactoRegla;
    }

    public void validacionReglasDominio(UserDomain usuarioDomain){
        unicoEmailRegla.validacionReglaEmail(usuarioDomain.getCorreo());
        unicoTipoNumeroIdRegla.validacionReglaNumeroId(usuarioDomain.getIdentificacion());
        unicoNumeroContactoRegla.validacionReglaNumeroContacto(usuarioDomain.getNumeroContacto());
    }
}
