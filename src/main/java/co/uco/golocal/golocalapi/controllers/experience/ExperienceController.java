package co.uco.golocal.golocalapi.controllers.experience;

import co.uco.golocal.golocalapi.controllers.experience.dto.ExperienceRequestDTO;
import co.uco.golocal.golocalapi.controllers.mapper.IExperienceMapperDTO;
import co.uco.golocal.golocalapi.controllers.support.Response;
import co.uco.golocal.golocalapi.data.entity.experience.ExperienceEntity;
import co.uco.golocal.golocalapi.domain.experiences.ExperienceDomain;
import co.uco.golocal.golocalapi.service.experience.ExperienceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/rest/experiences")
public class ExperienceController {
    private final ExperienceService experienceService;
    private final IExperienceMapperDTO experienceMapper;

    public ExperienceController(ExperienceService experienceService, IExperienceMapperDTO experienceMapper) {
        this.experienceService = experienceService;
        this.experienceMapper = experienceMapper;
    }
    @GetMapping("/dummy")
    public ExperienceDomain getDummy() {
        return new ExperienceDomain();
    }

    @PostMapping
    public ResponseEntity<Response<String>> createExperience(@Valid @RequestBody ExperienceRequestDTO experienceRequestDTO) {
        Response<String> response = new Response<>();
        try {
            ExperienceDomain experienceDomain = experienceMapper.toDomain(experienceRequestDTO);
            experienceService.createExperience(experienceDomain);
            response.setStatus(HttpStatus.CREATED);
            response.setMessage("Experiencia creada exitosamente");
            response.setData(List.of("OK"));
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            response.setMessage("Error creando experiencia: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<ExperienceDomain>> getExperienceById(@PathVariable UUID id) {
        Response<ExperienceDomain> response = new Response<>();
        try {
            Optional<ExperienceEntity> experienceEntity = experienceService.getExperienceById(id);
            if (experienceEntity.isPresent()) {
                ExperienceDomain experienceDomain = experienceService.getBusinessMapper().toDomain(experienceEntity.get());
                response.setStatus(HttpStatus.OK);
                response.setMessage("Experiencia encontrada exitosamente");
                response.setData(List.of(experienceDomain));

                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                response.setStatus(HttpStatus.NOT_FOUND);
                response.setMessage("Experiencia no encontrada");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
        }catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setMessage("Error al recuperar la experiencia: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/list")
    public ResponseEntity<List<ExperienceDomain>> getAllExperiencesInList() {
        List<ExperienceDomain> experiences = experienceService.getAllExperiencesInList();
        return ResponseEntity.ok(experiences);
    }

    @GetMapping
    public ResponseEntity<Response<ExperienceDomain>> getAllExperiences(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ExperienceDomain> experienceDomainPage = experienceService.getAllExperiences(pageable);

        Response<ExperienceDomain> response = new Response<>();
        response.setStatus(HttpStatus.OK);
        response.setData(experienceDomainPage.getContent());
        response.setMessage("Negocios listados exitosamente");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response<String>> deleteExperience(@PathVariable UUID id) {
        Response<String> response = new Response<>();
        try {
            experienceService.deleteExperience(id);
            response.setStatus(HttpStatus.OK);
            response.setMessage("Experiencia eliminada correctamente");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            response.setMessage("Error eliminando  experiencia: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

}

