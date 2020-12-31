package se.magnus.microservices.composite.product.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import se.magnus.api.composite.product.domain.ProductAggregate;
import se.magnus.api.composite.product.service.ProductAggregateService;
import se.magnus.api.composite.product.service.ProductCompositeService;
import se.magnus.api.core.product.Product;
import se.magnus.api.core.recommendation.Recommendation;
import se.magnus.api.core.review.Review;
import se.magnus.util.exception.NotFoundException;

import java.util.List;

@RestController
public class ProductCompositeServiceImpl implements ProductCompositeService {

    private final ProductCompositeServiceIntegration integration;
    private final ProductAggregateService productAggregateService;

    @Autowired
    public ProductCompositeServiceImpl(ProductCompositeServiceIntegration integration, ProductAggregateService productAggregateService) {
        this.integration = integration;
        this.productAggregateService = productAggregateService;
    }

    @Override
    public ProductAggregate getProduct(int productId) {

        Product product = integration.getProduct(productId);
        if(product == null) throw new NotFoundException("No product found for productId:" + productId);

        List<Recommendation> recommendations = integration.getRecommendations(productId);
        List<Review> reviews = integration.getReviews(productId);

        return productAggregateService.getProductAggregate(product, recommendations, reviews);
    }
}
