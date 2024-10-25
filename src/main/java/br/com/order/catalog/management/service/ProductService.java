package br.com.order.catalog.management.service;

import br.com.order.catalog.management.dto.ProductDTO;
import br.com.order.catalog.management.entity.Product;
import br.com.order.catalog.management.exceptions.ResourceNotFoundException;
import br.com.order.catalog.management.mapper.ProductMapper;
import br.com.order.catalog.management.repository.ProductRepository;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    private ProductMapper productMapper;

    public ProductDTO getProductById(UUID id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        return productMapper.toDTO(product);
    }

    public Page<ProductDTO> getProducts(Pageable pageable) {
        Page<Product> productsPage = productRepository.findAll(pageable);
        return productsPage.map(productMapper::toDTO);
    }

    public ProductDTO saveProduct(ProductDTO productToBeSaved) {
        Product product = productMapper.toEntity(productToBeSaved);
        return productMapper.toDTO(productRepository.save(product));
    }

    public ProductDTO updateProduct(UUID id, ProductDTO updatedProductDTO) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        existingProduct.setName(updatedProductDTO.getName());
        existingProduct.setPrice(updatedProductDTO.getPrice());
        existingProduct.setType(updatedProductDTO.getType());
        existingProduct.setActive(updatedProductDTO.isActive());

        Product updatedProduct = productRepository.save(existingProduct);
        return productMapper.toDTO(updatedProduct);
    }

    public void deleteProductById(UUID id) {
        if (!productRepository.existsById(id)) {
            throw new ResourceNotFoundException("Product not found");
        }
        productRepository.deleteById(id);
    }

}
