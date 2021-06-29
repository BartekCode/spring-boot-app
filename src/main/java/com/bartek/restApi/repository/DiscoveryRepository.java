package com.bartek.restApi.repository;

import com.bartek.restApi.model.Discovery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


public interface DiscoveryRepository {
    List<Discovery> findAll();
    Optional<Discovery> findById(Long id);
    Discovery save(Discovery entity);
    Page<Discovery> findAll(Pageable pageable);
    boolean existsById(Long id);
}
