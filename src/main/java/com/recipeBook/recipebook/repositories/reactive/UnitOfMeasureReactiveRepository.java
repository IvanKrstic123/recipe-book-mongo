package com.recipeBook.recipebook.repositories.reactive;

import com.recipeBook.recipebook.domain.Category;
import com.recipeBook.recipebook.domain.UnitOfMeasure;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface UnitOfMeasureReactiveRepository extends ReactiveMongoRepository<UnitOfMeasure, String> {

    Mono<Category> findByDescription(String description);
}
