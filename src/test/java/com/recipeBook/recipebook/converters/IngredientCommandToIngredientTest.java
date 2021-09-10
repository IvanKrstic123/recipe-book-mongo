package com.recipeBook.recipebook.converters;

import com.recipeBook.recipebook.commands.IngredientCommand;
import com.recipeBook.recipebook.commands.UnitOfMeasureCommand;
import com.recipeBook.recipebook.domain.Ingredient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class IngredientCommandToIngredientTest {

    private static final String ID_VALUE = "1";
    private static final String DESCRIPTION = "description";
    private static final BigDecimal AMOUNT = BigDecimal.valueOf(10L);
    private static final String RECIPE_ID = "1";
    private static final String UOM_ID = "1";


    private IngredientCommandToIngredient converter;

    @BeforeEach
    void setUp() {
        converter = new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure());
    }

    @Test
    void convert() {
        IngredientCommand source = new IngredientCommand();
        source.setId(ID_VALUE);
        source.setDescription(DESCRIPTION);
        source.setAmount(AMOUNT);
        UnitOfMeasureCommand uom = new UnitOfMeasureCommand();
        uom.setId(UOM_ID);
        source.setUom(uom);

        Ingredient converted = converter.convert(source);

        assertNotNull(converted);
        assertNotNull(converted.getUnitOfMeasure());
        assertEquals(ID_VALUE, converted.getId());
        assertEquals(AMOUNT, converted.getAmount());
        assertEquals(DESCRIPTION, converted.getDescription());
        assertEquals(UOM_ID, converted.getUnitOfMeasure().getId());
    }

    @Test
    void testNullParameter() {
        assertNull(converter.convert(null));
    }
    @Test
    void testEmtpyObject() {
        assertNotNull(converter.convert(new IngredientCommand()));
    }

}