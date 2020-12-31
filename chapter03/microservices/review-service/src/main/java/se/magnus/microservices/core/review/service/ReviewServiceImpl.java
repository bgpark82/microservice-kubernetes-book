package se.magnus.microservices.core.review.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import se.magnus.api.core.review.Review;
import se.magnus.api.core.review.ReviewService;
import se.magnus.util.exception.InvalidInputException;
import se.magnus.util.exception.NotFoundException;
import se.magnus.util.util.ServiceUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
public class ReviewServiceImpl implements ReviewService {

    private static final Logger LOG = LoggerFactory.getLogger(ReviewServiceImpl.class);

    private final ServiceUtil serviceUtil;

    @Autowired
    public ReviewServiceImpl(ServiceUtil serviceUtil) {
        this.serviceUtil = serviceUtil;
    }

    @Override
    public List<Review> getReviews(int productId) {

        if(productId < 0) throw new InvalidInputException("Invalid productId: " + productId);

        if(productId == 213) {
            LOG.debug("No reviews found for productId: {}", productId);
            return new ArrayList<>();
        }

        List<Review> reviews = Arrays.asList(
                new Review(productId, 1, "Author1", "Subject1", "Content1", serviceUtil.getServiceAddress()),
                new Review(productId, 2, "Author2", "Subject2", "Content2", serviceUtil.getServiceAddress()),
                new Review(productId, 3, "Author3", "Subject3", "Content3", serviceUtil.getServiceAddress())
        );

        LOG.debug("/reviews response size: {}", reviews.size());

        return reviews;
    }
}
