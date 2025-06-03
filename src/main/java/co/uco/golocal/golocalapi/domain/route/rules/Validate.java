package co.uco.golocal.golocalapi.domain.route.rules;

public interface Validate<T> {
    void execute(T entity);
}
