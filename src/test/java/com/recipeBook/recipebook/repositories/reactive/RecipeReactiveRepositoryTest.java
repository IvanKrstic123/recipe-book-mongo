package com.recipeBook.recipebook.repositories.reactive;

import com.recipeBook.recipebook.domain.Recipe;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@DataMongoTest
class RecipeReactiveRepositoryTest {

    @Autowired
    RecipeReactiveRepository recipeReactiveRepository;

    @BeforeEach
    void setUp() {
        recipeReactiveRepository.deleteAll().block();
    }

    @Test
    void testSaveRecipe() {
        Recipe recipe = new Recipe();
        recipe.setDescription("Dobar Recept");

        recipeReactiveRepository.save(recipe).block();

        assertEquals(1, recipeReactiveRepository.count().block());
    }
}