package com.recipeBook.recipebook.repositories;

import com.recipeBook.recipebook.bootstrap.RecipeBootstrap;
import com.recipeBook.recipebook.domain.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;


@DataMongoTest
class CategoryRepositoryTestIT {

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    RecipeRepository recipeRepository;

    @Autowired
    UnitOfMeasureRepository unitOfMeasureRepository;

    RecipeBootstrap recipeBootstrap;

    @BeforeEach
    void setUp() {
        recipeRepository.deleteAll(); // mysql suports transactions so after test rollback occur
        categoryRepository.deleteAll(); // mongodb (transactions not suported -no rollback )
        unitOfMeasureRepository.deleteAll();

        recipeBootstrap = new RecipeBootstrap(categoryRepository, recipeRepository, unitOfMeasureRepository);
        recipeBootstrap.onApplicationEvent(null);
    }

    @Test
    void findByDescription() {
        Optional<Category> american = categoryRepository.findByDescription("American");

        assertEquals("American", american.get().getDescription());

    }

    @Test
    void findByDescriptionItalian() {
        Optional<Category> italian = categoryRepository.findByDescription("Italian");

        assertEquals("Italian", italian.get().getDescription());

    }
}