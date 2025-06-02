package co.uco.golocal.golocalapi.domain.route.rules;

public interface Validate<D> {

    void execute(D data);
}
