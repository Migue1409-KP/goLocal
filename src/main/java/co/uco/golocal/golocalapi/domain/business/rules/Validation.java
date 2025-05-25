package co.uco.golocal.golocalapi.domain.business.rules;

public interface Validation <D> {
    void  execute(D data);
}
