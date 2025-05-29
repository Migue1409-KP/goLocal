package co.uco.golocal.golocalapi.controllers.experience;

import co.uco.golocal.golocalapi.data.entity.experience.ExperienceEntity;
import co.uco.golocal.golocalapi.domain.business.BusinessDomain;
import co.uco.golocal.golocalapi.domain.experiences.ExperienceDomain;
import co.uco.golocal.golocalapi.service.experience.ExperienceService;
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

    public ExperienceController(ExperienceService experienceService) {
        this.experienceService = experienceService;
    }

    @PostMapping
    public ResponseEntity<?> createExperience(@RequestBody ExperienceDomain experienceDomain) {
        try {
            experienceService.createExperience(experienceDomain);
            return ResponseEntity.status(HttpStatus.CREATED).body("Experience created successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error creating experience: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getExperienceById(@PathVariable UUID id) {
        try {
            Optional<ExperienceEntity> experienceEntity = experienceService.getExperienceById(id);
            if (experienceEntity.isPresent()) {
                ExperienceDomain experienceDomain = experienceService.getBusinessMapper().toDomain(experienceEntity.get());
                return ResponseEntity.ok(experienceDomain);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Experience not found");
            }
        }catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error retrieving experience: " + e.getMessage());
            }
    }

    @GetMapping
    public ResponseEntity<List<ExperienceDomain>> getAllExperiences() {
        List<ExperienceDomain> experiences = experienceService.getAllExperiences();
        return ResponseEntity.ok(experiences);
    }
}

