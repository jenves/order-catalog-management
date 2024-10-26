package br.com.order.catalog.management.controller.product;

import br.com.order.catalog.management.controller.product.model.CreateProductRequest;
import br.com.order.catalog.management.dto.ProductDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;

import jakarta.validation.Valid;

import java.util.UUID;

@RequestMapping("/products")
@Tag(name = "products")
interface ProductController {

  @Operation(summary = "Get an product by its uuid")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Found the user",
          content = {
              @Content(mediaType = "application/json",
                  schema = @Schema(implementation = ProductDTO.class))
          }),
      @ApiResponse(responseCode = "400", description = "Invalid uuid supplied",
          content = @Content),
      @ApiResponse(responseCode = "404", description = "User not found",
          content = @Content
      ),
      @ApiResponse(responseCode = "500", description = "Internal server error",
          content = @Content
      )
  })
  @GetMapping("/{id}")
  EntityModel<ProductDTO> findById(@PathVariable("id") UUID id);

  @GetMapping
  Page<ProductDTO> findAll(Pageable pageable);

  @PostMapping
  EntityModel<ProductDTO> create(@Valid @RequestBody CreateProductRequest request);

  @PutMapping("/{id}")
  EntityModel<ProductDTO> update(@PathVariable("id") UUID id,
      @Valid @RequestBody ProductDTO updatedProduct);

  @DeleteMapping("/{id}")
  ResponseEntity<Void> deleteById(@PathVariable("id") UUID id);
}
