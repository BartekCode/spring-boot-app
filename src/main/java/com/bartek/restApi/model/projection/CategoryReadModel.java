package com.bartek.restApi.model.projection;

import com.bartek.restApi.model.Category;
import com.bartek.restApi.model.Discovery;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryReadModel { //jak pwoinna wygladac odczytywana grupa

    private long id;
    private String name;
    private String description;
    private LocalDateTime dateAdded; //ostatni dodany Discovery
    private Set<CategoryDiscoveryReadModel> discoveries;

    public CategoryReadModel(Category source){
        id = source.getId();
        name= source.getName();
        description = source.getDescription();
        source.getDiscoveries().stream()
                .map(Discovery::getDateAdd)
                .max(LocalDateTime::compareTo)
                .ifPresent(dateTime -> dateAdded = dateTime);
        discoveries = source.getDiscoveries().stream()
                .map(CategoryDiscoveryReadModel::new).collect(Collectors.toSet());

    }

}
