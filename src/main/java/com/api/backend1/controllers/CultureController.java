package com.api.backend1.controllers;


import com.api.backend1.dtos.CultureDto;
import com.api.backend1.exceptions.ResourceNotFoundException;
import com.api.backend1.models.CultureModel;
import com.api.backend1.services.CultureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/culture")
public class CultureController {

    @Autowired
    CultureService cultureService;


    @PostMapping
    /**
     * Método público que recebe um objeto do tipo CultureDto via HTTP request body
     * e retorna um objeto do tipo CultureModel.
     *
     * @param cultureDto objeto CultureDto enviado pelo cliente via HTTP request body.
     * @return objeto CultureModel criado.
     */
    public CultureModel save(@RequestBody CultureDto cultureDto) {
        return cultureService.createCulture(cultureDto);
    }


    @GetMapping
    /**
     * Método público que retorna uma lista de objetos CultureModel.
     *
     * @return objeto ResponseEntity com status HTTP OK e corpo da resposta contendo a lista de objetos CultureModel.
     */
    public ResponseEntity<List<CultureModel>> getAllCulture() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(cultureService
                        .findAll());
    }

    @GetMapping("/search/{name}")
    /**
     * Método público que recebe um valor 'name' e retorna uma lista de objetos CultureModel que contém esse valor no campo 'name'.
     *
     * @param name valor a ser pesquisado no campo 'name' dos objetos CultureModel.
     * @return objeto ResponseEntity com status HTTP OK e corpo da resposta contendo a lista de objetos CultureModel.
     */
    public ResponseEntity<List<CultureModel>> findByNameContaining(@PathVariable String name) {
        /**
         * Chama o metodo findByNameContaining do service que tem uma lista de culturas com o valor de 'name' no campo name do model
         * O resultado dessa busca é armazenado numa lista de objetos chamada cultures.
         */
        List<CultureModel> cultures = cultureService.findByNameContaining(name);
        return ResponseEntity.status(HttpStatus.OK).body(cultures);
    }


    @DeleteMapping("/{id}")
    /**
     * Método público que recebe o ID de um objeto CultureModel e o remove do banco de dados.
     *
     * @param id ID do objeto CultureModel a ser removido.
     * @return objeto ResponseEntity com status HTTP OK e corpo da resposta contendo a mensagem 'Culture deleted successfully.'.
     */
    public ResponseEntity<Object> deleteCulture(@PathVariable UUID id) throws ResourceNotFoundException {
        cultureService.deleteCulture(id);
        return ResponseEntity.status(HttpStatus.OK).body("Culture deleted successfully.");
    }

    @PutMapping("/{id}")
    /**
     * Método público que recebe o ID de um objeto CultureModel e um objeto CultureDto enviado pelo cliente via HTTP request body.
     * O método atualiza os campos do objeto CultureModel com base nas informações contidas no objeto CultureDto.
     *
     * @param id ID do objeto CultureModel a ser atualizado.
     * @param cultureDto objeto CultureDto enviado pelo cliente via HTTP request body.
     * @return objeto ResponseEntity com status HTTP OK e corpo da resposta contendo o objeto CultureModel atualizado.
     */
    public ResponseEntity<CultureModel> updateCulture(@PathVariable UUID id,
                                                    @RequestBody CultureDto cultureDto)throws ResourceNotFoundException {

        CultureModel culture = cultureService.updateCulture(id, cultureDto);
        return ResponseEntity.status(HttpStatus.OK).body(culture);
    }
}
