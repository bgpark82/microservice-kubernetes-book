package se.magnus.api.composite.product.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ReviewSummary {

    private final int reviewId;
    private final String author;
    private final String subject;
}
