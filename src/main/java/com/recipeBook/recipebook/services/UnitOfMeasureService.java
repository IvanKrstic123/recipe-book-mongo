package com.recipeBook.recipebook.services;

import com.recipeBook.recipebook.commands.UnitOfMeasureCommand;

import java.util.Set;

public interface UnitOfMeasureService {

    Set<UnitOfMeasureCommand> listAllUoms();
}
