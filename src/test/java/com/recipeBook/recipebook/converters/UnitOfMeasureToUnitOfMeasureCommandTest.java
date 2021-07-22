package com.recipeBook.recipebook.converters;

import com.recipeBook.recipebook.commands.UnitOfMeasureCommand;
import com.recipeBook.recipebook.domain.UnitOfMeasure;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UnitOfMeasureToUnitOfMeasureCommandTest {

    private static final Long UOM_ID = 10L;
    private static final String UOM_DESCRIPTION = "DESCRIPTION";
    UnitOfMeasureToUnitOfMeasureCommand converter;

    @BeforeEach
    void setUp() {
        converter = new UnitOfMeasureToUnitOfMeasureCommand();
    }

    @Test
    void convert() {
        UnitOfMeasure uom = new UnitOfMeasure();
        uom.setId(UOM_ID);
        uom.setDescription(UOM_DESCRIPTION);

        UnitOfMeasureCommand converted = converter.convert(uom);

        assertNotNull(converted);
        assertEquals(UOM_ID, converted.getId());
        assertEquals(UOM_DESCRIPTION, converted.getDescription());
    }

    @Test
    void testEmptyObject() {
        assertNotNull(converter.convert(new UnitOfMeasure()));
    }

    @Test
    void testNullParameter() {
        assertNull(converter.convert(null));
    }
}