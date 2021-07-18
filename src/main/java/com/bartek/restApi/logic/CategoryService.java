package com.bartek.restApi.logic;

import com.bartek.restApi.model.Category;
import com.bartek.restApi.model.projection.CategoryReadModel;
import com.bartek.restApi.model.projection.CategoryWriteModel;
import com.bartek.restApi.repository.CategoryRepository;
import com.bartek.restApi.repository.DiscoveryRepository;

import java.util.List;
import java.util.stream.Collectors;

//@Service zrobilismy wstrzykniecie tego service w LogicConfiguration przez  @Bean
//@RequestScope
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final DiscoveryRepository discoveryRepository;

    public CategoryService(CategoryRepository categoryRepository, DiscoveryRepository discoveryRepository) {
        this.categoryRepository = categoryRepository;
        this.discoveryRepository = discoveryRepository;
    }

    public CategoryReadModel createCategory(CategoryWriteModel source){
        Category result = categoryRepository.save(source.toCategory());//uzywamy naszego mappera toCategory zamieniamy CategoryWriteModel na Category
        return new CategoryReadModel(result);
    }

    public List<CategoryReadModel> readAll(){
        return categoryRepository.findAll().stream() //wyszukujemy wszystkie Category
                .map(CategoryReadModel::new) //mapujemy Category na CategoryReadModel
                .collect(Collectors.toList()); //i tworzymy liste
    }

    public  void toggleCategory(long categoryId){
        if (discoveryRepository.existsByDoneIsFalseAndCategory_Id(categoryId)){
            throw new  IllegalStateException("Category hase undone discovery. Done all discovery first");
        }
       Category result = categoryRepository.findById(categoryId)
               .orElseThrow(() -> new IllegalArgumentException("Category with given id not found!"));
        result.setDone(!result.isDone()); //odwracamy Done
        categoryRepository.save(result);
    }
}
