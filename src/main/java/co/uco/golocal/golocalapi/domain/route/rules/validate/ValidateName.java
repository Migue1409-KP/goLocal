package co.uco.golocal.golocalapi.domain.route.rules.validate;


import co.uco.golocal.golocalapi.domain.route.RouteDomain;
import co.uco.golocal.golocalapi.domain.route.rules.Validate;
import co.uco.golocal.golocalapi.utils.UtilidadTexto;
import org.springframework.stereotype.Service;

@Service
public class ValidateName implements Validate<RouteDomain> {

    @Override
    public void execute(RouteDomain routeDomain) {
        validateObligatory(routeDomain.getName());
        validateLength(routeDomain.getName());
        validateFormat(routeDomain.getName());
    }

    private void validateFormat(String name) {
        if (!UtilidadTexto.contieneSoloLetrasDigitosEspacios(name)) {
            throw new RuntimeException("The route name can only contain letters, numbers, and spaces");
        }
    }

    private void validateObligatory(final String name) {
        if (UtilidadTexto.esNuloOVacio(name)) {
            throw new RuntimeException("The route name cannot be empty");
        }
    }
    private void validateLength(final String name) {
        if (!UtilidadTexto.tieneLongitudMaxima(name, 50)) {
            throw new RuntimeException("The maximum length of the route name is 50 characters");
        }
    }

}
