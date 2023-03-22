package com.api.backend1.controllers;

import com.api.backend1.dtos.CultureDto;
import com.api.backend1.exceptions.DuplicateObjNameException;
import com.api.backend1.models.CultureModel;
import com.api.backend1.repositories.CultureRepository;
import com.api.backend1.services.CultureService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@SpringJUnitConfig
@SpringBootTest
@ActiveProfiles("test")
@Transactional
class CultureControllerTest {

    @Autowired
    CultureController cultureController;

    @Autowired
    CultureDto cultureDto;

    @Autowired
    CultureService cultureService;

    @Autowired
    CultureRepository cultureRepository;


    @Test
    @DisplayName("Cadastrar uma cultura")
    void saveOneCulture() {
        // Cria um objeto CultureDto com o nome "Milho"
        CultureDto culture1 = new CultureDto();
        culture1.setName("Milho");

        // Cadastra a cultura com sucesso
        CultureModel savedCulture1 = cultureController.save(culture1);
        assertNotNull(savedCulture1);
    }

    @Test
    @DisplayName("Cadastrar duas culturas com nomes diferentes")
    void saveTwoDifferentCultures() {
        // Cria um objeto CultureDto com o nome "Milho"
        CultureDto culture1 = new CultureDto();
        culture1.setName("Milho");

        // Cadastra a primeira cultura com sucesso
        CultureModel savedCulture1 = cultureController.save(culture1);
        assertNotNull(savedCulture1);

        // Cria um objeto CultureDto com o nome "Trigo"
        CultureDto culture2 = new CultureDto();
        culture2.setName("Soja");

        // Cadastra a segunda cultura com sucesso
        CultureModel savedCulture2 = cultureController.save(culture2);
        assertNotNull(savedCulture2);
    }


    @Test
    @DisplayName("Tentar cadastrar varias culturas com o mesmo nome diferenciando espaços e letras maiusculas e minusculas deve lançar exceção")
    void saveTwoCulturesWithSameName() {
        // Cria um objeto CultureDto com o nome "Milho"
        CultureDto culture1 = new CultureDto();
        culture1.setName("Milho");

        // Cadastra a primeira cultura com sucesso
        CultureModel savedCulture1 = cultureController.save(culture1);
        assertNotNull(savedCulture1);

        // Tenta cadastrar a segunda cultura com o mesmo nome "Milho", deve lançar exceção DuplicateObjNameException
        CultureDto culture2 = new CultureDto();
        culture2.setName("Milho");

        assertThrows(DuplicateObjNameException.class, () -> {
            cultureController.save(culture2);
        });

        // Tenta cadastrar a terceira cultura com o mesmo nome "     Milho", deve lançar exceção DuplicateObjNameException
        CultureDto culture3 = new CultureDto();
        culture3.setName("        Milho");

        assertThrows(DuplicateObjNameException.class, () -> {
            cultureController.save(culture3);
        });

        // Tenta cadastrar a terceira cultura com o mesmo nome "     milho", deve lançar exceção DuplicateObjNameException
        CultureDto culture4 = new CultureDto();
        culture4.setName("        milho");

        assertThrows(DuplicateObjNameException.class, () -> {
            cultureController.save(culture4);
        });
    }



    @Test
    @DisplayName("Buscar todas as culturas")
    void getAllCulture() {
        // Cria uma lista de objetos CultureModel
        List<CultureModel> cultures = new ArrayList<>();
        CultureModel culture1 = new CultureModel();
        culture1.setName("Milho");
        cultures.add(culture1);

        CultureModel culture2 = new CultureModel();
        culture2.setName("Soja");
        cultures.add(culture2);

        // Salva as culturas no banco de dados de testes
        cultureRepository.saveAll(cultures);

        // Chama o método getAllCulture() do controller e verifica o resultado
        ResponseEntity<List<CultureModel>> response = cultureController.getAllCulture();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        assertEquals("Milho", response.getBody().get(0).getName());
        assertEquals("Soja", response.getBody().get(1).getName());
    }


    @Test
    @DisplayName("Buscar culturas pelo nome")
    void findCultureByNameContaining() {
        // Cria uma lista de objetos CultureModel
        List<CultureModel> cultures = new ArrayList<>();
        CultureModel culture1 = new CultureModel();
        culture1.setName("Milho");
        cultures.add(culture1);
        CultureModel culture2 = new CultureModel();
        culture2.setName("Feijão");
        cultures.add(culture2);

        // Salva as culturas no banco de dados de testes
        cultureRepository.saveAll(cultures);

        // Chama o método findCultureByNameContaining() do controller e verifica o resultado
        ResponseEntity<List<CultureModel>> response = cultureController.findByNameContaining("MIL");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals("Milho", response.getBody().get(0).getName());
    }



    @Test
    @DisplayName("Deletar uma cultura pelo ID")
    void deleteCulture() {
        // Cria uma cultura e salva no banco de dados
        CultureModel culture = new CultureModel();
        culture.setName("Milho");
        CultureModel savedCulture = cultureRepository.save(culture);

        // Chama o método deleteCulture() do controller para deletar a cultura salva
        ResponseEntity<Object> response = cultureController.deleteCulture(savedCulture.getId());
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // Verifica se a cultura foi deletada do banco de dados
        Optional<CultureModel> deletedCulture = cultureRepository.findById(savedCulture.getId());
        assertTrue(deletedCulture.isEmpty());
    }

    @Test
    @DisplayName("Deletar duas culturas pelo ID")
    void deleteTwoCultures() {
        // Cria duas culturas e salva no banco de dados
        CultureModel culture1 = new CultureModel();
        culture1.setName("Milho");
        CultureModel savedCulture1 = cultureRepository.save(culture1);

        CultureModel culture2 = new CultureModel();
        culture2.setName("Feijão");
        CultureModel savedCulture2 = cultureRepository.save(culture2);

        // Chama o método deleteCulture() do controller para deletar as culturas salvas
        ResponseEntity<Object> response1 = cultureController.deleteCulture(savedCulture1.getId());
        ResponseEntity<Object> response2 = cultureController.deleteCulture(savedCulture2.getId());
        assertEquals(HttpStatus.OK, response1.getStatusCode());
        assertEquals(HttpStatus.OK, response2.getStatusCode());

        // Verifica se as culturas foram deletadas do banco de dados
        Optional<CultureModel> deletedCulture1 = cultureRepository.findById(savedCulture1.getId());
        assertTrue(deletedCulture1.isEmpty());

        Optional<CultureModel> deletedCulture2 = cultureRepository.findById(savedCulture2.getId());
        assertTrue(deletedCulture2.isEmpty());
    }

    @Test
    @DisplayName("Atualizar cultura pelo ID")
    void updateCulture() {
        // Cria uma cultura e salva no banco de dados
        CultureModel culture = new CultureModel();
        culture.setName("Milho");
        CultureModel savedCulture = cultureRepository.save(culture);

        // Cria um objeto CultureDto com os novos dados para atualizar a cultura
        CultureDto updatedCultureDto = new CultureDto();
        updatedCultureDto.setName("Feijão");

        // Chama o método updateCulture() do controller para atualizar a cultura salva
        ResponseEntity<CultureModel> response = cultureController.updateCulture(savedCulture.getId(), updatedCultureDto);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // Verifica se a cultura foi atualizada no banco de dados
        Optional<CultureModel> updatedCulture = cultureRepository.findById(savedCulture.getId());
        assertTrue(updatedCulture.isPresent());
        assertEquals("Feijão", updatedCulture.get().getName());
    }

}
