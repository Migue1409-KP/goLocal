package co.uco.golocal.golocalapi.data.mapper;

public interface MapperEntity<E, D> {
    D toDomain(E entity);
    E toEntity(D domain);
}
