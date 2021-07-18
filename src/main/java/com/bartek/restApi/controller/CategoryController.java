package com.bartek.restApi.controller;

import com.bartek.restApi.logic.CategoryService;
import com.bartek.restApi.model.Category;
import com.bartek.restApi.model.projection.CategoryReadModel;
import com.bartek.restApi.model.projection.CategoryWriteModel;
import com.bartek.restApi.repository.CategoryRepository;
import com.bartek.restApi.repository.DiscoveryRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryRepository categoryRepository;
    private final DiscoveryRepository discoveryRepository;
    private final CategoryService categoryService;

    public CategoryController(CategoryRepository categoryRepository, DiscoveryRepository discoveryRepository, CategoryService categoryService) {
        this.categoryRepository = categoryRepository;
        this.discoveryRepository = discoveryRepository;
        this.categoryService = categoryService;
    }

    @GetMapping
    ResponseEntity<List<CategoryReadModel>> readAllCategorys (){
        return  ResponseEntity.ok(categoryService.readAll());
    }

    @PostMapping
    ResponseEntity<CategoryReadModel> createGroup (@RequestBody @Valid CategoryWriteModel toCreate){
        CategoryReadModel result = categoryService.createCategory(toCreate);
        return ResponseEntity.created(URI.create("/"+result.getId())).body(result);
    }

    @GetMapping("/{id}")
    ResponseEntity<?> findCategoryById(@PathVariable long id){
        if (categoryRepository.findById(id).isPresent()){
            return ResponseEntity.ok(discoveryRepository.findById(id));
        }
        return ResponseEntity.notFound().build();

    }
}
