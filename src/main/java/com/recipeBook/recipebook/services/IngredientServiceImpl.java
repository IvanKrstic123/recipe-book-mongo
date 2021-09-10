package com.recipeBook.recipebook.services;

import com.recipeBook.recipebook.commands.IngredientCommand;
import com.recipeBook.recipebook.converters.IngredientCommandToIngredient;
import com.recipeBook.recipebook.converters.IngredientToIngredientCommand;
import com.recipeBook.recipebook.converters.UnitOfMeasureCommandToUnitOfMeasure;
import com.recipeBook.recipebook.domain.Ingredient;
import com.recipeBook.recipebook.domain.Recipe;
import com.recipeBook.recipebook.repositories.RecipeRepository;
import com.recipeBook.recipebook.repositories.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService {

    private final RecipeRepository recipeRepository;
    private final UnitOfMeasureRepository uomRepository;
    private final IngredientToIngredientCommand ingredientToIngredientCommand;
    private final IngredientCommandToIngredient ingredientCommandToIngredient;
    private final UnitOfMeasureCommandToUnitOfMeasure unitOfMeasureCommandToUnitOfMeasure;

    public IngredientServiceImpl(RecipeRepository repository, UnitOfMeasureRepository uomRepository, IngredientToIngredientCommand converter, IngredientCommandToIngredient ingredientCommandToIngredient, UnitOfMeasureCommandToUnitOfMeasure converter1) {
        this.recipeRepository = repository;
        this.uomRepository = uomRepository;
        this.ingredientToIngredientCommand = converter;
        this.ingredientCommandToIngredient = ingredientCommandToIngredient;
        this.unitOfMeasureCommandToUnitOfMeasure = converter1;
    }

    @Override
    public IngredientCommand findByRecipeIdAndIngredientId(String recipeId, String ingredientId) {

        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);

        if (!recipeOptional.isPresent()) {
            log.debug("Recipe Not Found!");
        }

        Recipe recipe = recipeOptional.get();

        Optional<IngredientCommand> ingredientCommandOptional = recipe.getIngredients().stream()
                .filter(ingredient -> ingredient.getId().equals(ingredientId))
                .map(ingredient -> ingredientToIngredientCommand.convert(ingredient))
                .findFirst();

        if (!ingredientCommandOptional.isPresent()) {
            log.debug("Ingredient Not Found!");
        }

        return ingredientCommandOptional.get();
    }

    @Override
    @Transactional
    public IngredientCommand saveIngredientCommand(IngredientCommand command) {

        Optional<Recipe> recipeOptional = recipeRepository.findById(command.getRecipeId());

        if (!recipeOptional.isPresent()) {
            log.error("Recipe not found!");
            return new IngredientCommand();
        } else {
            Recipe recipe = recipeOptional.get();

            Optional<Ingredient> ingredientOptional = recipe.getIngredients().stream()
                    .filter(ingredient -> ingredient.getId().equals(command.getId()))
                    .findFirst();

            if (ingredientOptional.isPresent()) {
                for (Ingredient temp : recipe.getIngredients()) {
                    if (temp.getId().equals(command.getId())) {
                        temp.setDescription(command.getDescription());
                        temp.setAmount(command.getAmount());
                        temp.setUnitOfMeasure(unitOfMeasureCommandToUnitOfMeasure.convert(command.getUom()));
                    }
                }
            } else {
                recipe.addIngredient(ingredientCommandToIngredient.convert(command));
            }

            Recipe savedRecipe = recipeRepository.save(recipe);

            if(!ingredientOptional.isPresent()){
                //not totally safe... But best guess
                ingredientOptional = savedRecipe.getIngredients().stream()
                        .filter(recipeIngredients -> recipeIngredients.getDescription().equals(command.getDescription()))
                        .filter(recipeIngredients -> recipeIngredients.getAmount().equals(command.getAmount()))
                        .filter(recipeIngredients -> recipeIngredients.getUnitOfMeasure().getId().equals(command.getUom().getId()))
                        .findFirst();
            }

            //to do check for fail
            return ingredientToIngredientCommand.convert(ingredientOptional.get());

        }
    }

    @Override
    public void deleteById(String recipeId, String ingredientId) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);

        if (recipeOptional.isPresent()) {
            Recipe recipe = recipeOptional.get();

            Optional<Ingredient> ingredientOptional = recipe.getIngredients()
                    .stream()
                    .filter(ingredient -> ingredient.getId().equals(ingredientId))
                    .findFirst();

            if (ingredientOptional.isPresent()) {
                Ingredient ingredientToDelete = ingredientOptional.get();
                /*ingredientToDelete.setRecipe(null); */ //needed to cause hibernate to delete relationship
                recipe.getIngredients().remove(ingredientOptional.get());
                recipeRepository.save(recipe);
            }
        } else {
            log.debug("Recipe not found!");
        }
    }
}

