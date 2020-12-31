package se.magnus.microservices.core.review.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@SpringBootTest(webEnvironment = RANDOM_PORT)
class ReviewServiceImplTest {

    @Autowired
    private WebTestClient client;

    @DisplayName("productId로 getReview 호출")
    @Test
    void getReviewByProductId() {

        int productId = 1;

        client.get()
                .uri("/review?productId=" + productId)
                .accept(APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.length()").isEqualTo(3)
                .jsonPath("$.[0].productId").isEqualTo(productId);
    }

    @DisplayName("invalid 파라미터로 getReview 호출 시, Bad Request")
    @Test
    void getReviewInvalidParameter() {

        client.get()
                .uri("/review?productId=invalid-parameter")
                .accept(APPLICATION_JSON)
                .exchange()
                .expectStatus().isEqualTo(BAD_REQUEST)
                .expectHeader().contentType(APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.path").isEqualTo("/review");
    }

    @DisplayName("getReview 호출 시, Not Found")
    @Test
    void getReviewInvalidNotFound() {

        int productId = 213;

        client.get()
                .uri("/review?productId=" + productId)
                .accept(APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.length()").isEqualTo(0);
    }

    @DisplayName("음수 productId로 getReview 호출 시, Not Found")
    @Test
    void getReviewInvalidNegativeParameter() {

        int productId = -1;

        client.get()
                .uri("/review?productId=" + productId)
                .accept(APPLICATION_JSON)
                .exchange()
                .expectStatus().isEqualTo(UNPROCESSABLE_ENTITY)
                .expectHeader().contentType(APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.path").isEqualTo("/review")
                .jsonPath("$.message").isEqualTo("Invalid productId: " + productId);
    }
}