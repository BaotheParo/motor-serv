package com.motorcycleparts.repository;

import com.motorcycleparts.entity.Part;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PartRepository extends JpaRepository<Part, Long> {
    List<Part> findByNameContainingIgnoreCase(String name);
    Page<Part> findAll(Pageable pageable);
}