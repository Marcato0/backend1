package com.api.backend1.controllers;

import java.util.List;
import java.util.UUID;

import com.api.backend1.dtos.BudgetDto;
import com.api.backend1.exceptions.ResourceNotFoundException;
import com.api.backend1.models.BudgetModel;
import com.api.backend1.services.BudgetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/budget")
public class BudgetController {

    @Autowired
    private BudgetService budgetService;


    @PostMapping
    /**
     * Método público que recebe um objeto do tipo BudgetDto via HTTP request body
     * e retorna um objeto do tipo BudgetModel.
     *
     * @param budgetDto objeto BudgetDto enviado pelo cliente via HTTP request body.
     * @return objeto BudgetModel criado e persistido no banco de dados.
     */
    public BudgetModel save(@RequestBody BudgetDto budgetDto) {
        return budgetService.createBudget(budgetDto);
    }




    @GetMapping
    /**
     * Método público que retorna uma lista com todos os orçamentos registrados no banco de dados.
     *
     * @return objeto ResponseEntity com status HTTP OK e corpo da resposta contendo a lista de orçamentos.
     */
    public ResponseEntity<List<BudgetModel>> getAllBudget() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(budgetService
                        .findAll());
    }



    @GetMapping("/search/{name}")
    /**
     * Método público que recebe um parâmetro 'name' enviado pelo cliente via URL e retorna uma lista de
     * objetos BudgetModel que contém o valor do parâmetro 'name' no campo 'name' da entidade.
     *
     * @param name valor a ser buscado no campo 'name' do objeto BudgetModel.
     * @return objeto ResponseEntity com status HTTP OK e corpo da resposta contendo a lista de objetos BudgetModel.
     */
    public ResponseEntity<List<BudgetModel>> findByNameContaining(@PathVariable String name) {
        /**
         * Chama o metodo findByNameContaining do service que tem uma lista de orçamentos com o valor de 'name' no campo name do model
         * O resultado dessa busca é armazenado numa lista de objetos chamada budgets.
         */
        List<BudgetModel> budgets = budgetService.findByNameContaining(name);
        return ResponseEntity.status(HttpStatus.OK).body(budgets);
    }



    @DeleteMapping("/{id}")
    /**
     * Método público que recebe o ID de um objeto BudgetModel a ser deletado e retorna uma resposta HTTP.
     *
     * @param id ID do objeto BudgetModel a ser deletado.
     * @return objeto ResponseEntity com status HTTP OK e corpo da resposta contendo a mensagem de sucesso.
     */
    public ResponseEntity<Object> deleteBudget(@PathVariable UUID id) throws ResourceNotFoundException {
        budgetService.deleteBudget(id);
        return ResponseEntity.status(HttpStatus.OK).body("Budget deleted successfully.");
    }



    @PutMapping("/{id}")
    /**
     * Método público que recebe o ID de um objeto BudgetModel e um objeto BudgetDto enviado pelo cliente via HTTP request body.
     * O método atualiza os campos do objeto BudgetModel com base nas informações contidas no objeto BudgetDto.
     *
     * @param id ID do objeto BudgetModel a ser atualizado.
     * @param budgetDto objeto BudgetDto enviado pelo cliente via HTTP request body.
     * @return objeto ResponseEntity com status HTTP OK e corpo da resposta contendo o objeto BudgetModel atualizado.
     */
    public ResponseEntity<BudgetModel> updateBudget(@PathVariable UUID id,
                                                    @RequestBody BudgetDto budgetDto)
                                                    throws ResourceNotFoundException {

        BudgetModel budget = budgetService.updateBudget(id, budgetDto);
        return ResponseEntity.status(HttpStatus.OK).body(budget);
    }
}
