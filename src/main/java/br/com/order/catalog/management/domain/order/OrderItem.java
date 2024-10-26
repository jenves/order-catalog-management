package br.com.order.catalog.management.domain.order;


import br.com.order.catalog.management.domain.DomainEntity;
import br.com.order.catalog.management.domain.product.Product;
import java.math.BigDecimal;
import java.util.UUID;

public class OrderItem extends DomainEntity {

  private final Product product;

  private final Integer amount;

  public static OrderItem newOrderItem(final Product product, final Integer amount) {
    final var id = UUID.randomUUID();
    return new OrderItem(id, product, amount);
  }

  public OrderItem(final UUID id, final Product product, final Integer amount) {
    super(id);
    this.product = product;
    this.amount = amount;
  }

  public BigDecimal getSubtotal() {
    return this.product.getPrice().multiply(BigDecimal.valueOf(amount));
  }

  public Product getProduct() {
    return product;
  }

  public Integer getAmount() {
    return amount;
  }

  public boolean isProductActivate() {
    return product.isActive();
  }
}
