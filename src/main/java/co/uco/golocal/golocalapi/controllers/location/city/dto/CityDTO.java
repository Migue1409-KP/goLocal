package co.uco.golocal.golocalapi.controllers.location.city.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CityDTO {
    private UUID id;
    private String name;
    private UUID state;
}
