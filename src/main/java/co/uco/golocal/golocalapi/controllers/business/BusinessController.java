package co.uco.golocal.golocalapi.controllers.business;

import co.uco.golocal.golocalapi.data.entity.business.BusinessEntity;
import co.uco.golocal.golocalapi.domain.business.BusinessDomain;
import co.uco.golocal.golocalapi.service.business.BusinessService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/rest/business")
public class BusinessController {
    private final BusinessService businessService;


    public BusinessController(BusinessService businessService) {
        super();
        this.businessService = businessService;
    }

    @GetMapping("/dummy")
    public BusinessDomain getDummy() {
        return new BusinessDomain();
    }

    @PostMapping
    public ResponseEntity<String> createBusiness(@RequestBody BusinessDomain businessDomain) {
        try {
            businessService.createBusiness(businessDomain);
            return ResponseEntity.status(HttpStatus.CREATED).body("Business registered successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getBusinessesById(@PathVariable UUID id) {
        try {
            Optional<BusinessEntity> businessEntity = businessService.getBusinessById(id);
            if (businessEntity.isPresent()) {
                BusinessDomain domain = businessService.getBusinessMapper().toDomain(businessEntity.get());
                return ResponseEntity.ok(domain);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Business not found");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error retrieving business");
        }
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<?> getBusinessesByName(@PathVariable String name) {
        try {
            Optional<BusinessEntity> businessEntity = businessService.getBusinessByName(name);
            if (businessEntity.isPresent()) {
                BusinessDomain domain = businessService.getBusinessMapper().toDomain(businessEntity.get());
                return ResponseEntity.ok(domain);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("This name is not matched with any business.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error retrieving business");
        }
    }


    @PatchMapping("/{id}")
    public ResponseEntity<BusinessDomain> updateBusiness(@PathVariable UUID id, @RequestBody Map<String, Object> updates) {
        try {
            BusinessDomain businessDomain = businessService.updatePartial(id, updates);
            return ResponseEntity.ok(businessDomain);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);

        }
    }
    @GetMapping
    public ResponseEntity<Page<BusinessDomain>> getAllBusinesses(@RequestParam(defaultValue ="0") int page,
                                                                 @RequestParam(defaultValue = "10") int size,
                                                                 @RequestParam(defaultValue = "name") String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        Page<BusinessDomain> businessDomainPage = businessService.getAllBusinesses(pageable);
        return ResponseEntity.ok(businessDomainPage);
    }

}