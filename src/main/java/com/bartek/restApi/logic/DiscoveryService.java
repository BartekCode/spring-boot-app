package com.bartek.restApi.logic;

import com.bartek.restApi.model.Category;
import com.bartek.restApi.model.Discovery;
import com.bartek.restApi.model.projection.CategoryDiscoveryReadModel;
import com.bartek.restApi.repository.CategoryRepository;
import com.bartek.restApi.repository.DiscoveryRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DiscoveryService {

    private final DiscoveryRepository discoveryRepository;
    private final CategoryRepository categoryRepository;

    public DiscoveryService(DiscoveryRepository discoveryRepository, CategoryRepository categoryRepository) {
        this.discoveryRepository = discoveryRepository;
        this.categoryRepository = categoryRepository;
    }

public Discovery addDiscovery(Discovery discovery, long categoryId) {
    Optional<Category> category = categoryRepository.findById(categoryId);
    if (category.isEmpty()){
        throw  new NullPointerException("Category cannot be found!!");
    }else {
        discovery.setCategory(category.orElseThrow());
        discovery.setTitle(discovery.getTitle());
        discovery.setDescription(discovery.getDescription());
        discovery.setDateAdd(discovery.getDateAdd());
            category.stream()
                    .map(category1 -> category1.getDiscoveries()
                    .add(discovery)).collect(Collectors.toSet());
            if (categoryId == discovery.getCategory().getId()){
                System.out.println("Discovery added!");
            }
            return discovery;
        }
    }

      public Discovery discoveryMapper(CategoryDiscoveryReadModel toMap){
         String title = toMap.getTitle();
         String description = toMap.getDescription();
         String url = toMap.getUrl();

          return new Discovery(title,description, url,LocalDateTime.now());
     }
     public Discovery findDiscoveryByTitle(String title) throws Exception {
         Optional<Discovery> byTitle = discoveryRepository.findByTitle(title);
         return byTitle.orElseThrow(()-> new Exception("Discovery not found"));
     }

}
