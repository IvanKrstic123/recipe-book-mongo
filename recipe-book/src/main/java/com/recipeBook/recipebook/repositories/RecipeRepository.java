package com.recipeBook.recipebook.repositories;

import com.recipeBook.recipebook.domain.Recipe;
import org.springframework.data.repository.CrudRepository;


public interface RecipeRepository extends CrudRepository<Recipe, Long> {
}
