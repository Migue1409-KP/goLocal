package co.uco.golocal.golocalapi.controllers.route.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RouteDTO {

    private UUID id;

    @NotBlank(message = "El nombre de la ruta es obligatorio.")
    @Size(min = 1, max = 50, message = "El nombre de la ruta debe tener entre 1 y 50 caracteres.")
    @Pattern(regexp = "^[\\p{L}\\p{N} .,'\"!?¿¡()@#&$%+-]*$", message = "El nombre de la ruta solo puede contener letras, dígitos, espacios y caracteres especiales.")
    private String name;

    private List<UUID> categoryId;

    private List<UUID> experienceId;
}