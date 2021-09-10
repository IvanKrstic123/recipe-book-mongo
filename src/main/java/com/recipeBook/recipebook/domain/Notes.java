package com.recipeBook.recipebook.domain;

import lombok.*;
import org.springframework.data.annotation.Id;

@Getter
@Setter
@EqualsAndHashCode(exclude = "recipe")
public class Notes {

    @Id
    private String id;

    private Recipe recipe;

    private String recipeNotes;

    /** getters and setters **/
}
