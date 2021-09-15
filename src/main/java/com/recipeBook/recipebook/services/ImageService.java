package com.recipeBook.recipebook.services;

import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

import java.io.IOException;

public interface ImageService {

    Mono<Void> saveImageFile(String recipeId, MultipartFile file) throws IOException;
}
