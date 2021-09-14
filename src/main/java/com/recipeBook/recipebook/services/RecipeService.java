package com.recipeBook.recipebook.services;

import com.recipeBook.recipebook.commands.RecipeCommand;
import com.recipeBook.recipebook.domain.Recipe;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;
import java.util.Set;

public interface RecipeService {

    Flux<Recipe> getRecipes();

    Mono<Recipe> findById(String l);

    Mono<RecipeCommand> saveRecipeCommand(RecipeCommand command);

    Mono<RecipeCommand> findCommandById(String valueOf);

    Mono<Void> deleteById(String id);
}
