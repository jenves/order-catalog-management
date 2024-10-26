package br.com.order.catalog.management.service;

import br.com.order.catalog.management.dto.ProductDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface ProductService {

  ProductDTO getProductById(UUID id);

  Page<ProductDTO> getProducts(Pageable pageable);

  ProductDTO saveProduct(ProductDTO productToBeSaved);

  ProductDTO updateProduct(UUID id, ProductDTO updatedProductDTO);

  void deleteProductById(UUID id);

}
