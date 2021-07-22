package com.recipeBook.recipebook.converters;

import com.recipeBook.recipebook.commands.CategoryCommand;
import com.recipeBook.recipebook.domain.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoryCommandToCategoryTest {

    private static final Long LONG_VALUE = 1L;
    private static final String DESCRIPTION = "description";
    private CategoryCommandToCategory converter;

    @BeforeEach
    void setUp() {
        converter = new CategoryCommandToCategory();
    }

    @Test
    void convert() {
        CategoryCommand source = new CategoryCommand();
        source.setId(LONG_VALUE);
        source.setDescription(DESCRIPTION);

        Category converted = converter.convert(source);

        assertNotNull(source);
        assertEquals(LONG_VALUE, converted.getId());
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