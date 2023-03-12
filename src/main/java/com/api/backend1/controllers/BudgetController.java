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

    /**
    * método público que recebe um objeto do tipo BudgetDto via HTTP request body
    * e retorna um objeto do tipo BudgetModel.
    */
    @PostMapping
    public BudgetModel save(@RequestBody BudgetDto budgetDto) {
        return budgetService.createBudget(budgetDto);
    }

    @GetMapping
    public ResponseEntity<List<BudgetModel>> getAllBudget() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(budgetService
                        .findAll());
    }

    @GetMapping("/search/{name}")
    public ResponseEntity<List<BudgetModel>> findByNameContaining(@PathVariable String name) {
        /**
         * Chama o metodo findByNameContaining do service que tem uma lista de orçamentos com o valor de 'name' no campo name do model
         * O resultado dessa busca é armazenado numa lista de objetos chamada budgets.
         */
        List<BudgetModel> budgets = budgetService.findByNameContaining(name);
        return ResponseEntity.status(HttpStatus.OK).body(budgets);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteBudget(@PathVariable UUID id) throws ResourceNotFoundException {
        budgetService.deleteBudget(id);
        return ResponseEntity.status(HttpStatus.OK).body("Budget deleted successfully.");
    }

    @PutMapping("/{id}")
    public ResponseEntity<BudgetModel> updateBudget(@PathVariable UUID id,
                                                    @RequestBody BudgetDto budgetDto)
                                                    throws ResourceNotFoundException {

        BudgetModel budget = budgetService.updateBudget(id, budgetDto);
        return ResponseEntity.status(HttpStatus.OK).body(budget);
    }


}
