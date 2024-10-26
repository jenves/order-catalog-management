package br.com.order.catalog.management.repository;

import br.com.order.catalog.management.entity.OrderItemJpaEntity;
import br.com.order.catalog.management.entity.OrderJpaEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItemJpaEntity, UUID> {

}
