package br.com.order.catalog.management.controller;

import br.com.order.catalog.management.dto.ProductDTO;
import br.com.order.catalog.management.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

import jakarta.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/{id}")
    public EntityModel<ProductDTO> findById(@PathVariable("id") UUID id) {
        ProductDTO productDTO = productService.getProductById(id);
        EntityModel<ProductDTO> resource = EntityModel.of(productDTO);

        Link selfLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ProductController.class).findById(id)).withSelfRel();
        resource.add(selfLink);

        resource.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ProductController.class).findAll(null)).withRel("products"));
        resource.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ProductController.class).update(id, productDTO)).withRel("update"));
        resource.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ProductController.class).deleteById(id)).withRel("delete"));

        return resource;
    }

    @GetMapping
    public Page<ProductDTO> findAll(Pageable pageable) {
        return productService.getProducts(pageable);
    }

    @PostMapping
    public EntityModel<ProductDTO> create(@Valid @RequestBody ProductDTO productToBeSaved) {
        ProductDTO savedProduct = productService.saveProduct(productToBeSaved);
        EntityModel<ProductDTO> resource = EntityModel.of(savedProduct);

        Link selfLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ProductController.class).create(productToBeSaved)).withSelfRel();
        resource.add(selfLink);

        return resource;
    }

    @PutMapping("/{id}")
    public EntityModel<ProductDTO> update(@PathVariable("id") UUID id, @Valid @RequestBody ProductDTO updatedProduct) {
        ProductDTO updatedProductDTO = productService.updateProduct(id, updatedProduct);
        EntityModel<ProductDTO> resource = EntityModel.of(updatedProductDTO);

        Link selfLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ProductController.class).update(id, updatedProduct)).withSelfRel();
        resource.add(selfLink);

        return resource;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") UUID id) {
        productService.deleteProductById(id);
        return ResponseEntity.noContent().build();
    }
}
