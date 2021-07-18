package com.bartek.restApi.model.projection;

import com.bartek.restApi.model.Discovery;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDiscoveryReadModel { //discovery czytany w obrebie kategorii

    private String title;
    private String description;
    private String url;
    private boolean done;


    public CategoryDiscoveryReadModel(Discovery source) {
        title = source.getTitle();
        description = source.getDescription();
        url = source.getUrl();
        done = source.isDone();

    }
}
