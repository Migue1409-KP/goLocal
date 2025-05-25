package co.uco.golocal.golocalapi.controllers.business;

import co.uco.golocal.golocalapi.domain.business.BusinessDomain;
import co.uco.golocal.golocalapi.service.business.BusinessService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
            businessService.registerBusiness(businessDomain);
        return ResponseEntity.status(HttpStatus.CREATED).body("Business registered successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
