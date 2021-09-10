package com.recipeBook.recipebook.converters;

import com.recipeBook.recipebook.commands.CategoryCommand;
import com.recipeBook.recipebook.commands.IngredientCommand;
import com.recipeBook.recipebook.commands.NotesCommand;
import com.recipeBook.recipebook.commands.RecipeCommand;
import com.recipeBook.recipebook.domain.Difficulty;
import com.recipeBook.recipebook.domain.Recipe;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RecipeCommandToRecipeTest {

    public static final String RECIPE_ID = "1";
    public static final Integer COOK_TIME = Integer.valueOf("5");
    public static final Integer PREP_TIME = Integer.valueOf("7");
    public static final String DESCRIPTION = "My Recipe";
    public static final String DIRECTIONS = "Directions";
    public static final Difficulty DIFFICULTY = Difficulty.EASY;
    public static final Integer SERVINGS = Integer.valueOf("3");
    public static final String SOURCE = "Source";
    public static final String URL = "Some URL";
    public static final String CAT_ID_1 = "1";
    public static final String CAT_ID2 = "2";
    public static final String INGRED_ID_1 = "3";
    public static final String INGRED_ID_2 = "4";
    public static final String NOTES_ID = "9";

    RecipeCommandToRecipe converter;

    @BeforeEach
    void setUp() {
        converter = new RecipeCommandToRecipe(
                new CategoryCommandToCategory(),
                new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure()),
                new NotesCommandToNotes()
        );
    }

    @Test
    void convert() {
        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(RECIPE_ID);
        recipeCommand.setCookTime(COOK_TIME);
        recipeCommand.setPrepTime(PREP_TIME);
        recipeCommand.setDescription(DESCRIPTION);
        recipeCommand.setDifficulty(DIFFICULTY);
        recipeCommand.setDirections(DIRECTIONS);
        recipeCommand.setServings(SERVINGS);
        recipeCommand.setSource(SOURCE);
        recipeCommand.setUrl(URL);

        NotesCommand notes = new NotesCommand();
        notes.setId(NOTES_ID);
        recipeCommand.setNotes(notes);

        CategoryCommand category = new CategoryCommand();
        category.setId(CAT_ID_1);

        CategoryCommand category2 = new CategoryCommand();
        category2.setId(CAT_ID2);

        recipeCommand.getCategories().add(category);
        recipeCommand.getCategories().add(category2);

        IngredientCommand ingredient = new IngredientCommand();
        ingredient.setId(INGRED_ID_1);

        IngredientCommand ingredient2 = new IngredientCommand();
        ingredient2.setId(INGRED_ID_2);

        recipeCommand.getIngredients().add(ingredient);
        recipeCommand.getIngredients().add(ingredient2);

        /** when converted is called */
        Recipe converted = converter.convert(recipeCommand);

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
        assertNotNull(converter.convert(new RecipeCommand()));
    }
}


