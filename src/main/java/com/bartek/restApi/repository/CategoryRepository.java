package com.bartek.restApi.repository;


import com.bartek.restApi.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

//    @RestResource(path = "description", rel = "description")
//    List<Category> findByDescriptionNotNullAndDescriptionIsNotContaining(@Param("desc")String desc);
//
//} po usunieciu Data rest nie ma mozliwosci tworzenia takich metod z RestResource

    List<Category>findAll();
    Optional<Category>findById(Long id);
    Category save(Category entity);
}