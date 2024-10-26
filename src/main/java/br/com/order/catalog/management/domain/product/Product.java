package br.com.order.catalog.management.domain.product;

import br.com.order.catalog.management.domain.DomainEntity;
import java.math.BigDecimal;
import java.util.UUID;

public class Product extends DomainEntity {

  private final String name;

  private final BigDecimal price;

  private final ProductType type;

  private final Boolean active;

  public static Product newProduct(final String name, final BigDecimal price, final ProductType type,
      final Boolean active) {
    var id = UUID.randomUUID();
    return new Product(id, name, price, type, active);
  }

  public Product(final UUID id, final String name, final BigDecimal price, final ProductType type,
      final Boolean active) {
    super(id);
    this.name = name;
    this.price = price;
    this.type = type;
    this.active = active;
  }

  public String getName() {
    return name;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public ProductType getType() {
    return type;
  }

  public Boolean isActive() {
    return active;
  }
}
