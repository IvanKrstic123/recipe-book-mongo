package com.recipeBook.recipebook.repositories.reactive;

import com.recipeBook.recipebook.domain.Category;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;


import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@DataMongoTest
class CategoryReactiveRepositoryTest {

    @Autowired
    CategoryReactiveRepository categoryReactiveRepository;

    @BeforeEach
    void setUp() {
        categoryReactiveRepository.deleteAll().block();
    }

    @Test
    void testSave() {
        Category category = new Category();
        category.setDescription("Dobar");

        categoryReactiveRepository.save(category).block();

        assertEquals(1, categoryReactiveRepository.count().block());
    }

    @Test
    void testFindByDescription() {
        Category category = new Category();
        category.setDescription("foo");

        categoryReactiveRepository.save(category).block();

        Category foo = categoryReactiveRepository.findByDescription("foo").block();

        assertNotNull(foo.getId());
    }
}