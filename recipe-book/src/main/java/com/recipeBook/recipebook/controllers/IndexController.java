package com.recipeBook.recipebook.controllers;

import com.recipeBook.recipebook.domain.Category;
import com.recipeBook.recipebook.domain.UnitOfMeasure;
import com.recipeBook.recipebook.repositories.CategoryRepository;
import com.recipeBook.recipebook.repositories.UnitOfMeasureRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
public class IndexController {

    private CategoryRepository categoryRepository;
    private UnitOfMeasureRepository unitOfMeasureRepository;

    public IndexController(CategoryRepository categoryRepository, UnitOfMeasureRepository unitOfMeasureRepository) {
        this.categoryRepository = categoryRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    @RequestMapping({"","/","/index"})
    public String getIndexPage(){

        Optional<Category> american = categoryRepository.findByDescription("American");
        Optional<UnitOfMeasure> teaspoon = unitOfMeasureRepository.findByDescription("Teaspoon");

        System.out.println(american.get().getId());
        System.out.println(teaspoon.get().getId());
        return "index.html";
    }
}
