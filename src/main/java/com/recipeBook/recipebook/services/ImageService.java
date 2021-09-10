package com.recipeBook.recipebook.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageService {

    void saveImageFile(String recipeId, MultipartFile file) throws IOException;
}
