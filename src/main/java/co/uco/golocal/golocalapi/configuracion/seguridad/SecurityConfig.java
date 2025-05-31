package co.uco.golocal.golocalapi.configuracion.seguridad;

import co.uco.golocal.golocalapi.configuracion.seguridad.filters.JwtFiltroAutenticacion;
import co.uco.golocal.golocalapi.configuracion.seguridad.service.CustomerDetalleUsuarioService;
import co.uco.golocal.golocalapi.utils.JwtUtilidad;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtUtilidad jwtUtilidad;
    private final CustomerDetalleUsuarioService customerDetalleUsuarioService;

    public SecurityConfig(JwtUtilidad jwtUtilidad, CustomerDetalleUsuarioService customerDetalleUsuarioService) {
        this.jwtUtilidad = jwtUtilidad;
        this.customerDetalleUsuarioService = customerDetalleUsuarioService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(withDefaults())
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests(authorize ->
                        authorize
                                .requestMatchers("/api/v1/rest/authentication/**").permitAll()
                                .requestMatchers("/api/v1/rest/users").permitAll()
                                .requestMatchers("/api/v1/rest/business/**").permitAll()
                                .requestMatchers("/api/v1/rest/experiences/**").permitAll()
                                .requestMatchers("/api/v1/rest/category/**").permitAll()
                                .anyRequest().authenticated()
                )
                .addFilterBefore(jwtFiltroAutenticacion(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }


    @Bean
    public JwtFiltroAutenticacion jwtFiltroAutenticacion() {
        return new JwtFiltroAutenticacion(jwtUtilidad, customerDetalleUsuarioService);
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
