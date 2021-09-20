package com.recipeBook.recipebook.controllers;

import com.recipeBook.recipebook.commands.RecipeCommand;
import com.recipeBook.recipebook.exceptions.NotFoundExceotion;
import com.recipeBook.recipebook.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
 import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.exceptions.TemplateInputException;

@Slf4j
@Controller
public class RecipeController {

    private final RecipeService recipeService;

    private WebDataBinder webDataBinder;

    @InitBinder
    public void initBinder(WebDataBinder webDataBinder) {
        this.webDataBinder = webDataBinder;
    }

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping("/recipe/{id}/show")
    public String showById(@PathVariable String id, Model model) {

        model.addAttribute("recipe", recipeService.findById(id));

        return "recipe/show";

    }

    @GetMapping("recipe/new")
    public String newRecipe(Model model) {
        model.addAttribute("recipe", new RecipeCommand());

        return "recipe/recipeform";
    }

    @GetMapping("recipe/{id}/update")
    public String updateRecipe(@PathVariable String id, Model model) {
        model.addAttribute("recipe", recipeService.findCommandById(id));

        return "recipe/recipeform";
    }

    @PostMapping("recipe")
    public String saveOrUpdate(@ModelAttribute("recipe") RecipeCommand command) throws BindException {   /** binding form post params to recipecommand object **/

    webDataBinder.validate();
    BindingResult bindingResult = webDataBinder.getBindingResult();

    if (bindingResult.hasErrors()) {
        bindingResult.getAllErrors().forEach(objectError -> {
            log.debug(objectError.toString());
        });

        return "recipe/recipeform";
    }
        RecipeCommand savedCommand = recipeService.saveRecipeCommand(command).block();

        return "redirect:/recipe/"+ savedCommand.getId() + "/show";  /** showing saved recipe **/
    }

    @GetMapping("recipe/{id}/delete")
    public String deleteById(@PathVariable String id){

        recipeService.deleteById(id);
        return "redirect:/";
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({NotFoundExceotion.class, TemplateInputException.class})
    public String handleNotFound(Exception e, Model model) {

        model.addAttribute("exception", e);

        return "404error";
    }
}
