package com.bartek.restApi.repository;


import com.bartek.restApi.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;

@RepositoryRestResource()
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Override
    @RestResource(exported = false)//by nie było możliwosci usuniecia , nie umozliwimy wysylania takiego zapytania
    void deleteById(Long aLong);
    @Override
    @RestResource(exported = false)
    void delete(Category category);

    @RestResource(path = "description", rel = "description")
    List<Category> findByDescriptionNotNullAndDescriptionIsNotContaining(@Param("desc")String desc);

}
