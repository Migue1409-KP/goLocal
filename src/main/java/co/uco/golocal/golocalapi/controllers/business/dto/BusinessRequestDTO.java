package co.uco.golocal.golocalapi.controllers.business.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class BusinessRequestDTO {

    @NotBlank(message = "El nombre del negocio es obligatorio.")
    @Size(min = 1, max = 30, message = "El nombre del negocio debe tener entre 1 y 30 caracteres.")
    @Pattern(regexp = "^[\\p{L}\\p{N} .,'\"!?¿¡()@#&$%+-]*$", message = "El nombre del negocio solo puede contener letras, dígitos, espacios y caracteres especiales.")
    private String name;

    @NotBlank(message = "La descripción del negocio es obligatoria.")
    @Size(min = 1, max = 500, message = "La descripción del negocio debe tener entre 1 y 500 caracteres.")
    private String description;

    @NotNull(message = "La ubicación del negocio es obligatoria.")
    private UUID locationId;

    @NotNull(message = "El ID del usuario es obligatorio.")
    private UUID userId;

    @NotNull(message = "Debe proporcionar al menos una categoría.")
    @Size(min = 1, message = "Debe proporcionar al menos una categoría.")
    private List<UUID> categories;

}
