package se.magnus.api.core.recommendation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@AllArgsConstructor
public class Recommendation {

    private final int productId;
    private final int recommendationId;
    private final String author;
    private final int rate;
    private final String content;
    private final String serviceAddress;

    public Recommendation() {
        this(0, 0, null, 0, null, null);
    }
}