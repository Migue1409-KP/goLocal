package co.uco.golocal.golocalapi.controllers.authentication;

import co.uco.golocal.golocalapi.configuracion.seguridad.service.CustomUserDetails;
import co.uco.golocal.golocalapi.controllers.support.Response;
import co.uco.golocal.golocalapi.domain.user.UserDomain;
import co.uco.golocal.golocalapi.utils.JwtUtilidad;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/rest/authentication")
public class AuthenticationController {

    private final JwtUtilidad jwtUtilidad;
    private final AuthenticationManager authenticationManager;

    public AuthenticationController(JwtUtilidad jwtUtilidad, AuthenticationManager authenticationManager) {
        this.jwtUtilidad = jwtUtilidad;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/login")
    public ResponseEntity<Response<String>> createAuthenticationToken(@RequestBody UserDomain user) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();


        String jwt = jwtUtilidad.generarToken(userDetails,userDetails.getId(),userDetails.getRole());
        Response<String> response = new Response<>();
        response.setStatus(HttpStatus.OK);
        response.setData(List.of(jwt));
        response.setMessage("Authentication successful");
        return ResponseEntity.ok(response);
    }
}
