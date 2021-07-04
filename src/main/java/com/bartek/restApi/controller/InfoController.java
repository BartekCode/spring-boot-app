package com.bartek.restApi.controller;

import com.bartek.restApi.DiscoveryConfigurationProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class InfoController {

//    @Autowired //wstrzykiwanie przez pole, tylko spring tak wstrzykuje dlatego przez to mocno sie z nim wiążemy
    private final DataSourceProperties dataSource; //wstrzykujemy sobie jak ponizej value z tym ze trudniej zrobic cos zle i nie jest to napisane z paluuuchhhaaa
    private final DiscoveryConfigurationProperty myProp;

    public InfoController(DataSourceProperties dataSource, DiscoveryConfigurationProperty myProp) { //wstrzykniecie przez kontstruktor
        this.dataSource = dataSource;
        this.myProp = myProp;
    }

    @GetMapping("/info/url")
    String url(){
        return dataSource.getUrl(); // wtaki sposob nie zrobimy literowki
    }//Return the configured url or null if none was configured.

    @GetMapping("/info/prop") //czyli wpisując teraz taki adres powinnismy dostac false bo tak ustawilismy w properties
    boolean myProp(){
        return myProp.getTemplate().isAllowMultipleDiscoveries();
    }

}
