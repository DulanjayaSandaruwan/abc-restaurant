package com.project.abc.controller.category;

import com.project.abc.dto.category.CategoryDTO;
import com.project.abc.model.category.Category;
import com.project.abc.service.category.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/category")
@RestController
@Slf4j
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/create-category")
    public ResponseEntity<CategoryDTO> createItem(@RequestBody CategoryDTO categoryDTO) {
        categoryDTO.validate();
        Category category = categoryService.createCategory(categoryDTO);
        CategoryDTO createCategoryDTO = CategoryDTO.init(category);
        return ResponseEntity.ok(createCategoryDTO);
    }
}
