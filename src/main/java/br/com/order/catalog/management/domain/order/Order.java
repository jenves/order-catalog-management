package br.com.order.catalog.management.domain.order;

import br.com.order.catalog.management.domain.DomainEntity;
import br.com.order.catalog.management.domain.product.ProductType;
import br.com.order.catalog.management.exceptions.InvalidItemExceptionException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import jdk.jshell.Snippet.Status;

public class Order extends DomainEntity {

  private final OrderStatus status;

  private final Set<OrderItem> items;

  private final Integer discount;

  private final BigDecimal total;

  private final static Integer MONEY_SCALE = 2;

  public static Order newOrder(final OrderStatus status, final Set<OrderItem> items,
      final Integer discount) {
    final var id = UUID.randomUUID();
    return new Order(id, status, items, discount);
  }

  public Order(final UUID id, final OrderStatus status, final Set<OrderItem> items,
      final Integer discount) {
    super(id);
    validateItems(items);
    this.status = status;
    this.items = items;
    this.discount = discount;
    this.total = calculateTotal();
  }

  private void validateItems(Set<OrderItem> items) {
    items.forEach(item -> {
      if (!item.isProductActivate()) {
        throw new InvalidItemExceptionException("O produto encontra-se inativado");
      }
    });
  }

  private BigDecimal calculateTotal() {
    final var totalWithoutProducts = calculateTotalWithOutProducts();
    final var totalByProducts = calculateTotalByProducts();
    final var totalByProductsAfterDiscount =
        Objects.equals(status, OrderStatus.OPEN) ? calculateDiscount(totalByProducts)
            : totalByProducts;
    return totalWithoutProducts.add(totalByProductsAfterDiscount)
        .setScale(MONEY_SCALE, RoundingMode.HALF_EVEN);
  }

  private BigDecimal calculateTotalWithOutProducts() {
    return items.stream()
        .filter(item -> item.getProduct().isActive()
            && item.getProduct().getType() != ProductType.PRODUCT)
        .map(OrderItem::getSubtotal)
        .reduce(BigDecimal.ZERO, BigDecimal::add);
  }

  private BigDecimal calculateTotalByProducts() {
    return items.stream()
        .filter(item -> item.getProduct().isActive()
            && item.getProduct().getType() == ProductType.PRODUCT)
        .map(OrderItem::getSubtotal)
        .reduce(BigDecimal.ZERO, BigDecimal::add);
  }

  private BigDecimal calculateDiscount(BigDecimal totalProducts) {
    BigDecimal discountAmount = Objects.nonNull(discount) ?
        totalProducts.multiply(
            new BigDecimal(discount).divide(new BigDecimal(100), MONEY_SCALE,
                RoundingMode.HALF_EVEN)) :
        BigDecimal.ZERO;

    return totalProducts.subtract(discountAmount);
  }

  public OrderStatus getStatus() {
    return status;
  }

  public Set<OrderItem> getItems() {
    return items;
  }

  public Integer getDiscount() {
    return discount;
  }

  public BigDecimal getTotal() {
    return total;
  }
}
