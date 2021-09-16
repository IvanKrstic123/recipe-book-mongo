package com.recipeBook.recipebook.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ControllerExceptionHandler {

    /*@ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NumberFormatException.class)
    public ModelAndView handleBadRequest(Exception e) {
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.addObject("exception", e);
        modelAndView.setViewName("400error");

        return modelAndView;
    }*/
}
