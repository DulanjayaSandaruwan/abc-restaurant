package com.project.abc.controller.category;

import com.project.abc.dto.category.CategoryDTO;
import com.project.abc.dto.category.UpdateCategoryDTO;
import com.project.abc.model.category.Category;
import com.project.abc.service.category.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/category")
@RestController
@Slf4j
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/create-category")
    public ResponseEntity<CategoryDTO> createCategory(@RequestBody CategoryDTO categoryDTO) {
        categoryDTO.validate();
        Category category = categoryService.createCategory(categoryDTO);
        CategoryDTO createCategoryDTO = CategoryDTO.init(category);
        return ResponseEntity.ok(createCategoryDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable String id) {
        Category category = categoryService.getCategoryById(id);
        CategoryDTO categoryDTO = CategoryDTO.init(category);
        return ResponseEntity.ok(categoryDTO);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<CategoryDTO> updateCategory(
            @RequestBody UpdateCategoryDTO updateCategoryDTO,
            @PathVariable String id
    ) {
        updateCategoryDTO.validate();
        Category category = categoryService.updateCategory(updateCategoryDTO, id);
        CategoryDTO categoryDTO = CategoryDTO.init(category);
        return ResponseEntity.ok(categoryDTO);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<CategoryDTO> deleteCategory(@PathVariable String id) {
        Category category = categoryService.deleteCategory(id);
        return ResponseEntity.ok(CategoryDTO.init(category));
    }
}
