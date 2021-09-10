package com.recipeBook.recipebook.converters;

import com.recipeBook.recipebook.commands.CategoryCommand;
import com.recipeBook.recipebook.domain.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

class CategoryToCategoryCommandTest {

    private static final String ID_VALUE = "1";
    private static final String DESCRIPTION = "description";
    CategoryToCategoryCommand converter;

    @BeforeEach
    void setUp() {
        converter = new CategoryToCategoryCommand();
    }

    @Test
    void convert() {
        Category category = new Category();
        category.setId(ID_VALUE);
        category.setDescription(DESCRIPTION);

        CategoryCommand converted = converter.convert(category);

        assertNotNull(converted);
        assertEquals(ID_VALUE, converted.getId());
        assertEquals(DESCRIPTION, converted.getDescription());
    }

    @Test
    void testEmptyObject() {
        assertNotNull(converter.convert(new Category()));
    }

    @Test
    void testNullParameter() {
        assertNull(converter.convert(null));
    }
}