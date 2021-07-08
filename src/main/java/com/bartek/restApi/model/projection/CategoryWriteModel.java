package com.bartek.restApi.model.projection;

import com.bartek.restApi.model.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;
import java.util.stream.Collectors;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryWriteModel {

    private String name;
    private String description;
    private Set<CategoryDiscoveryWriteModel> discoveries; //discovery do zapisu

    public Category toCategpry(){
        var result = new Category();
        result.setName(name);
        result.setDescription(description);
        result.setDiscoveries(
                discoveries.stream()
                .map(CategoryDiscoveryWriteModel::toDiscovery)
                .collect(Collectors.toSet())
        );
        return result;
    }

}
