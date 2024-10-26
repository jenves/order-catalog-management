package br.com.order.catalog.management.service;

import br.com.order.catalog.management.domain.product.Product;
import br.com.order.catalog.management.dto.ProductDTO;

import java.util.Set;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface ProductService {

  ProductDTO getProductById(UUID id);

  Page<ProductDTO> getProducts(Pageable pageable);

  Product saveProduct(Product product);

  ProductDTO updateProduct(UUID id, ProductDTO updatedProductDTO);

  void deleteProductById(UUID id);

  Set<Product> getProductsById(Set<UUID> items);
}
