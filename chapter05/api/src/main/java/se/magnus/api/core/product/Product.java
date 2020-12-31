package se.magnus.api.core.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@AllArgsConstructor
public class Product {

    private final int productId;
    private final String name;
    private final int weight;
    private final String serviceAddress;

    public Product() {
        this(0, null, 0, null);
    }
}

