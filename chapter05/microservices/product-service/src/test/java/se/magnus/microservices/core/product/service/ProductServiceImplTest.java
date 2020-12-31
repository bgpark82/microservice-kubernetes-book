package se.magnus.microservices.core.product.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@SpringBootTest(webEnvironment = RANDOM_PORT)
class ProductServiceImplTest {

    @Autowired
    private WebTestClient client;

    @DisplayName("productId로 getProduct 호출")
    @Test
    void getProductById() {

        int productId = 1;

        client.get()
                .uri("/product/" + productId)
                .accept(APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.productId").isEqualTo(productId);
    }

    @DisplayName("Invalid 파라미터로 getProduct 호출 시, Bad Request")
    @Test
    void getProductInvalidParameterString() {

        client.get()
                .uri("/product/invalid-parameter")
                .accept(APPLICATION_JSON)
                .exchange()
                .expectStatus().isEqualTo(BAD_REQUEST)
                .expectHeader().contentType(APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.path").isEqualTo("/product/invalid-parameter")
                .jsonPath("$.message").isEqualTo("Type mismatch.");
    }

    @DisplayName("없는 파라미터로 getProduct 호출 시, Not Found")
    @Test
    void getProductNotFound() {

        int productId = 13;

        client.get()
                .uri("/product/" + productId)
                .accept(APPLICATION_JSON)
                .exchange()
                .expectStatus().isEqualTo(NOT_FOUND)
                .expectHeader().contentType(APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.path").isEqualTo("/product/" + productId)
                .jsonPath("$.message").isEqualTo("No product found for productId: " + productId);
    }

    @DisplayName("음수로 getProduct 호출 시, Invalid Parameter")
    @Test
    void getProductInvalidNegativeParameter() {

        int productId = -1;

        client.get()
                .uri("/product/" + productId)
                .accept(APPLICATION_JSON)
                .exchange()
                .expectStatus().isEqualTo(UNPROCESSABLE_ENTITY)
                .expectHeader().contentType(APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.path").isEqualTo("/product/" + productId)
                .jsonPath("$.message").isEqualTo("Invalid productId: " + productId);
    }
}