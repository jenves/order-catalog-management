package br.com.order.catalog.management.service;

import br.com.order.catalog.management.dto.ProductDTO;
import br.com.order.catalog.management.entity.Product;
import br.com.order.catalog.management.exceptions.ResourceNotFoundException;
import br.com.order.catalog.management.mapper.ProductMapper;
import br.com.order.catalog.management.repository.ProductRepository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.stereotype.Service;

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
    Product product = productRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException(PRODUCT_NOT_FOUND_ERROR_MESSAGE));

    return productMapper.toDTO(product);
  }

  public Page<ProductDTO> getProducts(Pageable pageable) {
    Page<Product> productsPage = productRepository.findAll(pageable);
    return productsPage.map(productMapper::toDTO);
  }

  public ProductDTO saveProduct(ProductDTO productToBeSaved) {
    Product product = productMapper.toEntity(productToBeSaved);
    Product productSaved = productRepository.save(product);
    return productMapper.toDTO(productSaved);
  }

  public ProductDTO updateProduct(UUID id, ProductDTO updatedProductDTO) {
    Product existingProduct = productRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException(PRODUCT_NOT_FOUND_ERROR_MESSAGE));

    existingProduct.setName(updatedProductDTO.name());
    existingProduct.setPrice(updatedProductDTO.price());
    existingProduct.setType(updatedProductDTO.type());
    existingProduct.setActive(updatedProductDTO.active());

    Product updatedProduct = productRepository.save(existingProduct);
    return productMapper.toDTO(updatedProduct);
  }

  public void deleteProductById(UUID id) {
    if (!productRepository.existsById(id)) {
      throw new ResourceNotFoundException(PRODUCT_NOT_FOUND_ERROR_MESSAGE);
    }
    productRepository.deleteById(id);
  }

}
