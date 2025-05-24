package co.uco.golocal.golocalapi.utils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import java.util.Date;
import java.util.function.Function;
import java.util.UUID;

@Component
public class JwtUtilidad {

    private static final String SECRET = "g5&8vR!qZ2#jK1@xP9*wD4zY$L0eM6sT";

    public String extraerNombreUsuario(String token) {
        return extraerReclamacion(token, Claims::getSubject);
    }

    public Date extraerExpiracion(String token) {
        return extraerReclamacion(token, Claims::getExpiration);
    }

    private <T> T extraerReclamacion(String token, Function<Claims, T> reclamacionResolver) {
        final Claims reclamaciones = extraerTodasLasReclamaciones(token);
        return reclamacionResolver.apply(reclamaciones);
    }

    private Claims extraerTodasLasReclamaciones(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET.getBytes())
                .parseClaimsJws(token)
                .getBody();
    }

    private Boolean esTokenExpirado(String token) {
        return extraerExpiracion(token).before(new Date());
    }

    public String generarToken(UserDetails userDetails, UUID id, String rol) {
        return crearToken(userDetails.getUsername(), id, rol);
    }

    private String crearToken(String sujeto, UUID id, String rol) {
        return Jwts.builder()
                .setSubject(sujeto)
                .claim("id", id.toString()) // Agregar el ID del usuario como una reclamación
                .claim("rol", rol) // Agregar el rol del usuario como una reclamación
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 horas
                .signWith(SignatureAlgorithm.HS256, SECRET.getBytes())
                .compact();
    }

    public Boolean validarToken(String token, UserDetails userDetails) {
        final String nombreUsuario = extraerNombreUsuario(token);
        return (nombreUsuario.equals(userDetails.getUsername()) && !esTokenExpirado(token));
    }
}
