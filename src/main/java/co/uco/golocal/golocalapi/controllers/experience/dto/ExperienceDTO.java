package co.uco.golocal.golocalapi.controllers.experience.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExperienceRequestDTO {

    @NotBlank(message="El nombre de una experiencia no puede ser vacio")
    @Size(max=100, message="El nombre de una experiencia no puede tener mas de 100 caracteres")
    @Pattern(regexp = "^[\\p{L}\\p{N} .,'\"!?¿¡()@#&$%+-]*$", message = "El nombre de la  experiencia solo puede contener letras, dígitos, espacios y caracteres especiales.")
    private String name;

    @NotBlank(message="La descripcion de una experiencia no puede ser vacia")
    @Size(max=500, message="El nombre de una experiencia no puede tener mas de 500 caracteres")
    private String description;

    @NotNull(message = "El precio de una experiencia no puede ser nulo")
    @DecimalMin(value = "0.0", inclusive = false, message = "El precio debe ser mayor a 0")
    private Double price;

    @NotNull(message = "El ID del negocio es obligatorio.")
    private UUID businessId;

    @NotNull(message = "El ID de la categoría es obligatorio.")
    private UUID categoryId;
}
