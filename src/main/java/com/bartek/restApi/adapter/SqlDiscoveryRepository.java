package com.bartek.restApi.adapter;

import com.bartek.restApi.model.Discovery;
import com.bartek.restApi.repository.DiscoveryRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
interface SqlDiscoveryRepository extends DiscoveryRepository, JpaRepository<Discovery, Long> {

    @Override //zapytanie natywnym query
    @Query(nativeQuery = true, value = "select count (*) > 0 from discoveries where id=:id")
    boolean existsById(@Param("id") Long id);//@Param "id" bedziemy z tego korzystac w zapytaniu

    @Override
    boolean existsByDoneIsFalseAndCategory_Id(Long categoryId);
//mają być nizrobione discovery w obrębie category

}

