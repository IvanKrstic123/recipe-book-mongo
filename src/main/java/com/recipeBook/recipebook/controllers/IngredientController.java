package com.recipeBook.recipebook.controllers;

import com.recipeBook.recipebook.services.RecipeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IngredientController {

    private RecipeService service;

    public IngredientController(RecipeService service) {
        this.service = service;
    }

    @GetMapping
    @RequestMapping("recipe/{id}/ingredients")
    public String getIngredients(@PathVariable String id, Model model) {
        model.addAttribute("recipe", service.findCommandById(Long.valueOf(id)));

        return "recipe/ingredient/list";
    }

    //view

    //update

    //delete

}
