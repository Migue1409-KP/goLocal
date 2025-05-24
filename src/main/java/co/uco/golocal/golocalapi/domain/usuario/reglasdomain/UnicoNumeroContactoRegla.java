package co.uco.golocal.golocalapi.domain.usuario.reglasdomain;
import co.uco.golocal.golocalapi.domain.usuario.exception.DuplicateNumeroContactoException;
import co.uco.golocal.golocalapi.repository.usuario.IUsuarioRepositorio;
import org.springframework.stereotype.Service;

@Service
public class UnicoNumeroContactoRegla {
    private final IUsuarioRepositorio iUsuarioRepositorio;


    public UnicoNumeroContactoRegla(IUsuarioRepositorio iUsuarioRepositorio) {
        this.iUsuarioRepositorio = iUsuarioRepositorio;
    }

    public void validacionReglaNumeroContacto(String numeroContacto){
        if(iUsuarioRepositorio.existsByNumeroContacto(numeroContacto)){
            throw new DuplicateNumeroContactoException("Ya fue registrado este numero: "+ numeroContacto+ " anteriormente.");
        }
    }
}
