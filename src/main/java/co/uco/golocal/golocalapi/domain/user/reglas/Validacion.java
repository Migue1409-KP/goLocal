package co.uco.golocal.golocalapi.domain.user.reglas;

public interface Validacion<D> {

    void execute(D data);
}
