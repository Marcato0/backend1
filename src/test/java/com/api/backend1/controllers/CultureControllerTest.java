package com.api.backend1.controllers;

import com.api.backend1.dtos.CultureDto;
import com.api.backend1.models.CultureModel;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringJUnitConfig
@SpringBootTest
@ActiveProfiles("test")
@Transactional
class CultureControllerTest {

    @Autowired
    CultureController cultureController;

    @Autowired
    CultureDto cultureDto;

    @Test
    @DisplayName("Cadastrar uma cultura")
    void save() {
        // Cria um objeto CultureDto para enviar ao método save() do controller
        cultureDto.setName("Milho");

        // Chama o método save() do controller passando o objeto CultureDto
        CultureModel savedCulture = cultureController.save(cultureDto);

        // Verifica se o objeto retornado não é nulo
        assertNotNull(savedCulture);
        assertNotNull(savedCulture.getId());
        assertEquals("Milho", savedCulture.getName());
    }

    @Test
    void getAllCulture() {
    }

    @Test
    void findByNameContaining() {
    }

    @Test
    void deleteCulture() {
    }

    @Test
    void updateCulture() {
    }
}
