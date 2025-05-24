package co.uco.golocal.golocalapi.domain.usuario;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDomain {

    private UUID id;
    private String nombre;
    private String apellido;
    private String tipoIdentificacion;
    private String identificacion;
    private String contrasena;
    private String correo;
    private String rol;
    private String ciudad;
    private String direccion;
    private String correoEmergencia;
    private String numeroContacto;


    public String toString() {
        return "Usuario{" +
                "id=" + id +
                "\n nombre='" + nombre + '\'' +
                ",\n apellido='" + apellido + '\'' +
                ",\n tipoId='" + tipoIdentificacion + '\'' +
                ",\n numeroId='" + identificacion + '\'' +
                ",\n correo='" + correo + '\'' +
                ",\n rol=" + rol +
                ",\n ciudad='" + ciudad + '\'' +
                ",\n direccion='" + direccion + '\'' +
                ",\n correoEmergencia='" + correoEmergencia + '\'' +
                ",\n numeroContacto='" + numeroContacto + '\'' +
                '}';
    }
}

