package com.recipeBook.recipebook.controllers;

import com.recipeBook.recipebook.commands.IngredientCommand;
import com.recipeBook.recipebook.commands.RecipeCommand;
import com.recipeBook.recipebook.commands.UnitOfMeasureCommand;
import com.recipeBook.recipebook.exceptions.NotFoundExceotion;
import com.recipeBook.recipebook.services.IngredientService;
import com.recipeBook.recipebook.services.RecipeService;
import com.recipeBook.recipebook.services.UnitOfMeasureService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.exceptions.TemplateInputException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Controller
public class IngredientController {

    private final RecipeService recipeService;
    private final IngredientService ingredientService;
    private final UnitOfMeasureService unitOfMeasureService;

    private WebDataBinder webDataBinder;

    @InitBinder("ingredient")
    public void initBinder(WebDataBinder webDataBinder) {
        this.webDataBinder = webDataBinder;
    }

    public IngredientController(RecipeService recipeService, IngredientService ingredientService, UnitOfMeasureService unitOfMeasureService) {
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
        this.unitOfMeasureService = unitOfMeasureService;
    }

    @GetMapping("recipe/{id}/ingredients")
    public String getIngredients(@PathVariable String id, Model model) {
        model.addAttribute("recipe", recipeService.findCommandById(id));

        return "recipe/ingredient/list";
    }

    @GetMapping("recipe/{recipeId}/ingredient/{ingredientId}/show")
    public String showRecipeIngredient(@PathVariable String recipeId,
                                       @PathVariable String ingredientId, Model model) {
        model.addAttribute("ingredient", ingredientService.findByRecipeIdAndIngredientId(recipeId, ingredientId));

        return "recipe/ingredient/show";
    }

    @GetMapping("recipe/{recipeId}/ingredient/{ingredientId}/update")
    public String updateRecipeIngredient(@PathVariable String recipeId,
                                       @PathVariable String ingredientId, Model model) {
        model.addAttribute("ingredient", ingredientService.findByRecipeIdAndIngredientId(recipeId, ingredientId));

        return "recipe/ingredient/ingredientform";
    }

    @GetMapping("recipe/{recipeId}/ingredient/new")
    public Mono<String> newIngredientForm(@PathVariable String recipeId, Model model){

        return recipeService.findCommandById(recipeId).map(recipeCommand -> {
            IngredientCommand ingredientCommand = new IngredientCommand();
            ingredientCommand.setRecipeId(recipeCommand.getId());
            ingredientCommand.setUom(new UnitOfMeasureCommand());

            model.addAttribute("ingredient", ingredientCommand);

            return "recipe/ingredient/ingredientform";
        });

    }

    @PostMapping("recipe/{recipeId}/ingredient")
    public String saveOrUpdateIngredient(@ModelAttribute("ingredient") IngredientCommand command, @PathVariable String recipeId,
                                         Model model){

        webDataBinder.validate();
        BindingResult bindingResult = webDataBinder.getBindingResult();

        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(objectError -> {
                log.debug(objectError.toString());
            });

            return "recipe/ingredient/ingredientform";
        }

        IngredientCommand saved = ingredientService.saveIngredientCommand(command);

        return "redirect:/recipe/" + recipeId +"/ingredient/" + saved.getId() + "/show";
    }

    @GetMapping("recipe/{recipeId}/ingredient/{ingredientId}/delete")
    public Mono<String> deleteIngredient(@PathVariable String recipeId, @PathVariable String ingredientId){

        ingredientService.deleteById(recipeId, ingredientId);

        return Mono.just("redirect:/recipe/" + recipeId + "/ingredients");
    }

    @ModelAttribute("uomList")
    public Flux<UnitOfMeasureCommand> populateUomList(){
        return unitOfMeasureService.listAllUoms();
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({NotFoundExceotion.class, TemplateInputException.class})
    public String handleNotFound(Exception e, Model model) {

        model.addAttribute("exception", e);

        return "404error";
    }
}
