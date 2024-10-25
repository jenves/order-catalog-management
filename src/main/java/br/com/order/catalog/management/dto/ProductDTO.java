package br.com.order.catalog.management.dto;

import br.com.order.catalog.management.domain.ProductType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.UUID;

public class ProductDTO {

    public ProductDTO() {}

    private UUID id;

    @NotBlank(message = "Product name is required")
    private String name;

    @NotNull(message = "Product price is required")
    @Positive(message = "Product price must be positive")
    private Double price;

    @NotNull(message = "Product type is required")
    private ProductType type;

    @NotNull(message = "Product active is required")
    private Boolean active;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public ProductType getType() {
        return type;
    }

    public void setType(ProductType type) {
        this.type = type;
    }

    public Boolean isActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
