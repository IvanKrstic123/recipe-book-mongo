package com.recipeBook.recipebook.repositories.reactive;

import com.recipeBook.recipebook.domain.Category;
import com.recipeBook.recipebook.domain.UnitOfMeasure;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@DataMongoTest
class UnitOfMeasureReactiveRepositoryTest {

    public static final String TABLESPOON = "Tablespoon";
    @Autowired
    UnitOfMeasureReactiveRepository unitOfMeasureReactiveRepository;

    @BeforeEach
    void setUp() {
        unitOfMeasureReactiveRepository.deleteAll().block();
    }

    @Test
    void saveUnitOfMeasure() {
        UnitOfMeasure uom = new UnitOfMeasure();
        uom.setDescription(TABLESPOON);

        unitOfMeasureReactiveRepository.save(uom).block();

        assertEquals(1, unitOfMeasureReactiveRepository.count().block());
    }

    @Test
    void testFindByDescription() {
        UnitOfMeasure uom = new UnitOfMeasure();
        uom.setDescription(TABLESPOON);

        unitOfMeasureReactiveRepository.save(uom).block();

        Category tablespoon = unitOfMeasureReactiveRepository.findByDescription(TABLESPOON).block();

        assertEquals(uom.getDescription(), tablespoon.getDescription());
    }
}