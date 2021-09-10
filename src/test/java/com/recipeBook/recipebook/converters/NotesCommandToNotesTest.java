package com.recipeBook.recipebook.converters;

import com.recipeBook.recipebook.commands.NotesCommand;
import com.recipeBook.recipebook.domain.Notes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NotesCommandToNotesTest {

    private static final String ID_VALUE = "1";
    private static final String RECIPE_NOTE = "recipe note";
    private NotesCommandToNotes converter;

    @BeforeEach
    void setUp() {
        converter = new NotesCommandToNotes();
    }

    @Test
    void convert() {
        NotesCommand source = new NotesCommand();
        source.setId(ID_VALUE);
        source.setRecipeNote(RECIPE_NOTE);

        Notes converted = converter.convert(source);

        assertNotNull(converted);
        assertEquals(ID_VALUE, converted.getId());
        assertEquals(RECIPE_NOTE, converted.getRecipeNote());
    }

    @Test
    void testEmptyObject() {
        assertNotNull(converter.convert(new NotesCommand()));
    }

    @Test
    void testNullParameter() {
        assertNull(converter.convert(null));
    }
}