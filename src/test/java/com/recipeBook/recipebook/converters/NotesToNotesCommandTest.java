package com.recipeBook.recipebook.converters;

import com.recipeBook.recipebook.commands.NotesCommand;
import com.recipeBook.recipebook.domain.Notes;
import com.recipeBook.recipebook.domain.Recipe;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NotesToNotesCommandTest {

    private static final Long ID_VALUE = 1L;
    private static final String RECIPE_NOTE = "recipe_note";
    NotesToNotesCommand converter;

    @BeforeEach
    void setUp() {
        converter = new NotesToNotesCommand();
    }

    @Test
    void convert() {
        Notes notes = new Notes();
        notes.setId(ID_VALUE);
        notes.setRecipeNote(RECIPE_NOTE);

        NotesCommand converted = converter.convert(notes);

        assertNotNull(converted);
        assertEquals(ID_VALUE, converted.getId());
        assertEquals(RECIPE_NOTE, converted.getRecipeNote());
    }

    @Test
    void testEmptyObject() {
        assertNotNull(converter.convert(new Notes()));
    }

    @Test
    void testNullParameter() {
        assertNull(converter.convert(null));
    }

}