package br.com.order.catalog.management.entity;

import br.com.order.catalog.management.domain.order.OrderStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;


@Entity
@Table(name = "orders")
public class OrderJpaEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID id;

  @NotNull(message = "Status is required")
  @Enumerated(EnumType.STRING)
  private OrderStatus status;

  @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
  private List<OrderItemJpaEntity> items;

  @Positive(message = "Discount must be positive")
  private Integer discount;

  private BigDecimal total;

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

  public List<OrderItemJpaEntity> getItems() {
    return items;
  }

  public void setItems(List<OrderItemJpaEntity> items) {
    this.items = items;
  }

  public @Positive(message = "Discount must be positive") Integer getDiscount() {
    return discount;
  }

  public void setDiscount(
      @Positive(message = "Discount must be positive") Integer discount) {
    this.discount = discount;
  }

  public BigDecimal getTotal() {
    return total;
  }

  public void setTotal(BigDecimal total) {
    this.total = total;
  }
}
