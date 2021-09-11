package com.recipeBook.recipebook.converters;

import com.recipeBook.recipebook.commands.IngredientCommand;
import com.recipeBook.recipebook.domain.Ingredient;
import com.recipeBook.recipebook.domain.Recipe;
import com.recipeBook.recipebook.domain.UnitOfMeasure;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IngredientToIngredientCommandTest {

    private static final String ID_VALUE = "1";
    private static final String DESCRIPTION = "description";
    private static final String UOM_ID = "1";
    private static final String UOM_DESCRIPTION = "uom_description";
    private static final Recipe recipe = new Recipe();
    IngredientToIngredientCommand converter;

    @BeforeEach
    void setUp() {
        converter = new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand());
    }

    @Test
    void convert() {
        Ingredient ingredient = new Ingredient();
        ingredient.setId(ID_VALUE);
        ingredient.setDescription(DESCRIPTION);

        UnitOfMeasure uom = new UnitOfMeasure();
        uom.setId(UOM_ID);
        uom.setDescription(UOM_DESCRIPTION);
        ingredient.setUnitOfMeasure(uom);
//        ingredient.setRecipe(recipe);

        IngredientCommand converted = converter.convert(ingredient);

        assertNotNull(converted);
        assertEquals(ID_VALUE, converted.getId());
        assertEquals(DESCRIPTION, converted.getDescription());
        assertEquals(UOM_ID, converted.getUom().getId());
        assertEquals(UOM_DESCRIPTION, converted.getUom().getDescription());
    }

    @Test
    void testEmptyObject() {
        assertNotNull(converter.convert(new Ingredient()));
    }

    @Test
    void testnullParameter() {
        assertNull(converter.convert(null));
    }
}