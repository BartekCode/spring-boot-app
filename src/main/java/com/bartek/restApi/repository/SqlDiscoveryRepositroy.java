package com.bartek.restApi.repository;

import com.bartek.restApi.model.Discovery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface SqlDiscoveryRepository extends JpaRepository<Discovery, Long> {
}

