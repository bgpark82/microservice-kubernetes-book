package se.magnus.api.composite.product.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@AllArgsConstructor
public class ServiceAddresses {

    private final String cmp;
    private final String pro;
    private final String rev;
    private final String rec;

    public ServiceAddresses() {
        this(null, null, null, null);
    }
}
