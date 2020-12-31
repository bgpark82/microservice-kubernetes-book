package se.magnus.microservices.composite.product.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import se.magnus.api.core.product.Product;
import se.magnus.api.core.product.ProductService;
import se.magnus.api.core.recommendation.Recommendation;
import se.magnus.api.core.recommendation.RecommendationService;
import se.magnus.api.core.review.Review;
import se.magnus.api.core.review.ReviewService;
import se.magnus.util.exception.InvalidInputException;
import se.magnus.util.exception.NotFoundException;
import se.magnus.util.http.HttpError;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpMethod.GET;

@Component
@RequiredArgsConstructor
public class ProductCompositeServiceIntegration implements ProductService, RecommendationService, ReviewService {

    private static final Logger LOG = LoggerFactory.getLogger(ProductCompositeServiceIntegration.class);

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    private final String productServiceUrl;
    private final String recommendationServiceUrl;
    private final String reviewServiceUrl;

    @Autowired
    public ProductCompositeServiceIntegration(
            RestTemplate restTemplate,
            ObjectMapper objectMapper,
            @Value("${app.product-service.host}") String productServiceHost,
            @Value("${app.product-service.port}") int productServicePort,
            @Value("${app.recommendation-service.host}") String recommendationServiceHost,
            @Value("${app.recommendation-service.port}") int recommendationServicePort,
            @Value("${app.review-service.host}") String reviewServiceHost,
            @Value("${app.review-service.port}") int reviewServicePort
    ) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
        this.productServiceUrl = "http://" + productServiceHost + ":" + productServicePort + "/product/";
        this.recommendationServiceUrl = "http://" + recommendationServiceHost + ":" + recommendationServicePort + "/recommendation?productId=";
        this.reviewServiceUrl = "http://" + reviewServiceHost + ":" + reviewServicePort + "/review?productId=";
    }

    @Override
    public Product getProduct(int productId) {

        try {
            String url = productServiceUrl + productId;
            LOG.debug("Will call getProduct API on URL: {}", url);

            Product product = restTemplate.getForObject(url, Product.class);
            LOG.debug("Found a product with productId: {}", product);

            return product;

        } catch(HttpClientErrorException ex) {
            switch (ex.getStatusCode()) {
                case NOT_FOUND:
                    throw new NotFoundException(getErrorMessage(ex));
                case UNPROCESSABLE_ENTITY:
                    throw new InvalidInputException(getErrorMessage(ex));
                default:
                    LOG.warn("Got a unexpected HTTP error: {}", ex.getMessage());
                    LOG.warn("Error body: {}", ex.getResponseBodyAsString());
                    throw ex;
            }
        }
    }

    @Override
    public List<Recommendation> getRecommendations(int productId) {

        try {
            String url = recommendationServiceUrl + productId;
            LOG.debug("Will call getRecommendations API on URL: {}", url);

            List<Recommendation> recommendations = restTemplate
                    .exchange(url, GET, null, new ParameterizedTypeReference<List<Recommendation>>() {}).getBody();
            LOG.debug("Found {} recommendations for a product with productId: {}", recommendations.size(), productId);

            return recommendations;

        } catch (HttpClientErrorException ex) {
            LOG.warn("Got an exception while requesting recommendations, return zero recommendations: {}", ex.getMessage());
            return new ArrayList<>();
        }
    }

    @Override
    public List<Review> getReviews(int productId) {

        try {
            String url = reviewServiceUrl + productId;
            LOG.debug("Will call getReviews API on URL: {}", url);

            List<Review> reviews = restTemplate
                    .exchange(url, GET, null, new ParameterizedTypeReference<List<Review>>() {}).getBody();
            LOG.debug("Found {} reviews for a product with id: {}", reviews.size(), productId);

            return reviews;

        } catch (HttpClientErrorException ex) {
            LOG.warn("Got an exception while requesting reviews, return zero reviews: {}", ex.getMessage());
            return new ArrayList<>();
        }
    }

    private String getErrorMessage(HttpClientErrorException ex) {
        try {
            LOG.warn("Error Response Body as String value: {}", ex.getResponseBodyAsString());
            return objectMapper.readValue(ex.getResponseBodyAsString(), HttpError.class).getMessage();
        } catch (JsonProcessingException e) {
            return ex.getMessage();
        }
    }
}
