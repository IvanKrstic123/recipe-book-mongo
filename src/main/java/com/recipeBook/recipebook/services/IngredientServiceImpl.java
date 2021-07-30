package com.recipeBook.recipebook.services;

import com.recipeBook.recipebook.commands.IngredientCommand;
import com.recipeBook.recipebook.converters.IngredientToIngredientCommand;
import com.recipeBook.recipebook.domain.Recipe;
import com.recipeBook.recipebook.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Stream;

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService{

    private final RecipeRepository repository;
    private final IngredientToIngredientCommand converter;

    public IngredientServiceImpl(RecipeRepository repository, IngredientToIngredientCommand converter) {
        this.repository = repository;
        this.converter = converter;
    }

    @Override
    public IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId) {

        Optional<Recipe> recipeOptional = repository.findById(recipeId);

        if (!recipeOptional.isPresent()) {
            log.debug("Recipe Not Found!");
        }

        Recipe recipe = recipeOptional.get();

        Optional<IngredientCommand> ingredientCommandOptional = recipe.getIngredients().stream()
                .filter(ingredient -> ingredient.getId() == ingredientId)
                .map(ingredient -> converter.convert(ingredient))
                .findFirst();

        if (!ingredientCommandOptional.isPresent()) {
            log.debug("Ingredient Not Found!");
        }

        return ingredientCommandOptional.get();
    }
}
