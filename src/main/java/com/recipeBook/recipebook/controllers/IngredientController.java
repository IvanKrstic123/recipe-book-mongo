package com.recipeBook.recipebook.controllers;

import com.recipeBook.recipebook.commands.IngredientCommand;
import com.recipeBook.recipebook.commands.RecipeCommand;
import com.recipeBook.recipebook.commands.UnitOfMeasureCommand;
import com.recipeBook.recipebook.services.IngredientService;
import com.recipeBook.recipebook.services.RecipeService;
import com.recipeBook.recipebook.services.UnitOfMeasureService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
public class IngredientController {

    private final RecipeService recipeService;
    private final IngredientService ingredientService;
    private final UnitOfMeasureService unitOfMeasureService;


    public IngredientController(RecipeService recipeService, IngredientService ingredientService, UnitOfMeasureService unitOfMeasureService) {
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
        this.unitOfMeasureService = unitOfMeasureService;
    }

    @GetMapping
    @RequestMapping("recipe/{id}/ingredients")
    public String getIngredients(@PathVariable String id, Model model) {
        model.addAttribute("recipe", recipeService.findCommandById(Long.valueOf(id)));

        return "recipe/ingredient/list";
    }

    @GetMapping
    @RequestMapping("recipe/{recipeId}/ingredient/{ingredientId}/show")
    public String showRecipeIngredient(@PathVariable String recipeId,
                                       @PathVariable String ingredientId, Model model) {
        model.addAttribute("ingredient", ingredientService.findByRecipeIdAndIngredientId(Long.valueOf(recipeId), Long.valueOf(ingredientId)));

        return "recipe/ingredient/show";
    }

    @GetMapping
    @RequestMapping("recipe/{recipeId}/ingredient/{ingredientId}/update")
    public String updateRecipeIngredient(@PathVariable String recipeId,
                                       @PathVariable String ingredientId, Model model) {
        model.addAttribute("ingredient", ingredientService.findByRecipeIdAndIngredientId(Long.valueOf(recipeId), Long.valueOf(ingredientId)));

        model.addAttribute("uomList", unitOfMeasureService.listAllUoms());

        return "recipe/ingredient/ingredientform";
    }

    @GetMapping
    @RequestMapping("recipe/{recipeId}/ingredient/new")
    public String newIngredientForm(@PathVariable String recipeId, Model model){
        RecipeCommand recipeCommand = recipeService.findCommandById(Long.valueOf(recipeId));

        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setRecipeId(recipeCommand.getId());
        ingredientCommand.setUom(new UnitOfMeasureCommand());

        model.addAttribute("ingredient", ingredientCommand);
        model.addAttribute("uomList", unitOfMeasureService.listAllUoms());

        return "recipe/ingredient/ingredientform";
    }

    @PostMapping
    @RequestMapping("recipe/{recipeId}/ingredient")
    public String saveOrUpdateIngredient(@ModelAttribute IngredientCommand command, Model model){
        IngredientCommand saved = ingredientService.saveIngredientCommand(command);

        log.debug("Saved recipe id: " + saved.getRecipeId());
        log.debug("saved ingredient id: " + saved.getId());

        return "redirect:/recipe/" + saved.getRecipeId() +"/ingredient/" + saved.getId() + "/show";
    }

    @GetMapping
    @RequestMapping("recipe/{recipeId}/ingredient/{ingredientId}/delete")
    public String deleteIngredient(@PathVariable String recipeId, @PathVariable String ingredientId){

        ingredientService.deleteById(Long.valueOf(recipeId), Long.valueOf(ingredientId));

        return "redirect:/recipe/" + recipeId + "/ingredients";
    }
}
