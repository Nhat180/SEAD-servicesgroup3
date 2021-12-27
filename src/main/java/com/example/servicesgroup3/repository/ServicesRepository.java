package com.example.servicesgroup3.repository;

import com.example.servicesgroup3.model.Services;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ServicesRepository extends JpaRepository<Services, Long> {
    Optional<Services> findByName (String name);
    Page<Services> findAll (Pageable pageable);
}
