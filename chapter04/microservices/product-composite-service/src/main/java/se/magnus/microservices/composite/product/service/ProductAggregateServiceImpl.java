package se.magnus.microservices.composite.product.service;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.magnus.api.composite.product.domain.ProductAggregate;
import se.magnus.api.composite.product.domain.RecommendationSummary;
import se.magnus.api.composite.product.domain.ReviewSummary;
import se.magnus.api.composite.product.domain.ServiceAddresses;
import se.magnus.api.composite.product.service.ProductAggregateService;
import se.magnus.api.core.product.Product;
import se.magnus.api.core.recommendation.Recommendation;
import se.magnus.api.core.review.Review;
import se.magnus.util.util.ServiceUtil;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductAggregateServiceImpl implements ProductAggregateService {

    private final ServiceUtil serviceUtil;

    @Autowired
    public ProductAggregateServiceImpl(ServiceUtil serviceUtil) {
        this.serviceUtil = serviceUtil;
    }

    @Override
    public ProductAggregate getProductAggregate(Product product, List<Recommendation> recommendations, List<Review> reviews) {
        return new ProductAggregate(
                product.getProductId(),
                product.getName(),
                product.getWeight(),
                getRecommendationSummaries(recommendations),
                getReviewSummaries(reviews),
                getServiceAddresses(product, recommendations, reviews));
    }

    private List<ReviewSummary> getReviewSummaries(List<Review> reviews) {
        return (reviews == null) ? null :
                reviews.stream()
                        .map(r -> new ReviewSummary(r.getReviewId(), r.getAuthor(), r.getSubject()))
                        .collect(Collectors.toList());
    }

    private List<RecommendationSummary> getRecommendationSummaries(List<Recommendation> recommendations) {
        return (recommendations == null) ? null :
                recommendations.stream()
                        .map(r -> new RecommendationSummary(r.getRecommendationId(), r.getAuthor(), r.getRate()))
                        .collect(Collectors.toList());
    }

    private ServiceAddresses getServiceAddresses(Product product, List<Recommendation> recommendations, List<Review> reviews) {
        String serviceAddress = serviceUtil.getServiceAddress();
        String productAddress = product.getServiceAddress();
        String reviewAddress = (reviews != null && reviews.size() > 0) ? reviews.get(0).getServiceAddress() : "";
        String recommendationAddress = (recommendations != null && recommendations.size() > 0) ? recommendations.get(0).getServiceAddress() : "";

        return new ServiceAddresses(serviceAddress, productAddress, reviewAddress, recommendationAddress);
    }
}
