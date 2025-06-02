package co.uco.golocal.golocalapi.controllers.category.support.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

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

    @NotBlank(message="El precio de una experiencia no puede ser vacio")
    @Pattern(regexp = "^[0-9]+(\\.[0-9]{1,2})?$", message = "El precio de la experiencia debe ser un número válido con hasta dos decimales.")
    private String price;
}
