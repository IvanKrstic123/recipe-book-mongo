package com.recipeBook.recipebook.repositories.reactive;

import com.recipeBook.recipebook.domain.Recipe;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface RecipeReactiveRepository extends ReactiveMongoRepository<Recipe, String> {
}
