package co.uco.golocal.golocalapi.configuracion.seguridad.filters;

import co.uco.golocal.golocalapi.configuracion.seguridad.service.CustomerDetalleUsuarioService;
import co.uco.golocal.golocalapi.utils.JwtUtilidad;
import co.uco.golocal.golocalapi.utils.UtilidadObjecto;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;


import java.io.IOException;

@Service
public class JwtFiltroAutenticacion extends   OncePerRequestFilter{

    private final JwtUtilidad jwtUtilidad;
    private final CustomerDetalleUsuarioService customerDetalleUsuarioService;

    public JwtFiltroAutenticacion(JwtUtilidad jwtUtilidad, CustomerDetalleUsuarioService customerDetalleUsuarioService) {
        this.jwtUtilidad = jwtUtilidad;
        this.customerDetalleUsuarioService = customerDetalleUsuarioService;
    }

    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String authorizationHeader = request.getHeader("Authorization");
        String username = null;
        String jwt = null;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {

            jwt = authorizationHeader.substring(7);
            username = jwtUtilidad.extraerNombreUsuario(jwt);

        }

        if (!UtilidadObjecto.esNulo(username) && UtilidadObjecto.esNulo(SecurityContextHolder.getContext().getAuthentication())) {
            UserDetails userDetails = this.customerDetalleUsuarioService.loadUserByUsername(username);
            if (jwtUtilidad.validarToken(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        filterChain.doFilter(request, response);
    }
}
