package br.com.order.catalog.management.mapper;

import br.com.order.catalog.management.dto.ProductDTO;
import br.com.order.catalog.management.entity.Product;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    @Autowired
    private ModelMapper modelMapper;

    public ProductDTO toDTO(Product product) {
        return modelMapper.map(product, ProductDTO.class);
    }

    public Product toEntity(ProductDTO productDTO) {
        return modelMapper.map(productDTO, Product.class);
    }
}