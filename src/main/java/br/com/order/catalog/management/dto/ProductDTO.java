package br.com.order.catalog.management.dto;

import br.com.order.catalog.management.domain.product.ProductType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.UUID;

public record ProductDTO(
        UUID id,

        @NotBlank(message = "Product name is required")
        String name,

        @NotNull(message = "Product price is required")
        @Positive(message = "Product price must be positive")
        BigDecimal price,

        @NotNull(message = "Product type is required")
        ProductType type,

        @NotNull(message = "Product active is required")
        Boolean active
) {

}

