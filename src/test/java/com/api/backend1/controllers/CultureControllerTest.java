package com.api.backend1.controllers;


import com.api.backend1.dtos.CultureDto;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest

//Usar meu banco de dados para os testes
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)

//Usar application-test.properties
@ActiveProfiles("test")
class CultureControllerTest {

    @Autowired
    CultureController cultureController;

    @Autowired
    CultureDto cultureDto;

    @Test
    @DisplayName("Cadastrar uma cultura")
    @Transactional
    void save() {
       var culture = cultureController.save(cultureDto);
       assertThat(culture).isNull();
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