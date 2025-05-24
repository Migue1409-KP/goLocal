package co.uco.golocal.golocalapi.utils;

import java.util.Arrays;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

@Component
public class UtilidadTexto {
	
	public static final String VACIO = "";
	private static final String PATRON_SOLO_DIGITOS = "\\d+";
	private static final String PATRON_SOLO_LETRAS = "^[A-Za-záéíóúÁÉÍÓÚ]+$";
	private static final String PATRON_SOLO_LETRAS_Y_DIGITOS = "^[0-9A-Za-záéíóúÁÉÍÓÚ]+$";
	private static final String PATRON_SOLO_LETRAS_DIGITOS_ESPACIOS = "^[0-9A-Za-záéíóúÁÉÍÓÚ ]+$";
	private static final String CONTRASENA = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&]).*$";
	private static final String PATRON_FECHA = "^([0-2]\\d|3[01])/(\\d|1[0-2])/\\d{4}$";
	private static final String PATRON_LETRAS_DIGITOS_ESPACIOS_ESPECIALES = "^[A-Za-z0-9\\s#\\-\\.,]+$";
	private static final String PATRON_LETRAS_DIGITOS_DOMINIO = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";

	private UtilidadTexto() {
	    super();
	}

	public static final boolean esNulo(final String cadena) {
	    return UtilidadObjecto.esNulo(cadena);
	}

	public static final boolean esNuloOVacio(final String cadena) {
	    return esNulo(cadena) || VACIO.equals(aplicarTrim(cadena));
	}

	public static final String aplicarTrim(final String cadena) {
	    return obtenerPorDefecto(cadena).trim();
	}

	public static final String obtenerPorDefecto(final String cadena, final String valorPorDefecto) {
	    return UtilidadObjecto.obtenerPorDefecto(cadena, valorPorDefecto);
	}

	public static final String obtenerPorDefecto(final String cadena) {
	    return obtenerPorDefecto(cadena, VACIO);
	}

	public static final String concatenar(final String... cadenas) {
	    return Arrays.asList(UtilidadObjecto.obtenerPorDefecto(cadenas, new String[0])).stream().map(cadena -> cadena)
	            .collect(Collectors.joining());
	}

	public static final boolean sonIgualesConTrim(final String valorUno, final String valorDos) {
	    return aplicarTrim(valorUno).equals(aplicarTrim(valorDos));
	}

	public static final boolean esVacio(final String valor) {
	    return sonIgualesConTrim(valor, VACIO);
	}

	public static final boolean esContrasenaValida(final String valor) {
	    return aplicarTrim(valor).matches(CONTRASENA);
	}

	public static final boolean esFechaValida(final String valor) {
	    return valor.matches(PATRON_FECHA);
	}

	public static final boolean tieneLongitudMinima(final String valor, final int longitudMínima) {
	    return aplicarTrim(valor).length() >= longitudMínima;
	}

	public static final boolean tieneLongitudMaxima(final String valor, final int longitudMáxima) {
	    return aplicarTrim(valor).length() <= longitudMáxima;
	}

	public static final boolean tieneLongitudExacta(final String valor, final int longitud) {
	    return aplicarTrim(valor).length() == longitud;
	}

	public static final boolean tieneLongitudValida(final String valor, final int longitudMínima, final int longitudMáxima) {
	    return tieneLongitudMinima(valor, longitudMínima) && tieneLongitudMaxima(valor, longitudMáxima);
	}

	public static boolean contieneSoloLetras(final String valor) {
	    return aplicarTrim(valor).matches(PATRON_SOLO_LETRAS);
	}

	public static boolean contieneSoloLetrasDigitosEspacios(final String valor) {
	    return aplicarTrim(valor).matches(PATRON_SOLO_LETRAS_DIGITOS_ESPACIOS);
	}

	public static boolean contieneLetrasDigitosDominio(final String valor) {
	    return aplicarTrim(valor).matches(PATRON_LETRAS_DIGITOS_DOMINIO);
	}

	public static final boolean contieneSoloDigitos(final String valor) {
	    return aplicarTrim(valor).matches(PATRON_SOLO_DIGITOS);
	}

	public static final boolean contieneSoloLetrasDigitos(final String valor) {
	    return aplicarTrim(valor).matches(PATRON_SOLO_LETRAS_Y_DIGITOS);
	}
	public static final boolean contieneSoloLetrasDigitosEspaciosEspeciales(final String valor) {
		return aplicarTrim(valor).matches(PATRON_LETRAS_DIGITOS_ESPACIOS_ESPECIALES);
	}
}
