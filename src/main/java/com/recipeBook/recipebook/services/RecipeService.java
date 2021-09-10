package com.recipeBook.recipebook.services;

import com.recipeBook.recipebook.commands.RecipeCommand;
import com.recipeBook.recipebook.domain.Recipe;

import java.util.Optional;
import java.util.Set;

public interface RecipeService {

    Set<Recipe> getRecipes();

    Recipe findById(String l);

    RecipeCommand saveRecipeCommand(RecipeCommand command);

    RecipeCommand findCommandById(String valueOf);

    void deleteById(String id);
}
