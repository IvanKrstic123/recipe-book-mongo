package com.recipeBook.recipebook.repositories;

import com.recipeBook.recipebook.domain.Category;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<Category, Long> {
}
