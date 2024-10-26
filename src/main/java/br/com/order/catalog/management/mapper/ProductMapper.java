package br.com.order.catalog.management.mapper;

import br.com.order.catalog.management.dto.ProductDTO;
import br.com.order.catalog.management.entity.Product;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    ProductDTO toDTO(Product product);

    Product toEntity(ProductDTO productDTO);
}