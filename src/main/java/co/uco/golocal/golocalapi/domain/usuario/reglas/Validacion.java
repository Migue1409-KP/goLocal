package co.uco.golocal.golocalapi.domain.usuario.reglas;

public interface Validacion<D> {

    void execute(D data);
}
