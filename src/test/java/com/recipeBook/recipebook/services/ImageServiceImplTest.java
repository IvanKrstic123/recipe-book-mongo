package com.recipeBook.recipebook.services;

import com.recipeBook.recipebook.domain.Recipe;
import com.recipeBook.recipebook.repositories.RecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class ImageServiceImplTest {

    @Mock
    RecipeRepository recipeRepository;

    ImageService imageService;

    @BeforeEach
    void setUp() {
        recipeRepository = Mockito.mock(RecipeRepository.class);

        imageService = new ImageServiceImpl(recipeRepository);
    }

    @Test
    void saveImageFile() throws IOException {

        MultipartFile multipartFile = new MockMultipartFile("imageFile", "testing.txt", "text/plain",
                "Spring Fremework Guru".getBytes());

        Long id = 1L;
        Recipe recipe = new Recipe();
        recipe.setId(id);

        Optional<Recipe> optionalRecipe = Optional.of(recipe);

        when(recipeRepository.findById(anyLong())).thenReturn(optionalRecipe);

        ArgumentCaptor<Recipe> argumentCaptor = ArgumentCaptor.forClass(Recipe.class);

        //because this method is void we use ArgumentCaptor
        imageService.saveImageFile(id, multipartFile);

        verify(recipeRepository, times(1)).save(argumentCaptor.capture());

        Recipe savedRecipe = argumentCaptor.getValue();

        assertEquals(multipartFile.getBytes().length, savedRecipe.getImage().length );
    }
}