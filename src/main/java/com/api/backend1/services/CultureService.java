package com.api.backend1.services;


import com.api.backend1.dtos.CultureDto;
import com.api.backend1.exceptions.DuplicateObjNameException;
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



    @Transactional
    /**
     * Cria uma nova cultura com base no DTO fornecido e a salva no banco de dados.
     *
     * @param cultureDto o DTO que contém as informações da cultura a ser criada
     * @return a cultura criada e salva no banco de dados
     * @throws DuplicateObjNameException se já existir uma cultura com o mesmo nome
     */
    public CultureModel createCulture(CultureDto cultureDto) throws ResourceNotFoundException {


        //Verificar se tem alguma cultura com o mesmo nome
        if (cultureRepository.existsByName(cultureDto.getName())) {
            throw new DuplicateObjNameException("Name of culture is already in use!");
        }

        // Criar uma Cultura com base no DTO
        var culture = new CultureModel();
        BeanUtils.copyProperties(cultureDto, culture);


        // salva no banco de dados
        return cultureRepository.save(culture);
    }


    /**
     * Retorna uma lista de todas as culturas salvas no banco de dados.
     *
     * @return uma lista de todas as culturas salvas no banco de dados
     */
    public List<CultureModel> findAll() {
        return cultureRepository
                .findAll();
    }


    /**
     * Busca todas as culturas cujo nome contenha a string fornecida.
     *
     * @param name a string que deve ser contida no nome das culturas buscadas
     * @return uma lista de todas as culturas cujo nome contenha a string fornecida
     * @throws ResourceNotFoundException se nenhuma cultura for encontrada com o nome fornecido
     */
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


    @Transactional
    /**
     * Exclui a cultura com o id fornecido.
     * @param id o id da cultura a ser excluída.
     * @throws ResourceNotFoundException se não houver cultura com o id fornecido.
     */
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


    @Transactional
    /**
     * Atualiza os dados da cultura com o id fornecido com base nos dados fornecidos.
     * @param id o id da cultura a ser atualizada.
     * @param cultureDto um objeto CultureDto contendo os novos dados da cultura.
     * @return um objeto CultureModel representando a cultura atualizada.
     * @throws ResourceNotFoundException se não houver cultura com o id fornecido.
     */
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
