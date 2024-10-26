package br.com.order.catalog.management.domain;

import java.util.Objects;
import java.util.UUID;

public abstract class DomainEntity {

  private final UUID id;

  protected DomainEntity(UUID id) {
    this.id = id;
  }

  public UUID getId() {
    return Objects.requireNonNull(id, "'id' should not be null");
  }

  @Override
  public boolean equals(Object object) {
    if (this == object) {
      return true;
    }
    if (object == null || getClass() != object.getClass()) {
      return false;
    }
    DomainEntity that = (DomainEntity) object;
    return Objects.equals(id, that.id);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(id);
  }
}
