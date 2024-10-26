package br.com.order.catalog.management.domain.order;

import java.util.UUID;

public record PreOrderItem(
    UUID id,
    Integer amount
) {

}
