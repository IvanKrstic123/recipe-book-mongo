package com.recipeBook.recipebook.controllers;

import com.recipeBook.recipebook.config.WebConfig;
import com.recipeBook.recipebook.domain.Recipe;
import com.recipeBook.recipebook.services.RecipeService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.server.RouterFunction;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class RouterFunctionTest {

    public static final String ID = "1";
    public static final String DESCRIPTION = "Very Good";

    private WebTestClient webTestClient;

    @Mock
    RecipeService recipeService;

    /*  maybe like this is happy path scenarion
    @Autowired
    private ApplicationContext context;*/

    @BeforeEach
    void setUp() {
        recipeService = Mockito.mock(RecipeService.class);

        WebConfig webConfig = new WebConfig();
        RouterFunction<?> routerFunction = webConfig.routes(recipeService);

        webTestClient = WebTestClient.bindToRouterFunction(routerFunction).build();
    }
    @Test
    void testGetRecipes() {

        when(recipeService.getRecipes()).thenReturn(Flux.just());

        webTestClient.get()
                .uri("/api/recipes")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void testGetRecipesWithData() {
        Recipe recipe = new Recipe();
        recipe.setId(ID);
        recipe.setDescription(DESCRIPTION);

        when(recipeService.getRecipes()).thenReturn(Flux.just(recipe));

        webTestClient.get()
                .uri("/api/recipes")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Recipe.class)
                .value(userResponse -> {
                            Assertions.assertThat(userResponse.get(0).getId()).isEqualTo(ID);
                            Assertions.assertThat(userResponse.get(0).getDescription()).isEqualTo(DESCRIPTION);
                        }
                );
    }
}