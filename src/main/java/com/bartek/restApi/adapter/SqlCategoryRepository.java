package com.bartek.restApi.adapter;

import com.bartek.restApi.model.Category;
import com.bartek.restApi.repository.CategoryRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SqlCategoryRepository extends CategoryRepository, JpaRepository<Category, Long> {

    @Override
    @Query("select distinct c from  Category c join fetch c.discoveries")//query by nie bylo n+1 selectorów
    List<Category> findAll();
}
