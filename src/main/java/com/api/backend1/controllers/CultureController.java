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
    public CultureModel save(@RequestBody CultureDto cultureDto) {
        return cultureService.createCulture(cultureDto);
    }


    @GetMapping
    public ResponseEntity<List<CultureModel>> getAllCulture() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(cultureService
                        .findAll());
    }

    @GetMapping("/search/{name}")
    public ResponseEntity<List<CultureModel>> findByNameContaining(@PathVariable String name) {
        /**
         * Chama o metodo findByNameContaining do service que tem uma lista de culturas com o valor de 'name' no campo name do model
         * O resultado dessa busca é armazenado numa lista de objetos chamada cultures.
         */
        List<CultureModel> cultures = cultureService.findByNameContaining(name);
        return ResponseEntity.status(HttpStatus.OK).body(cultures);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteCulture(@PathVariable UUID id) throws ResourceNotFoundException {
        cultureService.deleteCulture(id);
        return ResponseEntity.status(HttpStatus.OK).body("Culture deleted successfully.");
    }

    @PutMapping("/{id}")
    public ResponseEntity<CultureModel> updateCulture(@PathVariable UUID id,
                                                    @RequestBody CultureDto cultureDto)
            throws ResourceNotFoundException {

        CultureModel culture = cultureService.updateCulture(id, cultureDto);
        return ResponseEntity.status(HttpStatus.OK).body(culture);
    }




}
