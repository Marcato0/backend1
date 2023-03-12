package com.api.backend1.services;


import com.api.backend1.dtos.CultureDto;
import com.api.backend1.exceptions.DuplicateProductNameException;
import com.api.backend1.exceptions.ResourceNotFoundException;
import com.api.backend1.models.CultureModel;
import com.api.backend1.repositories.CultureRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CultureService {

    //ponto de injeção
    @Autowired
    CultureRepository cultureRepository;


    // cria uma cultura a partir do DTO de culturas e salva no banco de dados
    @Transactional
    public CultureModel createCulture(CultureDto cultureDto) throws ResourceNotFoundException {


        //Verificar se tem alguma cultura com o mesmo nome
        if (cultureRepository.existsByName(cultureDto.getName())) {
            throw new DuplicateProductNameException("Name of culture is already in use!");
        }

        // Criar uma Cultura com base no DTO
        var culture = new CultureModel();
        BeanUtils.copyProperties(cultureDto, culture);


        // salva no banco de dados
        return cultureRepository.save(culture);
    }

    // retorna uma lista de todas as culturas salvas no banco de dados
    public List<CultureModel> findAll() {
        return cultureRepository
                .findAll();
    }

    public List<CultureModel> findByNameContaining(String name) {

        //busca a cultura pelo id
        List<CultureModel> optionalCulture = cultureRepository.findByNameContaining(name);

        // verifica se a cultura existe
        if (optionalCulture.isEmpty()) {
            throw new ResourceNotFoundException("Culture not found with name containing: " + name);
        }

        return cultureRepository
                .findByNameContaining(name);
    }

    // exclui uma cultura com o ID fornecido
    @Transactional
    public void deleteCulture(UUID id) throws ResourceNotFoundException {

        //busca a cultura pelo id
        Optional<CultureModel> optionalCulture = cultureRepository.findById(id);

        // verifica se a cultura existe
        if (optionalCulture.isEmpty()) {
            throw new ResourceNotFoundException("Culture not found with id: " + id);
        }


        // exclui a cultura encontrada
        CultureModel culture = optionalCulture.get();
        cultureRepository.deleteById(culture.getId());
    }

    // atualiza um produto com o ID fornecido com base no DTO de produto
    @Transactional
    public CultureModel updateCulture(UUID id, CultureDto cultureDto) throws ResourceNotFoundException {

        Optional<CultureModel> optionalCulture = cultureRepository.findById(id);

        // verifica se a cultura existe
        if (optionalCulture.isEmpty()) {
            throw new ResourceNotFoundException("Culture not found with id: " + id);
        }


        // Criar uma cultura com base no DTO
        var culture = new CultureModel();
        BeanUtils.copyProperties(cultureDto, culture);



        //Buscar id
        culture.setId(optionalCulture.get().getId());

        // salva no banco de dados
        return cultureRepository.save(culture);
        
    }




}
