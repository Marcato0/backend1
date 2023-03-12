package com.api.backend1.repositories;


import com.api.backend1.models.BudgetModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface BudgetRepository extends JpaRepository<BudgetModel, UUID> {

    List<BudgetModel> findByNameContaining(String name);
}
