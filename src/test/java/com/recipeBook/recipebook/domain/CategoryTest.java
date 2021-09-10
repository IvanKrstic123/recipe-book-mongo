package com.recipeBook.recipebook.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoryTest {

    Category category;

    @BeforeEach
    public void setUp(){
        category = new Category();
    }

    @Test
    void getId() {
        category.setId("4");

        assertEquals("4", category.getId());
    }

    @Test
    void getDescription() {
    }

    @Test
    void getRecipes() {
    }


}