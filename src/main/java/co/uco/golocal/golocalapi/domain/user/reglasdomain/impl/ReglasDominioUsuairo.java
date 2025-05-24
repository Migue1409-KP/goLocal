package co.uco.golocal.golocalapi.domain.user.reglasdomain.impl;

import co.uco.golocal.golocalapi.domain.user.UserDomain;
import co.uco.golocal.golocalapi.domain.user.reglasdomain.UnicoEmailRegla;
import co.uco.golocal.golocalapi.domain.user.reglasdomain.UnicoNumeroContactoRegla;
import co.uco.golocal.golocalapi.domain.user.reglasdomain.UnicoTipoNumeroIdRegla;
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
        unicoEmailRegla.validacionReglaEmail(usuarioDomain.getEmail());
        unicoTipoNumeroIdRegla.validacionReglaNumeroId(usuarioDomain.getTaxId());
        unicoNumeroContactoRegla.validacionReglaNumeroContacto(usuarioDomain.getPhone());
    }
}
