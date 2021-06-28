package com.bartek.restApi.repository;


import com.bartek.restApi.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

//    @RestResource(path = "description", rel = "description")
//    List<Category> findByDescriptionNotNullAndDescriptionIsNotContaining(@Param("desc")String desc);
//
//} po usunieciu Data rest nie ma mozliwosci tworzenia takich metod z RestResource
}