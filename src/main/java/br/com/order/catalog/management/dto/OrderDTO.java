package br.com.order.catalog.management.dto;

import br.com.order.catalog.management.domain.OrderStatus;
import br.com.order.catalog.management.entity.OrderItem;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class OrderDTO {

    private UUID id;

    @NotNull(message = "Status is required")
    private OrderStatus status;

    private List<OrderItem> itens = new ArrayList<>();

    @Positive(message = "Discount must be positive")
    private Double discount;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public List<OrderItem> getItens() {
        return itens;
    }

    public void setItens(List<OrderItem> itens) {
        this.itens = itens;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }
}
