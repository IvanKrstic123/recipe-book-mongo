package com.recipeBook.recipebook.services;

import com.recipeBook.recipebook.commands.IngredientCommand;
import com.recipeBook.recipebook.commands.UnitOfMeasureCommand;
import com.recipeBook.recipebook.converters.IngredientCommandToIngredient;
import com.recipeBook.recipebook.converters.IngredientToIngredientCommand;
import com.recipeBook.recipebook.converters.UnitOfMeasureCommandToUnitOfMeasure;
import com.recipeBook.recipebook.converters.UnitOfMeasureToUnitOfMeasureCommand;
import com.recipeBook.recipebook.domain.Ingredient;
import com.recipeBook.recipebook.domain.Recipe;
import com.recipeBook.recipebook.repositories.UnitOfMeasureRepository;
import com.recipeBook.recipebook.repositories.reactive.RecipeReactiveRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class IngredientServiceImplTest {

    private static final String RECIPE_ID = "1";
    private static final String INGREDIENT1_ID = "1";
    private static final String INGREDIENT2_ID = "2";

    private final IngredientToIngredientCommand ingredientToIngredientCommand = new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand());
    private final IngredientCommandToIngredient ingredientCommandToIngredient = new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure());

    @Mock
    RecipeReactiveRepository recipeReactiveRepository;

    @Mock
    UnitOfMeasureRepository uomRepository;

    IngredientService ingredientService;

    @BeforeEach
    void setUp() {
        recipeReactiveRepository = Mockito.mock(RecipeReactiveRepository.class);
        uomRepository = Mockito.mock(UnitOfMeasureRepository.class);

        ingredientService = new IngredientServiceImpl(recipeReactiveRepository, uomRepository, ingredientToIngredientCommand, ingredientCommandToIngredient, new UnitOfMeasureCommandToUnitOfMeasure());
    }

    @Test
    void findByRecipeIdAndIngredientIdHappyPath() {

        //given
        Recipe recipe = new Recipe();
        recipe.setId(RECIPE_ID);

        Ingredient ingredient1 = new Ingredient();
        ingredient1.setId(INGREDIENT1_ID);

        Ingredient ingredient2 = new Ingredient();
        ingredient2.setId(INGREDIENT2_ID);

        // add ingredients to recipe
        recipe.addIngredient(ingredient1);
        recipe.addIngredient(ingredient2);

        when(recipeReactiveRepository.findById(anyString())).thenReturn(Mono.just(recipe));

        IngredientCommand ingredientCommand = ingredientService.findByRecipeIdAndIngredientId("1", "2").block();

        //when
        assertEquals("2", ingredientCommand.getId());
        verify(recipeReactiveRepository, times(1)).findById(anyString());
    }

    @Test
    void saveIngredientCommand() {
        UnitOfMeasureCommand unitOfMeasureCommand = new UnitOfMeasureCommand();
        unitOfMeasureCommand.setId("1");

        IngredientCommand command = new IngredientCommand();
        command.setId("1");
        command.setRecipeId("2");
        command.setDescription("teaspoon");
        command.setAmount(BigDecimal.valueOf(5));
        command.setUom(unitOfMeasureCommand);

        //what will be returned
        Recipe savedRecipe = new Recipe();
        savedRecipe.addIngredient(ingredientCommandToIngredient.convert(command));

        when(recipeReactiveRepository.findById(anyString())).thenReturn(Mono.just(new Recipe()));
        when(recipeReactiveRepository.save(any())).thenReturn(Mono.just(savedRecipe));

        IngredientCommand savedCommand = ingredientService.saveIngredientCommand(command).block();

        assertEquals("1", savedCommand.getId());
        verify(recipeReactiveRepository, times(1)).findById(anyString());
        verify(recipeReactiveRepository, times(1)).save(any(Recipe.class));
    }

    @Test
    void deleteIngredient() {
        Ingredient ingredient = new Ingredient();
        ingredient.setId("1");
        ingredient.setDescription("teaspoon");
        ingredient.setAmount(BigDecimal.valueOf(5));

        //what will be returned
        Recipe recipe = new Recipe();
        recipe.setId("2");
        recipe.addIngredient(ingredient);
//        ingredient.setRecipe(recipe);

        when(recipeReactiveRepository.findById(anyString())).thenReturn(Mono.just(recipe));
        when(recipeReactiveRepository.save(any())).thenReturn(Mono.just(recipe));

        ingredientService.deleteById("2","1");

        verify(recipeReactiveRepository, times(1)).findById(anyString());
        verify(recipeReactiveRepository, times(1)).save(any(Recipe.class));

    }
}