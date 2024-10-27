package br.com.order.catalog.management.mapper;

import br.com.order.catalog.management.controller.product.model.CreateProductRequest;
import br.com.order.catalog.management.domain.product.Product;
import br.com.order.catalog.management.dto.ProductDTO;
import br.com.order.catalog.management.entity.ProductJpaEntity;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ProductMapper {

  ProductDTO toDTO(ProductJpaEntity productJpaEntity);

  ProductDTO toDTO(Product product);

  ProductJpaEntity toEntity(Product product);

  Product toDomain(ProductJpaEntity jpaEntity);

  default Product toDomain(CreateProductRequest request) {
    final var name = request.name();
    final var type = request.type();
    final var price = request.price();
    final var active = request.active();
    return Product.newProduct(name, price, type, active);
  }
}