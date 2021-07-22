package com.bartek.restApi.repository;

import com.bartek.restApi.model.Discovery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;


public interface DiscoveryRepository {
    List<Discovery> findAll();
    Optional<Discovery> findById(int id);
    Discovery save(Discovery entity);
    boolean existsByDoneIsFalseAndCategory_Id(Long id);
    Page<Discovery> findAll(Pageable pageable);
    boolean existsById(int id);
    Optional<Discovery>findByTitle(String title);
}
