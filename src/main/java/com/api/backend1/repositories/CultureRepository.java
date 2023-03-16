package com.api.backend1.repositories;


import com.api.backend1.models.CultureModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CultureRepository extends JpaRepository<CultureModel, UUID> {
    List<CultureModel> findByNameContainingIgnoreCase(String name);

    Optional<Object> findByNameIgnoreCase(String name);
}
