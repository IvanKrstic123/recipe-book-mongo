package com.recipeBook.recipebook.bootstrap;

import com.recipeBook.recipebook.domain.*;
import com.recipeBook.recipebook.repositories.CategoryRepository;
import com.recipeBook.recipebook.repositories.RecipeRepository;
import com.recipeBook.recipebook.repositories.UnitOfMeasureRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.*;

@Component
public class RecipeBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private RecipeRepository recipeRepository;
    private CategoryRepository categoryRepository;
    private UnitOfMeasureRepository unitOfMeasureRepository;

    public RecipeBootstrap(RecipeRepository recipeRepository, CategoryRepository categoryRepository, UnitOfMeasureRepository unitOfMeasureRepository) {
        this.recipeRepository = recipeRepository;
        this.categoryRepository = categoryRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        recipeRepository.saveAll(getRecipes());
    }

    private List<Recipe> getRecipes(){
        List<Recipe> recipes = new ArrayList<>();

        /** get unit of measure **/
        Optional<UnitOfMeasure> each = unitOfMeasureRepository.findByDescription("Each");

        if(!each.isPresent()) {
            throw  new RuntimeException("Expected UOM Not Found!");
        }

        Optional<UnitOfMeasure> tablespoon = unitOfMeasureRepository.findByDescription("Tablespoon");

        if(!tablespoon.isPresent()) {
            throw  new RuntimeException("Expected UOM Not Found!");
        }

        Optional<UnitOfMeasure> teaspoon = unitOfMeasureRepository.findByDescription("Teaspoon");

        if(!teaspoon.isPresent()) {
            throw  new RuntimeException("Expected UOM Not Found!");
        }

        Optional<UnitOfMeasure> pint = unitOfMeasureRepository.findByDescription("Pint");

        if(!pint.isPresent()) {
            throw  new RuntimeException("Expected UOM Not Found!");
        }

        Optional<UnitOfMeasure> dash = unitOfMeasureRepository.findByDescription("Dash");

        if(!dash.isPresent()) {
            throw  new RuntimeException("Expected UOM Not Found!");
        }

        Optional<UnitOfMeasure> ounce = unitOfMeasureRepository.findByDescription("Ounce");

        if(!ounce.isPresent()) {
            throw  new RuntimeException("Expected UOM Not Found!");
        }

        Optional<UnitOfMeasure> pinch = unitOfMeasureRepository.findByDescription("Pinch");

        if(!pinch.isPresent()) {
            throw  new RuntimeException("Expected UOM Not Found!");
        }

        Optional<UnitOfMeasure> cup = unitOfMeasureRepository.findByDescription("Cup");

        if(!cup.isPresent()) {
            throw  new RuntimeException("Expected UOM Not Found!");
        }

        UnitOfMeasure eachUOM = each.get();
        UnitOfMeasure tablespoonUOM = tablespoon.get();
        UnitOfMeasure teaspoonUOM = teaspoon.get();
        UnitOfMeasure pintUOM = pint.get();
        UnitOfMeasure dashUOM = dash.get();
        UnitOfMeasure ounceUOM = ounce.get();
        UnitOfMeasure pinchUOM = pinch.get();
        UnitOfMeasure cupUOM = cup.get();

        /** get categories **/
        Optional<Category> american = categoryRepository.findByDescription("American");

        if(!american.isPresent()) {
            throw new RuntimeException("Category Required!");
        }

        Optional<Category> mexican = categoryRepository.findByDescription("Mexican");

        if(!mexican.isPresent()) {
            throw new RuntimeException("Category Required!");
        }

        Category americanCategory = american.get();
        Category mexicanCategory = mexican.get();

        /** guacamole recipe **/
        Recipe guacRecipe = new Recipe();
        guacRecipe.setDescription("Perfect Guacmole");
        guacRecipe.setCookTime(0);
        guacRecipe.setPrepTime(10);
        guacRecipe.setDifficulty(Difficulty.EASY);
        guacRecipe.setDirections("Oljusti nesto... uraditi to i to i tako");

        Notes guacNotes = new Notes();
        guacNotes.setRecipeNote("Ovo je vrlo jednostavan recept koji se priprema svega u par koraka....");

        guacRecipe.setNotes(guacNotes);

        guacRecipe.addIngredient(new Ingredient("ripe avocados", new BigDecimal(2), eachUOM));
        guacRecipe.addIngredient(new Ingredient("Kosher Salt", new BigDecimal(5), teaspoonUOM));
        guacRecipe.addIngredient(new Ingredient("Fresh line juice or lemon juice", new BigDecimal(2), tablespoonUOM));
        guacRecipe.addIngredient(new Ingredient("Minced red onion", new BigDecimal(2), tablespoonUOM));

        guacRecipe.getCategories().add(americanCategory);
        guacRecipe.getCategories().add(mexicanCategory);

        /** add to return list **/
        recipes.add(guacRecipe);

        /** tacos Recipe **/
        Recipe tacosRecipe = new Recipe();
        tacosRecipe.setDescription("Spici Grilled Chicked Taco");
        tacosRecipe.setCookTime(9);
        tacosRecipe.setPrepTime(20);
        tacosRecipe.setDifficulty(Difficulty.MODERATE);
        tacosRecipe.setDirections("Pripremite plitku posudu u koju cete smestiti batake....");

        Notes tacosNotes = new Notes();
        tacosNotes.setRecipeNote("Poreklom je iz meksika i odlican je za celu porodicu. Priprema se veoma lako i vrlo je ukusno!");

        tacosRecipe.setNotes(tacosNotes);

        tacosRecipe.addIngredient(new Ingredient("Ancho Chili Powder", new BigDecimal(2), tablespoonUOM));
        tacosRecipe.addIngredient(new Ingredient("Bried origano", new BigDecimal(1), teaspoonUOM));
        tacosRecipe.addIngredient(new Ingredient("Dried Origano", new BigDecimal(1), teaspoonUOM));
        tacosRecipe.addIngredient(new Ingredient("Salt", new BigDecimal(5), teaspoonUOM));
        tacosRecipe.addIngredient(new Ingredient("Olive oil", new BigDecimal(2), eachUOM));

        /** add to return list **/
        recipes.add(tacosRecipe);

        return  recipes;
    }


}
