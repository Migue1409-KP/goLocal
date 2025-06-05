package co.uco.golocal.golocalapi.controllers.location.city;

import co.uco.golocal.golocalapi.controllers.location.city.dto.CityDTO;
import co.uco.golocal.golocalapi.controllers.mapper.ICityMapperDTO;
import co.uco.golocal.golocalapi.controllers.support.Response;
import co.uco.golocal.golocalapi.domain.location.CityDomain;
import co.uco.golocal.golocalapi.service.location.city.CityService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/rest/cities")
public class CityController {

    private final CityService cityService;
    private final ICityMapperDTO cityMapper;

    public CityController(CityService cityService, ICityMapperDTO cityMapper) {
        this.cityService = cityService;
        this.cityMapper = cityMapper;
    }

    @GetMapping
    public ResponseEntity<List<CityDomain>> getAllCities() {
        return ResponseEntity.ok(cityService.getAllCities());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CityDomain> getCityById(@PathVariable UUID id) {
        return cityService.getCityById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Response<CityDomain>> createCity(@Valid @RequestBody CityDTO cityDTO) {
        Response<CityDomain> response = new Response<>();
        try {
            CityDomain cityDomain = cityMapper.toDomain(cityDTO);
            CityDomain createdCity = cityService.createCity(cityDomain);

            response.setStatus(HttpStatus.CREATED);
            response.setMessage("Ciudad registrada exitosamente");
            response.setData(List.of(createdCity));

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            response.setMessage("Error al registrar la ciudad: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response<Void>> deleteCity(@PathVariable UUID id) {
        Response<Void> response = new Response<>();
        try {
            cityService.deleteCity(id);

            response.setStatus(HttpStatus.OK);
            response.setMessage("Ciudad eliminada exitosamente");

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.setMessage("Error al eliminar la ciudad: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);

        }
    }
}