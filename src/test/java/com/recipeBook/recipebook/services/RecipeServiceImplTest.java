package com.recipeBook.recipebook.services;

import com.recipeBook.recipebook.domain.Recipe;
import com.recipeBook.recipebook.repositories.RecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RecipeServiceImplTest {

    @Mock
    RecipeRepository recipeRepository;

    RecipeServiceImpl recipeService;

    @BeforeEach
    void setUp() {
        recipeRepository = Mockito.mock(RecipeRepository.class);

        recipeService = new RecipeServiceImpl(recipeRepository);
    }

    @Test
    void getRecipes() {

        /* how mock data will look like */
        Recipe recipe = new Recipe();
        Set<Recipe> recipeData = new HashSet();
        recipeData.add(recipe);

        /* what should be returned when calling method */
        when(recipeRepository.findAll()).thenReturn(recipeData);

        /* calling method for testing */
        Set<Recipe> recipes = recipeService.getRecipes();

        assertEquals(recipes.size(), 1);
        verify(recipeRepository, times(1)).findAll(); /* broj pozivanja metode */
    }

    @Test
    void findById() {
        Recipe recipe = new Recipe();
        recipe.setId(1L);
        Optional<Recipe> recipeOptional = Optional.of(recipe);

        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);

        Recipe recipeReturned = recipeService.findById(1L);

        assertNotNull(recipeReturned);
        verify(recipeRepository, times(1)).findById(anyLong());
        verify(recipeRepository, never()).findAll();
    }
}