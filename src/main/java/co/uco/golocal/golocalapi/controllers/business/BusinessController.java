package co.uco.golocal.golocalapi.controllers.business;

import co.uco.golocal.golocalapi.controllers.business.dto.BusinessRequestDTO;
import co.uco.golocal.golocalapi.controllers.mapper.IBusinessMapperDTO;
import co.uco.golocal.golocalapi.controllers.support.Response;
import co.uco.golocal.golocalapi.data.entity.business.BusinessEntity;
import co.uco.golocal.golocalapi.domain.business.BusinessDomain;
import co.uco.golocal.golocalapi.domain.business.exception.BusinessNameAlreadyExistsException;
import co.uco.golocal.golocalapi.domain.business.exception.DoesntBussinesExistIdException;
import co.uco.golocal.golocalapi.service.business.BusinessService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
@Validated
@RequestMapping("/api/v1/rest/business")
public class BusinessController {
    private final BusinessService businessService;
    private final IBusinessMapperDTO businessMapper;


    public BusinessController(BusinessService businessService, IBusinessMapperDTO businessMapper) {
        super();
        this.businessService = businessService;
        this.businessMapper = businessMapper;
    }

    @GetMapping("/dummy")
    public BusinessDomain getDummy() {
        return new BusinessDomain();
    }

    @PostMapping
    public ResponseEntity<Response<String>> createBusiness(@Valid @RequestBody BusinessRequestDTO businessRequestDTO) {
        Response<String> response = new Response<>();

        try {
            BusinessDomain domain = businessMapper.toDomain(businessRequestDTO);
            businessService.createBusiness(domain);

            response.setStatus(HttpStatus.CREATED);
            response.setMessage("Negocio registrado exitosamente");
            response.setData(List.of("OK"));

            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (BusinessNameAlreadyExistsException e) {
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        } catch (Exception e) {
            response.setMessage("Error inesperado al registrar el negocio: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }


    @GetMapping("/{id}")
    public ResponseEntity<Response<BusinessDomain>> getBusinessById(@PathVariable UUID id) {
        Response<BusinessDomain> response = new Response<>();
        try {
            Optional<BusinessEntity> optional = businessService.getBusinessById(id);
            BusinessDomain domain = businessService.getBusinessMapper().toDomain(optional.get());

            response.setStatus(HttpStatus.OK);
            response.setMessage("Negocio encontrado correctamente");
            response.setData(List.of(domain));

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (DoesntBussinesExistIdException e) {
            response.setStatus(HttpStatus.BAD_REQUEST);
            response.setMessage(e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setMessage("Error al recuperar el negocio");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
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
    public ResponseEntity<Response<BusinessDomain>> updateBusiness(
            @PathVariable UUID id,
            @RequestBody Map<String, Object> updates) {

        Response<BusinessDomain> response = new Response<>();

        try {
            BusinessDomain updated = businessService.updatePartial(id, updates);

            response.setStatus(HttpStatus.OK);
            response.setData(List.of(updated));
            response.setMessage("Negocio actualizado correctamente");

            return ResponseEntity.ok(response);

        } catch (IllegalArgumentException | IllegalStateException e) {
            response.setStatus(HttpStatus.BAD_REQUEST);
            response.setMessage("Datos inválidos: " + e.getMessage());
        } catch (Exception e) {
            response.setStatus(HttpStatus.NOT_FOUND);
            response.setMessage("No se pudo actualizar el negocio: " + e.getMessage());
        }

        return ResponseEntity.status(response.getStatus()).body(response);
    }


    @GetMapping
    public ResponseEntity<Response<BusinessDomain>> getAllBusinesses(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<BusinessDomain> businessPage = businessService.getAllBusinesses(pageable);

        Response<BusinessDomain> response = new Response<>();
        response.setStatus(HttpStatus.OK);
        response.setData(businessPage.getContent());
        response.setMessage("Negocios listados exitosamente");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Response<Void>> deleteBusiness(@PathVariable UUID id) {
        Response<Void> response = new Response<>();

        try {
            businessService.deleteBusiness(id);
            response.setStatus(HttpStatus.OK);
            response.setMessage("negocio eliminado correctamente");
            return ResponseEntity.status(HttpStatus.OK).body(response);

        } catch (Exception e) {
            response.setStatus(HttpStatus.NOT_FOUND);
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @GetMapping("/filter")
    public ResponseEntity<Response<BusinessDomain>> filterBusinesses(
            @RequestParam(required = false) UUID cityId,
            @RequestParam(required = false) UUID stateId,
            @RequestParam(required = false) List<UUID> categoryIds,
            Pageable pageable) {

        var response = new Response<BusinessDomain>();
        try {
            Page<BusinessDomain> filtered = businessService.filterBusinesses(cityId, stateId, categoryIds, pageable);
            response.setStatus(HttpStatus.OK);
            response.setData(filtered.getContent());
            response.setMessage("Negocios filtrados correctamente");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setMessage("Error al filtrar negocios: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }


}