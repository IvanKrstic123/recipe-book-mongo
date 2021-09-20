package com.recipeBook.recipebook.commands;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class IngredientCommand {

    private String id;
    private String recipeId;

    @NotBlank // not null & not empty string
    private String description;

    @Min(value = 1)
    @NotNull
    private BigDecimal amount;

    @NotNull
    private UnitOfMeasureCommand uom;

}
