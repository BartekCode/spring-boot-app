package com.bartek.restApi.model.projection;

import com.bartek.restApi.model.Discovery;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDiscoveryWriteModel { //discovery w obrębie kategorii

    private String title;
    private String description;
    private String url;
    private LocalDateTime dateAdded;

    public Discovery toDiscovery(){

        return new Discovery(title,description,url, dateAdded);
    }

}
