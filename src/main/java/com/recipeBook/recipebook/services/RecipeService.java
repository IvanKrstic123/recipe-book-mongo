package com.recipeBook.recipebook.services;

import com.recipeBook.recipebook.commands.RecipeCommand;
import com.recipeBook.recipebook.domain.Recipe;

import java.util.Optional;
import java.util.Set;

public interface RecipeService {

    Set<Recipe> getRecipes();

    Recipe findById(Long l);

    RecipeCommand saveRecipeCommand(RecipeCommand command);

    RecipeCommand findCommandById(Long valueOf);

    void deleteById(Long id);
}
