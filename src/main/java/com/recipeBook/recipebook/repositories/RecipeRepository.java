package com.recipeBook.recipebook.repositories;

import com.recipeBook.recipebook.domain.Recipe;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

public interface RecipeRepository extends CrudRepository<Recipe, String> {
}
