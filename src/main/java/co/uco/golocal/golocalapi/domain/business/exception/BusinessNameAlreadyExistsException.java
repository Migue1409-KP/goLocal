package co.uco.golocal.golocalapi.domain.business.exception;

public class BusinessNameAlreadyExistsException extends RuntimeException {
        public BusinessNameAlreadyExistsException(String name) {
            super("El nombre de negocio '" + name + "' ya existe.");
        }
    }


