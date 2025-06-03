package co.uco.golocal.golocalapi.controllers.review.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDTO {

    private UUID id;

    @NotNull(message = "El ID del usuario es obligatorio")
    private UUID userId;

    @NotNull(message = "La calificación es obligatoria")
    @Min(value = 0, message = "La calificación mínima es 0")
    @Max(value = 5, message = "La calificación máxima es 5")
    private Double rating;

    @Size(max = 250, message = "La descripción no puede exceder los 250 caracteres")
    @Pattern(regexp = "^[a-zA-Z0-9áéíóúÁÉÍÓÚñÑ\\s.,;:¡!¿?-]+$",
             message = "La descripción contiene caracteres no permitidos")
    private String description;

    private UUID experienceId;

    private UUID routeId;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @AssertTrue(message = "La reseña debe estar asociada a una ruta o a una experiencia, no a ambas o ninguna")
    private boolean validateAssociation() {
        // Verifica si exactamente uno de los dos tiene valor
        boolean hasExperience = experienceId != null;
        boolean hasRoute = routeId != null;

        return (hasExperience && !hasRoute) || (!hasExperience && hasRoute);
    }
}