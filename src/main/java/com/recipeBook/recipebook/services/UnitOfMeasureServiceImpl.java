package com.recipeBook.recipebook.services;

import com.recipeBook.recipebook.commands.UnitOfMeasureCommand;
import com.recipeBook.recipebook.converters.UnitOfMeasureToUnitOfMeasureCommand;
import com.recipeBook.recipebook.repositories.UnitOfMeasureRepository;
import com.recipeBook.recipebook.repositories.reactive.UnitOfMeasureReactiveRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UnitOfMeasureServiceImpl implements UnitOfMeasureService{

    private final UnitOfMeasureReactiveRepository uomsRespository;
    private final UnitOfMeasureToUnitOfMeasureCommand converter;

    public UnitOfMeasureServiceImpl(UnitOfMeasureReactiveRepository uomsRespository, UnitOfMeasureToUnitOfMeasureCommand converter) {
        this.uomsRespository = uomsRespository;
        this.converter = converter;
    }

    @Override
    public Flux<UnitOfMeasureCommand> listAllUoms() {

        return uomsRespository.findAll()
                .map(converter::convert);

       /* return StreamSupport.stream(uomsRespository.findAll()
                .spliterator(), false)
                .map(converter::convert)
                .collect(Collectors.toSet());*/
    }
}
