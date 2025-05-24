package co.uco.golocal.golocalapi.controllers.authentication;

import co.uco.golocal.golocalapi.configuracion.seguridad.service.CustomUserDetails;
import co.uco.golocal.golocalapi.domain.user.UserDomain;
import co.uco.golocal.golocalapi.utils.JwtUtilidad;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/rest/autenticacion")
public class AuthenticationController {

    private final JwtUtilidad jwtUtilidad;
    private final AuthenticationManager authenticationManager;

    public AuthenticationController(JwtUtilidad jwtUtilidad, AuthenticationManager authenticationManager) {
        this.jwtUtilidad = jwtUtilidad;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/login")
    public ResponseEntity<String> createAuthenticationToken(@RequestBody UserDomain usuarioDomain) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(usuarioDomain.getEmail(), usuarioDomain.getPassword()));

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();


        String jwt = jwtUtilidad.generarToken(userDetails,userDetails.getId(),userDetails.getRole());

        return ResponseEntity.ok(jwt);
    }
}
