package com.recipeBook.recipebook.services;

import com.recipeBook.recipebook.domain.Recipe;
import com.recipeBook.recipebook.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@Service
public class ImageServiceImpl implements ImageService{

    private final RecipeRepository recipeRepository;

    public ImageServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    public void saveImageFile(String recipeId, MultipartFile file) throws IOException {

        Recipe recipe = recipeRepository.findById(recipeId).get();

        //collection bytes and storing in wraper type -> Byte[]
        Byte[] imageBytes = new Byte[file.getBytes().length];

        int i = 0;

        for (byte temp : file.getBytes()) {
            imageBytes[i++] = temp;
        }

        recipe.setImage(imageBytes);

        recipeRepository.save(recipe);
    }
}
