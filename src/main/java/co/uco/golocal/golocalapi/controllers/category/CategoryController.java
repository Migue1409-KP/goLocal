package co.uco.golocal.golocalapi.controllers.category;

import co.uco.golocal.golocalapi.controllers.category.dto.CategoryDTO;
import co.uco.golocal.golocalapi.controllers.mapper.ICategoryMapperDTO;
import co.uco.golocal.golocalapi.controllers.support.Response;
import co.uco.golocal.golocalapi.data.entity.category.CategoryEntity;
import co.uco.golocal.golocalapi.data.entity.experience.ExperienceEntity;
import co.uco.golocal.golocalapi.domain.category.CategoryDomain;
import co.uco.golocal.golocalapi.domain.experiences.ExperienceDomain;
import co.uco.golocal.golocalapi.service.category.CategoryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@Validated
@RequestMapping("/api/v1/rest/category")
public class CategoryController {

    private final CategoryService categoryService;
    private final ICategoryMapperDTO categoryMapperDTO;


    public CategoryController(CategoryService categoryService, ICategoryMapperDTO categoryMapperDTO) {
        this.categoryService = categoryService;
        this.categoryMapperDTO = categoryMapperDTO;
    }

    @GetMapping("/dummy")
    public CategoryDomain getDummy() {
        return new CategoryDomain();
    }

    @PostMapping
    public ResponseEntity<Response<String>> createCategory(@Valid @RequestBody CategoryDTO categoryDTO) {
        Response<String> response = new Response<>();
        try {
            CategoryDomain categoryDomain = categoryMapperDTO.toDomain(categoryDTO);
            categoryService.createCategory(categoryDomain);
            response.setStatus(HttpStatus.CREATED);
            response.setMessage("Categoría creada exitosamente");
            response.setData(List.of("OK"));
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            response.setMessage("Error creando categoría: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @GetMapping("/list")
    public ResponseEntity<Response<List<CategoryDomain>>> getAllCategoriesInList() {
        Response<List<CategoryDomain>> response = new Response<>();
        try {
            List<CategoryDomain> categories = categoryService.getAllCategories();
            response.setStatus(HttpStatus.OK);
            response.setMessage("Categorías obtenidas exitosamente");
            response.setData(Collections.singletonList(categories));
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.setMessage("Error obteniendo categorías: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response<String>> deleteCategory(@PathVariable UUID id) {
        Response<String> response = new Response<>();
        try {
            categoryService.deleteCategory(id);
            response.setStatus(HttpStatus.OK);
            response.setMessage("Categoria eliminada correctamente");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            response.setMessage("Error eliminando  categoria: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<CategoryDomain>> getCategoryById(@PathVariable UUID id) {
        Response<CategoryDomain> response = new Response<>();
        try {
            CategoryDomain categoryDomain = categoryService.getCategoryById(id);
            response.setStatus(HttpStatus.OK);
            response.setMessage("Categoría encontrada correctamente");
            response.setData(List.of(categoryDomain));
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.setMessage("Error obteniendo categoría: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }



}
