package com.bartek.restApi;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties("discovery") //jak sie bedzie zaczynac nasze property
public class DiscoveryConfigurationProperty {
    private Template template;

    @Getter
    @Setter
    @AllArgsConstructor
    public static class Template{//zagniezdzona klasa ktora ma obiekt boolean ktory wpiszemy do property
        private boolean allowMultipleDiscoveries;
    }
}
