package com.recipeBook.recipebook.commands;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class NotesCommand {
    private String id;
    private RecipeCommand recipe;
    private String recipeNote;
}
