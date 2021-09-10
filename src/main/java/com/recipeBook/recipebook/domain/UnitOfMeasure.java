package com.recipeBook.recipebook.domain;

import lombok.*;
import org.springframework.data.annotation.Id;


@Data
public class UnitOfMeasure {

    @Id
    private String id;
    private String description;

}
