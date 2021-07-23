package com.recipeBook.recipebook.domain;

import lombok.*;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"notes", "ingredients", "categories"})
@Entity
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    private Integer prepTime;
    private Integer cookTime;
    private Integer servings;
    private String source;
    private String url;

    @Lob
    private String directions;

    @Lob /** large object **/
    private Byte[] image;

    @Enumerated(value = EnumType.STRING) /** ordinal je default **/
    private Difficulty difficulty;

    @OneToOne(cascade = CascadeType.ALL) /** necemo raditi sa notes i ingredients posebno pa se brise ako se obrise recipe **/
    private Notes notes;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "recipe")    /** propertie child-a,target **/
    private Set<Ingredient> ingredients = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "recipe_category",
        joinColumns = @JoinColumn(name = "recipe_id"), inverseJoinColumns = @JoinColumn(name = "category_id"))
    private Set<Category> categories = new HashSet<>();

    /** getters and setters **/


    public void setNotes(Notes notes) {
        if (notes != null) {
            this.notes = notes;
            notes.setRecipe(this);
        }
    }

    public Recipe addIngredient(Ingredient ingredient) {
        ingredient.setRecipe(this);
        this.ingredients.add(ingredient);
        return this;
    }



}
