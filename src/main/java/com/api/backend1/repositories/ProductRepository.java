package com.api.backend1.repositories;


import com.api.backend1.models.ProductModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<ProductModel, UUID> {

    List<ProductModel> findByNameContaining(String name);

    boolean existsByName(String name);
}
