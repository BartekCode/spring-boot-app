package com.bartek.restApi.repository;

import com.bartek.restApi.model.Discovery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@RepositoryRestResource
public interface DiscoveryRepository extends JpaRepository <Discovery, Long> {
}
