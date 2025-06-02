package co.uco.golocal.golocalapi.controllers.category.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryDTO {

    @NotBlank(message = "El nombre de una categoria es obligatorio.")
    @Size(min = 1, max = 25, message = "El nombre de una categoria debe tener entre 1 y 25 caracteres.")
    @Pattern(regexp = "^[\\p{L}\\p{N} .,'\"!?¿¡()@#&$%+-]*$", message = "Una categoria  solo puede contener letras, dígitos, espacios y caracteres especiales.")
    private String name;


}
