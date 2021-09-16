package com.recipeBook.recipebook.services;

import com.recipeBook.recipebook.commands.UnitOfMeasureCommand;
import com.recipeBook.recipebook.converters.UnitOfMeasureToUnitOfMeasureCommand;
import com.recipeBook.recipebook.domain.UnitOfMeasure;
import com.recipeBook.recipebook.repositories.UnitOfMeasureRepository;
import com.recipeBook.recipebook.repositories.reactive.UnitOfMeasureReactiveRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import reactor.core.publisher.Flux;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UnitOfMeasureServiceImplTest {

    @Mock
    UnitOfMeasureReactiveRepository uomRepository;

    UnitOfMeasureService uomService;

    @BeforeEach
    void setUp() {
        uomRepository = Mockito.mock(UnitOfMeasureReactiveRepository.class);
        uomService = new UnitOfMeasureServiceImpl(uomRepository, new UnitOfMeasureToUnitOfMeasureCommand());
    }

    @Test
    void listAllUoms() {
        Set<UnitOfMeasure> uoms = new HashSet<>();

        UnitOfMeasure uom1 = new UnitOfMeasure();
        uom1.setId("1");

        UnitOfMeasure uom2 = new UnitOfMeasure();
        uom2.setId("2");

        uoms.add(uom1);
        uoms.add(uom2);

        when(uomRepository.findAll()).thenReturn(Flux.fromIterable(uoms));

        List<UnitOfMeasureCommand> commands = uomService.listAllUoms().collectList().block();

        assertEquals(2, commands.size());
        verify(uomRepository, times(1)).findAll();
    }
}