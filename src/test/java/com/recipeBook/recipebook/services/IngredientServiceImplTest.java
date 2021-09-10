package com.recipeBook.recipebook.services;

import com.recipeBook.recipebook.commands.IngredientCommand;
import com.recipeBook.recipebook.commands.UnitOfMeasureCommand;
import com.recipeBook.recipebook.converters.IngredientCommandToIngredient;
import com.recipeBook.recipebook.converters.IngredientToIngredientCommand;
import com.recipeBook.recipebook.converters.UnitOfMeasureCommandToUnitOfMeasure;
import com.recipeBook.recipebook.converters.UnitOfMeasureToUnitOfMeasureCommand;
import com.recipeBook.recipebook.domain.Ingredient;
import com.recipeBook.recipebook.domain.Recipe;
import com.recipeBook.recipebook.repositories.RecipeRepository;
import com.recipeBook.recipebook.repositories.UnitOfMeasureRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class IngredientServiceImplTest {

    private static final String RECIPE_ID = "1";
    private static final String INGREDIENT1_ID = "1";
    private static final String INGREDIENT2_ID = "2";

    private IngredientToIngredientCommand ingredientToIngredientCommand = new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand());
    private final IngredientCommandToIngredient ingredientCommandToIngredient = new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure());

    @Mock
    RecipeRepository recipeRepository;

    @Mock
    UnitOfMeasureRepository uomRepository;

    IngredientService ingredientService;

    @BeforeEach
    void setUp() {
        recipeRepository = Mockito.mock(RecipeRepository.class);
        uomRepository = Mockito.mock(UnitOfMeasureRepository.class);

        ingredientService = new IngredientServiceImpl(recipeRepository, uomRepository, ingredientToIngredientCommand, ingredientCommandToIngredient, new UnitOfMeasureCommandToUnitOfMeasure());
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

        // mock data
        Optional<Recipe> optionalRecipe = Optional.of(recipe);

        when(recipeRepository.findById(anyString())).thenReturn(optionalRecipe);

        IngredientCommand ingredientCommand = ingredientService.findByRecipeIdAndIngredientId("1", "2");

        //when
        assertEquals("2", ingredientCommand.getId());
        assertEquals("1", ingredientCommand.getRecipeId());
        verify(recipeRepository, times(1)).findById(anyString());
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

        when(recipeRepository.findById(any())).thenReturn(Optional.of(new Recipe()));
        when(recipeRepository.save(any())).thenReturn(savedRecipe);

        IngredientCommand savedCommand = ingredientService.saveIngredientCommand(command);

        assertEquals("1", savedCommand.getId());
        verify(recipeRepository, times(1)).findById(any());
        verify(recipeRepository, times(1)).save(any(Recipe.class));
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
        ingredient.setRecipe(recipe);

        when(recipeRepository.findById(any())).thenReturn(Optional.of(recipe));

        ingredientService.deleteById("2","1");

        assertEquals(0, recipeRepository.findById("2").get().getIngredients().size());
        verify(recipeRepository, times(2)).findById(anyString());
        verify(recipeRepository, times(1)).save(any(Recipe.class));

    }
}