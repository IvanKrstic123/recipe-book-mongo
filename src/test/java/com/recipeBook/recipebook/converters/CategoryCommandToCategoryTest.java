package com.recipeBook.recipebook.converters;

import com.recipeBook.recipebook.commands.CategoryCommand;
import com.recipeBook.recipebook.domain.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoryCommandToCategoryTest {

    private static final String ID_VALUE = "1";
    private static final String DESCRIPTION = "description";
    private CategoryCommandToCategory converter;

    @BeforeEach
    void setUp() {
        converter = new CategoryCommandToCategory();
    }

    @Test
    void convert() {
        CategoryCommand source = new CategoryCommand();
        source.setId(ID_VALUE);
        source.setDescription(DESCRIPTION);

        Category converted = converter.convert(source);

        assertNotNull(source);
        assertEquals(ID_VALUE, converted.getId());
        assertEquals(DESCRIPTION, converted.getDescription());
    }

    @Test
    void testEmptyObject() {
        assertNotNull(converter.convert(new CategoryCommand()));
    }

    @Test
    void testNullParamater() {
        assertNull(converter.convert(null));
    }
}