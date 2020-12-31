package se.magnus.microservices.core.recommendation.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import se.magnus.api.core.recommendation.Recommendation;
import se.magnus.api.core.recommendation.RecommendationService;
import se.magnus.util.exception.InvalidInputException;
import se.magnus.util.exception.NotFoundException;
import se.magnus.util.util.ServiceUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
public class RecommendationServiceImpl implements RecommendationService {

    private static final Logger LOG = LoggerFactory.getLogger(RecommendationServiceImpl.class);

    private final ServiceUtil serviceUtil;

    @Autowired
    public RecommendationServiceImpl(ServiceUtil serviceUtil) {
        this.serviceUtil = serviceUtil;
    }

    @Override
    public List<Recommendation> getRecommendations(int productId) {

        if(productId < 0) {
            throw new InvalidInputException("Invalid productId: " + productId);
        }

        if(productId == 113) {
            LOG.debug("No recommendations found for productId: {}", productId);
            return new ArrayList<>();
        }

        List<Recommendation> recommendations = Arrays.asList(
                new Recommendation(productId, 1, "Author 1", 1, "Content 1", serviceUtil.getServiceAddress()),
                new Recommendation(productId, 2, "Author 2", 2, "Content 2", serviceUtil.getServiceAddress()),
                new Recommendation(productId, 3, "Author 3", 3, "Content 3", serviceUtil.getServiceAddress()));

        LOG.debug("/recommedation response size : {}" + recommendations.size());

        return recommendations;
    }
}
