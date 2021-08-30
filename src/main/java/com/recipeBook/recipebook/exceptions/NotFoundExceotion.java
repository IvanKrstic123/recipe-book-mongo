package com.recipeBook.recipebook.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundExceotion extends RuntimeException {

    public NotFoundExceotion() {
    }

    public NotFoundExceotion(String message) {
        super(message);
    }

    public NotFoundExceotion(String message, Throwable cause) {
        super(message, cause);
    }
}
