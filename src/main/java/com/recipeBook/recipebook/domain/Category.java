package com.recipeBook.recipebook.domain;

import lombok.*;
import org.springframework.data.annotation.Id;


import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode(exclude = "recipes")
public class Category {

    @Id
    private String id;
    private String description;
    private Set<Recipe> recipes;

}
