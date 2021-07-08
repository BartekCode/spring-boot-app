package com.bartek.restApi.model.projection;

import com.bartek.restApi.model.Discovery;
import com.bartek.restApi.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;



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
