package com.recipeBook.recipebook.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"notes", "ingredients", "categories"})
@Document
public class Recipe {

    @Id
    private String id;
    private String description;
    private Integer prepTime;
    private Integer cookTime;
    private Integer servings;
    private String source;
    private String url;

    private String directions;

    private Byte[] image;

    private Difficulty difficulty;

    private Notes notes;

    private Set<Ingredient> ingredients = new HashSet<>();

    @DBRef
    private Set<Category> categories = new HashSet<>();

    /** getters and setters **/


    public void setNotes(Notes notes) {
        if (notes != null) {
            this.notes = notes;
        }
    }

    public Recipe addIngredient(Ingredient ingredient) {
        this.ingredients.add(ingredient);
        return this;
    }
}
