package co.uco.golocal.golocalapi.domain.experiences.rules;

public interface Validation <D> {
    void  execute(D data);
}
