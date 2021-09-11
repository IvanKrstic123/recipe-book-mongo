package com.recipeBook.recipebook.repositories;

import com.recipeBook.recipebook.bootstrap.RecipeBootstrap;
import com.recipeBook.recipebook.domain.UnitOfMeasure;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest
class UnitOfMeasureRepositoryTestIT {

    @Autowired
    UnitOfMeasureRepository unitOfMeasureRepository;

    @Autowired
    RecipeRepository recipeRepository;

    @Autowired
    CategoryRepository categoryRepository;

    RecipeBootstrap recipeBootstrap;

    @BeforeEach
    void setUp() {
        recipeRepository.deleteAll(); // mysql suports transactions so after test rollback ocur
        categoryRepository.deleteAll(); // mondo (transactions not suported -no rollback )
        unitOfMeasureRepository.deleteAll();

        recipeBootstrap = new RecipeBootstrap(categoryRepository, recipeRepository, unitOfMeasureRepository);
        recipeBootstrap.onApplicationEvent(null);
    }

    @Test
    void findByDescription() {
        Optional<UnitOfMeasure> teaspon = unitOfMeasureRepository.findByDescription("Teaspoon");

        assertEquals("Teaspoon", teaspon.get().getDescription());
    }

    @Test
    void findByDescriptionCup() {
        Optional<UnitOfMeasure> cup = unitOfMeasureRepository.findByDescription("Cup");

        assertEquals("Cup", cup.get().getDescription());
    }
}