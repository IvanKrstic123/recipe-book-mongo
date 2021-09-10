package com.recipeBook.recipebook.converters;

import com.recipeBook.recipebook.commands.NotesCommand;
import com.recipeBook.recipebook.domain.Notes;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class NotesToNotesCommand implements Converter<Notes, NotesCommand> {

    private RecipeToRecipeCommand converter;

    @Synchronized
    @Nullable
    @Override
    public NotesCommand convert(Notes notes) {
        if (notes == null) {
            return null;
        }

        final NotesCommand notesCommand = new NotesCommand();
        notesCommand.setId(notes.getId());
        notesCommand.setRecipeNote(notes.getRecipeNotes());
        return notesCommand;
    }
}
