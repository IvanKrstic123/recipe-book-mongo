package com.recipeBook.recipebook.services;

import com.recipeBook.recipebook.domain.Recipe;
import com.recipeBook.recipebook.repositories.RecipeRepository;
import com.recipeBook.recipebook.repositories.reactive.RecipeReactiveRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

import java.io.IOException;

@Slf4j
@Service
public class ImageServiceImpl implements ImageService{

    private final RecipeReactiveRepository recipeReactiveRepository;

    public ImageServiceImpl(RecipeReactiveRepository recipeRepository) {
        this.recipeReactiveRepository = recipeRepository;
    }

    @Override
    public Mono<Void> saveImageFile(String recipeId, MultipartFile file) throws IOException {

        recipeReactiveRepository.findById(recipeId)
                .map(recipe -> {
                    Byte[] byteObjects = new Byte[0];
                    try {
                        byteObjects = new Byte[file.getBytes().length];

                        int i = 0;

                        for (byte b : file.getBytes()) {
                            byteObjects[i++] = b;
                        }

                        recipe.setImage(byteObjects);

                        return recipe;
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }).publish(recipeMono -> recipeReactiveRepository.save(recipeMono.block()));

        return Mono.empty();
    }
}
