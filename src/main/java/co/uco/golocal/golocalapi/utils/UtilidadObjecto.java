package co.uco.golocal.golocalapi.utils;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UtilidadObjecto {
	
	public static final String VACIO = "";

	private UtilidadObjecto() {
	    super();
	}

	public static final <T> boolean esNulo(final T objeto) {
	    return objeto == null;
	}

	public static final <T> T obtenerPorDefecto(final T objeto, final T objetoPorDefecto) {
	    return esNulo(objeto) ? objetoPorDefecto : objeto;
	}

	public static final <T> boolean esVacio(final T objeto) {
	    return objeto.equals(VACIO);
	}


	public static boolean isEmptyOrNull(UUID location) {
		return location == null || location.equals(new UUID(0L, 0L));
	}
}
