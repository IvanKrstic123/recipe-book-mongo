package com.recipeBook.recipebook.commands;

import com.recipeBook.recipebook.domain.Difficulty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.*;

@Getter
@Setter
@NoArgsConstructor
public class RecipeCommand {

    private String id;

    @NotBlank
    @Size(min = 3, max = 255) /** maven dependency -validation- */
    private String description;

    @Min(1)
    @Max(999)
    private Integer prepTime;

    @Min(1)
    @Max(999)
    private Integer cookTime;

    @Min(1)
    @Max(100)
    private Integer servings;

    @URL
    private String url;

    @NotBlank
    private String directions;

    private Byte[] image;
    private String source;
    private Difficulty difficulty;
    private NotesCommand notes;
    private List<IngredientCommand> ingredients = new ArrayList<>();
    private List<CategoryCommand> categories = new ArrayList<>();

}
