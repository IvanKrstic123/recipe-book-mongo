package com.recipeBook.recipebook.services;

import com.recipeBook.recipebook.domain.Recipe;

import java.util.Set;

public interface RecipeService {

    Set<Recipe> getRecipes();
}
