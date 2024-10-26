package br.com.order.catalog.management.controller.order.model;

import br.com.order.catalog.management.domain.order.OrderStatus;
import br.com.order.catalog.management.domain.order.PreOrderItem;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.List;

public record CreateOrderRequest(
    @NotNull(message = "Status is required") OrderStatus status,
    List<PreOrderItem> items,
    @Positive(message = "Discount must be positive") Integer discount
) {

}
