package com.api.backend1.repositories;


import com.api.backend1.models.PhotoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PhotoRepository extends JpaRepository<PhotoModel, UUID> {
    Optional<PhotoModel> findByName(String name);

    boolean existsByName(String name);

    List<PhotoModel> findByIdInAndProductIsNull(List<UUID> photoIds);

//
}
