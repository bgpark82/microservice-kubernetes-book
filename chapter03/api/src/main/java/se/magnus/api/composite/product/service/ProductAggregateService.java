package se.magnus.api.composite.product.service;

import se.magnus.api.composite.product.domain.ProductAggregate;
import se.magnus.api.core.product.Product;
import se.magnus.api.core.recommendation.Recommendation;
import se.magnus.api.core.review.Review;

import java.util.List;

public interface ProductAggregateService {

    ProductAggregate getProductAggregate(Product product, List<Recommendation> recommendations, List<Review> reviews);
}
