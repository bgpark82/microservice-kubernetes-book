package se.magnus.api.composite.product.service;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import se.magnus.api.composite.product.domain.ProductAggregate;

public interface ProductCompositeService {

    @GetMapping(value = "/product-composite/{productId}", produces = "application/json")
    ProductAggregate getProduct(@PathVariable int productId);
}