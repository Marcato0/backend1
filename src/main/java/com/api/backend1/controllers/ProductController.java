package com.api.backend1.controllers;


import com.api.backend1.dtos.ProductDto;
import com.api.backend1.dtos.ProductWithCulturesDto;
import com.api.backend1.exceptions.ResourceNotFoundException;
import com.api.backend1.models.ProductModel;
import com.api.backend1.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/product")
public class ProductController{

    @Autowired
    ProductService productService;



    @PostMapping
    public ProductModel save(@RequestBody ProductDto productDto) {
        return productService.createProduct(productDto);
    }


    @GetMapping
    public ResponseEntity<List<ProductModel>> getAllProduct() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(productService
                        .getAllProducts());
    }

    @GetMapping("/search/{name}")
    public ResponseEntity<List<ProductModel>> findByNameContaining(@PathVariable String name) {
        /**
         * Chama o metodo findByNameContaining do service que tem uma lista de produtos com o valor de 'name' no campo name do ProductDto
         * O resultado dessa busca é armazenado numa lista de objetos chamada products.
         */
        List<ProductModel> products = productService.findByNameContaining(name);
        return ResponseEntity.status(HttpStatus.OK).body(products);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteProduct(@PathVariable UUID id) throws ResourceNotFoundException {
        productService.deleteProduct(id);
        return ResponseEntity.status(HttpStatus.OK).body("Product deleted successfully.");
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductModel> updateProduct(@PathVariable UUID id,
                                                                @RequestBody ProductDto productDto) throws ResourceNotFoundException {
        var updatedProduct = productService.updateProduct(id, productDto);
        return ResponseEntity.ok(updatedProduct);
    }




}
