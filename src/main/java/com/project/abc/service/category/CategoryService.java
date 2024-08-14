package com.project.abc.service.category;

import com.project.abc.commons.Hash;
import com.project.abc.commons.exceptions.http.BadRequestException;
import com.project.abc.commons.exceptions.user.UserExType;
import com.project.abc.dto.category.CategoryDTO;
import com.project.abc.dto.user.UserDTO;
import com.project.abc.model.category.Category;
import com.project.abc.model.user.User;
import com.project.abc.repository.category.CategoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Slf4j
@Transactional
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public Category createCategory(CategoryDTO dto) {
        log.info("create category name {}", dto.getCategoryName());
        Category category = Category.init(dto);
        if (categoryRepository.findByCategoryNameAndStatusNot(dto.getCategoryName() , Category.CategoryStatus.DELETED).isPresent()) {
            throw new BadRequestException("email already exist", UserExType.EMAIL_ALREADY_EXIST);
        }
        return categoryRepository.save(category);
    }
}
