package com.api.backend1.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


import com.api.backend1.dtos.BudgetDto;
import com.api.backend1.exceptions.ResourceNotFoundException;
import com.api.backend1.models.BudgetModel;
import com.api.backend1.models.ProductModel;
import com.api.backend1.repositories.BudgetRepository;
import com.api.backend1.repositories.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BudgetService {

    //ponto de injeção
    @Autowired
    private BudgetRepository budgetRepository;
    @Autowired
    private ProductRepository productRepository;


    @Transactional
    /**
     * Cria um novo orçamento com base nos dados fornecidos no objeto BudgetDto.
     * @param budgetDto objeto BudgetDto com os dados do novo orçamento.
     * @return o objeto BudgetModel criado e salvo no repositório de orçamentos.
     * @throws ResourceNotFoundException se o produto referenciado no objeto BudgetDto não existir.
     */
    public BudgetModel createBudget(BudgetDto budgetDto) throws ResourceNotFoundException {

        // procura o produto com base no ID fornecido no DTO de orçamento
        UUID productId = UUID.fromString(budgetDto.getProduct().getId().toString());

        // Busca o produto no repositório de produtos pelo ID
        Optional<ProductModel> optionalProduct = productRepository.findById(productId);

        // Verifica se o produto existe
        if (optionalProduct.isEmpty()) {
            throw new ResourceNotFoundException("Product not found with id: " + productId);
        }

        // obtém o produto encontrado
        ProductModel product = optionalProduct.get();

        // cria o modelo de orçamento
        BudgetModel budget = new BudgetModel(
                UUID.randomUUID(),
                LocalDate.now(),
                budgetDto.getName(),
                budgetDto.getEmail(),
                budgetDto.getPhone(),
                product
        );

        // salva no banco de dados
        return budgetRepository.save(budget);
    }


    /**
     * Retorna uma lista de todas as cotações salvas no banco de dados.
     *
     * @return uma lista de todas as cotações salvas no banco de dados
     */
    public List<BudgetModel> findAll() {
        return budgetRepository
                .findAll();
    }


    /**
     * Busca todas as cotações cujo nome contenha a string fornecida.
     *
     * @param name a string que deve ser contida no nome das cotações buscadas
     * @return uma lista de todas as cotações cujo nome contenha a string fornecida
     * @throws ResourceNotFoundException se nenhuma cotação for encontrada com o nome fornecido
     */
    public List<BudgetModel> findByNameContaining(String name) {
        return budgetRepository
                .findByNameContaining(name);
    }


    @Transactional
    /**
     * Exclui a cotação com o id fornecido.
     * @param id o id da cotação a ser excluída.
     * @throws ResourceNotFoundException se não houver cotação com o id fornecido.
     */
    public void deleteBudget(UUID id) throws ResourceNotFoundException {

        //busca o orçamento pelo id
        Optional<BudgetModel> optionalBudget = budgetRepository.findById(id);

        // verifica se o orçamento existe e, se não existir, lança uma exceção
        if (optionalBudget.isEmpty()) {
            throw new ResourceNotFoundException("Budget not found with id: " + id);
        }

        // exclui o orçamento encontrado
        BudgetModel budget = optionalBudget.get();
        budgetRepository.deleteById(budget.getId());
    }



    @Transactional
    /**
     * Atualiza um orçamento existente com base em seu ID e no DTO de orçamento fornecido.
     * @param id ID do orçamento a ser atualizado.
     * @param budgetDto DTO de orçamento contendo as informações atualizadas.
     * @return o modelo de orçamento atualizado.
     * @throws ResourceNotFoundException se o orçamento não for encontrado com o ID fornecido ou
     * o produto não for encontrado com o ID fornecido no DTO de orçamento.
     */
    public BudgetModel updateBudget(UUID id, BudgetDto budgetDto) throws ResourceNotFoundException {

        Optional<BudgetModel> optionalBudget = budgetRepository.findById(id);

        // verifica se o orçamento existe
        if (optionalBudget.isEmpty()) {
            throw new ResourceNotFoundException("Budget not found with id: " + id);
        }

        // procura o produto com base no ID fornecido no DTO de orçamento
        UUID productId = UUID.fromString(budgetDto.getProduct().getId().toString());
        Optional<ProductModel> optionalProduct = productRepository.findById(productId);

        // verifica se o produto existe
        if (optionalProduct.isEmpty()) {
            throw new ResourceNotFoundException("Product not found with id: " + productId);
        }

        // obtém o produto encontrado
        ProductModel product = optionalProduct.get();

        // atualiza o modelo de orçamento com base no DTO de orçamento
        BudgetModel budget = optionalBudget.get();
        budget.setRequest_date(LocalDate.now());
        budget.setName(budgetDto.getName());
        budget.setEmail(budgetDto.getEmail());
        budget.setPhone(budgetDto.getPhone());
        budget.setProduct(product);

        //salva no banco de dados
        return budgetRepository.save(budget);
    }


}
