package com.bartek.restApi.logic;

import com.bartek.restApi.repository.CategoryRepository;
import com.bartek.restApi.repository.DiscoveryRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

@Configuration
public class LogicConfiguration {

    @Bean
    CategoryService categoryService(final CategoryRepository categoryRepository,@Lazy final DiscoveryRepository discoveryRepository) {
        return new CategoryService(categoryRepository,discoveryRepository);
    }



}
