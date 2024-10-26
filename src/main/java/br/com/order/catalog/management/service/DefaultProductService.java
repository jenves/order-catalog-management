package br.com.order.catalog.management.service;

import br.com.order.catalog.management.domain.product.Product;
import br.com.order.catalog.management.dto.ProductDTO;
import br.com.order.catalog.management.entity.ProductJpaEntity;
import br.com.order.catalog.management.exceptions.ResourceNotFoundException;
import br.com.order.catalog.management.mapper.ProductMapper;
import br.com.order.catalog.management.repository.ProductRepository;

import java.util.Set;
import java.util.UUID;

import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DefaultProductService implements ProductService {

  private final static String PRODUCT_NOT_FOUND_ERROR_MESSAGE = "Product not found";

  private final ProductRepository productRepository;

  private final ProductMapper productMapper;

  public DefaultProductService(ProductRepository productRepository, ProductMapper productMapper) {
    this.productRepository = productRepository;
    this.productMapper = productMapper;
  }

  public ProductDTO getProductById(UUID id) {
    ProductJpaEntity productJpaEntity = productRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException(PRODUCT_NOT_FOUND_ERROR_MESSAGE));

    return productMapper.toDTO(productJpaEntity);
  }

  public Page<ProductDTO> getProducts(Pageable pageable) {
    Page<ProductJpaEntity> productsPage = productRepository.findAll(pageable);
    return productsPage.map(productMapper::toDTO);
  }

  @Transactional
  public Product saveProduct(Product product) {
    final var productJpaEntity = productRepository.save(productMapper.toEntity(product));
    return productMapper.toDomain(productJpaEntity);
  }

  @Transactional
  public ProductDTO updateProduct(UUID id, ProductDTO updatedProductDTO) {
    ProductJpaEntity existingProductJpaEntity = productRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException(PRODUCT_NOT_FOUND_ERROR_MESSAGE));

    existingProductJpaEntity.setName(updatedProductDTO.name());
    existingProductJpaEntity.setPrice(updatedProductDTO.price());
    existingProductJpaEntity.setType(updatedProductDTO.type());
    existingProductJpaEntity.setActive(updatedProductDTO.active());

    ProductJpaEntity updatedProductJpaEntity = productRepository.save(existingProductJpaEntity);
    return productMapper.toDTO(updatedProductJpaEntity);
  }

  @Transactional
  public void deleteProductById(UUID id) {
    if (!productRepository.existsById(id)) {
      throw new ResourceNotFoundException(PRODUCT_NOT_FOUND_ERROR_MESSAGE);
    }
    productRepository.deleteById(id);
  }

  @Override
  public Set<Product> getProductsById(Set<UUID> items) {
    return productRepository.findAllById(items).stream().map(productMapper::toDomain).collect(Collectors.toSet());
  }

}
