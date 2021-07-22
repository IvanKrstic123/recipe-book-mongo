package com.recipeBook.recipebook.converters;

import com.recipeBook.recipebook.commands.RecipeCommand;
import com.recipeBook.recipebook.domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RecipeToRecipeCommandTest {

    private static final Long RECIPE_ID = 1L;
    private static final Integer COOK_TIME = 5;
    private static final Integer PREP_TIME = 25;
    private static final String DESCRIPTION = "description";
    private static final String DIRECTIONS = "directions";
    private static final Integer SERVINGS = 50;
    private static final String SOURCE = "www.food.rs";
    private static final String URL = "url";
    private static final Long NOTES_ID = 1L;
    private static final Long CATEGORY_ID = 20L;
    private static final Long CATEGORY1_ID = 21L;
    private static final Long INGREDIENT_ID = 10L;
    private static final Long INGREDIENT1_ID = 11L;
    private static final Difficulty DIFFICULTY = Difficulty.EASY;
    RecipeToRecipeCommand converter;

    @BeforeEach
    void setUp() {
        converter = new RecipeToRecipeCommand(
                new NotesToNotesCommand(),
                new CategoryToCategoryCommand(),
                new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand()));
    }

    @Test
    void convert() {
        //initialize
        Recipe recipe = new Recipe();
        recipe.setId(RECIPE_ID);
        recipe.setCookTime(COOK_TIME);
        recipe.setPrepTime(PREP_TIME);
        recipe.setDescription(DESCRIPTION);
        recipe.setDifficulty(DIFFICULTY);
        recipe.setDirections(DIRECTIONS);
        recipe.setServings(SERVINGS);
        recipe.setSource(SOURCE);
        recipe.setUrl(URL);

        /** initialize Notes objects */
        Notes notes1 = new Notes();
        notes1.setId(NOTES_ID);

        recipe.setNotes(notes1);

        /** initialize Categories objects */
        Category category = new Category();
        category.setId(CATEGORY_ID);

        Category category1 = new Category();
        category1.setId(CATEGORY1_ID);

        recipe.getCategories().add(category);
        recipe.getCategories().add(category1);

        /** initialize Ingredients objects */
        Ingredient ingredient = new Ingredient();
        ingredient.setId(INGREDIENT_ID);

        Ingredient ingredient1 = new Ingredient();
        ingredient.setId(INGREDIENT1_ID);

        recipe.getIngredients().add(ingredient);
        recipe.getIngredients().add(ingredient1);

        //when
        RecipeCommand converted = converter.convert(recipe);

        //assert
        assertNotNull(converted);
        assertEquals(RECIPE_ID, converted.getId());
        assertEquals(COOK_TIME, converted.getCookTime());
        assertEquals(PREP_TIME, converted.getPrepTime());
        assertEquals(DESCRIPTION, converted.getDescription());
        assertEquals(DIFFICULTY, converted.getDifficulty());
        assertEquals(DIRECTIONS, converted.getDirections());
        assertEquals(SERVINGS, converted.getServings());
        assertEquals(SOURCE, converted.getSource());
        assertEquals(URL, converted.getUrl());
        assertEquals(NOTES_ID, converted.getNotes().getId());
        assertEquals(2, converted.getCategories().size());
        assertEquals(2, converted.getIngredients().size());
    }

    @Test
    public void testNullObject() throws Exception {
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmptyObject() throws Exception {
        assertNotNull(converter.convert(new Recipe()));
    }

}