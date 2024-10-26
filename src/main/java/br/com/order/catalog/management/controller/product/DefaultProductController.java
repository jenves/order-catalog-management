package br.com.order.catalog.management.controller.product;

import br.com.order.catalog.management.controller.product.model.CreateProductRequest;
import br.com.order.catalog.management.dto.ProductDTO;
import br.com.order.catalog.management.mapper.ProductMapper;
import br.com.order.catalog.management.service.ProductService;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
public class DefaultProductController implements ProductController {

  private final ProductService productService;

  private final ProductMapper productMapper;

  public DefaultProductController(ProductService productService, ProductMapper productMapper) {
    this.productService = productService;
    this.productMapper = productMapper;
  }

  public EntityModel<ProductDTO> findById(UUID id) {
    ProductDTO productDTO = productService.getProductById(id);
    EntityModel<ProductDTO> resource = EntityModel.of(productDTO);

    Link selfLink = WebMvcLinkBuilder.linkTo(
        WebMvcLinkBuilder.methodOn(DefaultProductController.class).findById(id)).withSelfRel();
    resource.add(selfLink);

    resource.add(
        WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(DefaultProductController.class).findAll(null))
            .withRel("products"));
    resource.add(WebMvcLinkBuilder.linkTo(
            WebMvcLinkBuilder.methodOn(DefaultProductController.class).update(id, productDTO))
        .withRel("update"));
    resource.add(
        WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(DefaultProductController.class).deleteById(id))
            .withRel("delete"));

    return resource;
  }

  public Page<ProductDTO> findAll(Pageable pageable) {
    return productService.getProducts(pageable);
  }

  public EntityModel<ProductDTO> create(CreateProductRequest request) {
    final var product = productService.saveProduct(productMapper.toDomain(request));
    EntityModel<ProductDTO> resource = EntityModel.of(productMapper.toDTO(product));

    Link selfLink = WebMvcLinkBuilder.linkTo(
            WebMvcLinkBuilder.methodOn(DefaultProductController.class).create(request))
        .withSelfRel();
    resource.add(selfLink);

    return resource;
  }

  public EntityModel<ProductDTO> update(UUID id, ProductDTO updatedProduct) {
    ProductDTO updatedProductDTO = productService.updateProduct(id, updatedProduct);
    EntityModel<ProductDTO> resource = EntityModel.of(updatedProductDTO);

    Link selfLink = WebMvcLinkBuilder.linkTo(
            WebMvcLinkBuilder.methodOn(DefaultProductController.class).update(id, updatedProduct))
        .withSelfRel();
    resource.add(selfLink);

    return resource;
  }

  public ResponseEntity<Void> deleteById(UUID id) {
    productService.deleteProductById(id);
    return ResponseEntity.noContent().build();
  }
}
