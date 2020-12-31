package se.magnus.microservices.core.recommendation.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@SpringBootTest(webEnvironment = RANDOM_PORT)
class RecommendationServiceImplTest {

    @Autowired
    private WebTestClient client;

    @DisplayName("productId로 getRecommendation 호출")
    @Test
    void getRecommendationByProductId() {

        int productId = 1;

        client.get()
                .uri("/recommendation?productId=" + productId)
                .accept(APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.length()").isEqualTo(3)
                .jsonPath("$.[0].productId").isEqualTo(productId);
    }


    @DisplayName("Invalid 파라미터로 getRecommendation 호출")
    @Test
    void getRecommendationInvalidParameter() {

        client.get()
                .uri("/recommendation?productId=invalid-parameter")
                .accept(APPLICATION_JSON)
                .exchange()
                .expectStatus().isEqualTo(BAD_REQUEST)
                .expectHeader().contentType(APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.path").isEqualTo("/recommendation")
                .jsonPath("$.message").isEqualTo("Type mismatch.");
    }

    @DisplayName("없는 파라미터로 getRecommendation 호출 시 Not Found")
    @Test
    void getRecommendationNotFound() {

        int productId = 113;

        client.get()
                .uri("/recommendation?productId=" + productId)
                .accept(APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.length()").isEqualTo(0);
    }

    @DisplayName("음수 파라미터로 getRecommendation 호출 시 Invalid")
    @Test
    void getRecommendationInvalidNegativeParameter() {

        int productId = -1;

        client.get()
                .uri("/recommendation?productId=" + productId)
                .accept(APPLICATION_JSON)
                .exchange()
                .expectStatus().isEqualTo(UNPROCESSABLE_ENTITY)
                .expectHeader().contentType(APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.path").isEqualTo("/recommendation")
                .jsonPath("$.message").isEqualTo("Invalid productId: " + productId);
    }
}