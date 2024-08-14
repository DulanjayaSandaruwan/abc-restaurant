package com.project.abc.repository.category;

import com.project.abc.model.category.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, String> {

    Optional<Category> findByCategoryNameAndStatusNot(String name, Category.CategoryStatus status);
}
