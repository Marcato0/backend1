package com.api.backend1.controllers;


import com.api.backend1.dtos.ProductDto;
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
    /**
     * Método público que recebe um objeto do tipo ProductDto via HTTP request body
     * e retorna um objeto do tipo ProductModel.
     *
     * @param productDto objeto ProductDto enviado pelo cliente via HTTP request body.
     * @return objeto ProductModel criado e persistido no banco de dados.
     */
    public ProductModel save(@RequestBody ProductDto productDto) {
        return productService.createProduct(productDto);
    }


    @GetMapping
    /**
     * Método público que retorna uma lista de todos os objetos ProductModel.
     *
     * @return lista de objetos ProductModel.
     */
    public ResponseEntity<List<ProductModel>> getAllProduct() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(productService
                        .getAllProducts());
    }

    @GetMapping("/search/{name}")
    /**
     * Método público que recebe uma string 'name' via HTTP path variable e retorna
     * uma lista de objetos ProductModel com o valor de 'name' no campo 'name' do objeto ProductDto.
     *
     * @param name valor para busca do campo 'name' do objeto ProductDto.
     * @return lista de objetos ProductModel com 'name' no campo 'name' do objeto ProductDto.
     */
    public ResponseEntity<List<ProductModel>> findByNameContaining(@PathVariable String name) {
        /**
         * Chama o metodo findByNameContaining do service que tem uma lista de produtos com o valor de 'name' no campo name do ProductDto
         * O resultado dessa busca é armazenado numa lista de objetos chamada products.
         */
        List<ProductModel> products = productService.findByNameContaining(name);
        return ResponseEntity.status(HttpStatus.OK).body(products);
    }


    @DeleteMapping("/{id}")
    /**
     * Método público que recebe um UUID via HTTP path variable e exclui o objeto ProductModel correspondente.
     *
     * @param id UUID do objeto ProductModel a ser excluído.
     * @return mensagem de sucesso da exclusão.
     */
    public ResponseEntity<Object> deleteProduct(@PathVariable UUID id) throws ResourceNotFoundException {
        productService.deleteProduct(id);
        return ResponseEntity.status(HttpStatus.OK).body("Product deleted successfully.");
    }

    @PutMapping("/{id}")
    /**
     * Método público que recebe um UUID via HTTP path variable e um objeto do tipo ProductDto
     * via HTTP request body e atualiza o objeto ProductModel correspondente.
     *
     * @param id         UUID do objeto ProductModel a ser atualizado.
     * @param productDto objeto ProductDto enviado pelo cliente via HTTP request body.
     * @return objeto ProductModel atualizado.
     */
    public ResponseEntity<ProductModel> updateProduct(@PathVariable UUID id,
                                                                @RequestBody ProductDto productDto) throws ResourceNotFoundException {
        var updatedProduct = productService.updateProduct(id, productDto);
        return ResponseEntity.ok(updatedProduct);
    }

}
