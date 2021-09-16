package com.recipeBook.recipebook.controllers;

import com.recipeBook.recipebook.commands.RecipeCommand;
import com.recipeBook.recipebook.services.ImageService;
import com.recipeBook.recipebook.services.RecipeService;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Controller
public class ImageController {

    private final RecipeService recipeService;
    private final ImageService imageService;

    public ImageController(RecipeService recipeService, ImageService imageService) {
        this.recipeService = recipeService;
        this.imageService = imageService;
    }

    @GetMapping("recipe/{id}/image")
    public String showUploadForm(@PathVariable String id, Model model) {

        if (!ObjectId.isValid(id)) {
            throw new NumberFormatException();
        }
        model.addAttribute("recipe", recipeService.findCommandById(id));

        return "recipe/imageuploadform";
    }

    @PostMapping("recipe/{id}/image")
    public String handleImagePost(@PathVariable String id, @RequestParam("imagefile") MultipartFile file) throws IOException {

        imageService.saveImageFile(id, file).block()    ;

        return "redirect:/recipe/" + id + "/show";
    }

    /*@GetMapping("recipe/{id}/recipeImage")
    public void renderImageFromDB(@PathVariable String id, HttpServletResponse response) throws IOException {
        RecipeCommand recipeCommand = recipeService.findCommandById(id).block();

        if(recipeCommand.getImage() != null) {
            //unboxing
            byte[] byteArray = new byte[recipeCommand.getImage().length];

            int i = 0;
            for (Byte temp : recipeCommand.getImage()) {
                byteArray[i++] = temp;
            }

            response.setContentType("image/jpeg");
            InputStream inputStream = new ByteArrayInputStream(byteArray);
            IOUtils.copy(inputStream, response.getOutputStream());
        }
    }*/
}
