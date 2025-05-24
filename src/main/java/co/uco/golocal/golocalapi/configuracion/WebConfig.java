package co.uco.golocal.golocalapi.configuracion;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins("http://localhost:4200", "https://orange-ocean-018aaf80f.5.azurestaticapps.net")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH")
                .allowedHeaders("*")
                .allowCredentials(true); // Esto permite el uso de credenciales
    }
}
