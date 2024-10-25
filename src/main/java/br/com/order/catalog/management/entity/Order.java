package br.com.order.catalog.management.entity;

import br.com.order.catalog.management.domain.OrderStatus;
import br.com.order.catalog.management.domain.ProductType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @NotNull(message = "Status is required")
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> itens = new ArrayList<>();

    @Positive(message = "Discount must be positive")
    private Double discount;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public @NotNull(message = "Status is required") OrderStatus getStatus() {
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

    public Double calculateTotal() {
        double totalProducts = itens.stream()
                .filter(item -> item.getProduct().isActive() && item.getProduct().getType() == ProductType.PRODUCT)
                .mapToDouble(OrderItem::getSubtotal)
                .sum();
        double discountAmount = (discount != null ? totalProducts * (discount / 100) : 0);
        return totalProducts - discountAmount;
    }

}
