package com.motorcycleparts.service;

import com.motorcycleparts.entity.Part;
import com.motorcycleparts.repository.OrderRepository;
import com.motorcycleparts.repository.PartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PartService {
    @Autowired
    private PartRepository partRepository;
    @Autowired
    private OrderRepository orderRepository;

    public Page<Part> getAllParts(Pageable pageable) {
        return partRepository.findAll(pageable);
    }

    public Part savePart(Part part) {
        return partRepository.save(part);
    }

    public Optional<Part> getPartById(Long id) {
        return partRepository.findById(id);
    }

    public boolean existsById(Long id) {
        return partRepository.existsById(id);
    }

    public void deletePart(Long id) {
        if (!partRepository.existsById(id)) {
            throw new IllegalArgumentException("Part ID does not exist");
        }
        if (!orderRepository.findByPartId(id).isEmpty()) {
            throw new IllegalArgumentException("Cannot delete part with existing orders");
        }
        partRepository.deleteById(id);
    }

    public List<Part> findByName(String name) {
        return partRepository.findByNameContainingIgnoreCase(name);
    }
}