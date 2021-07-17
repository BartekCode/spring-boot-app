package com.bartek.restApi.logic;

import com.bartek.restApi.repository.CategoryRepository;
import com.bartek.restApi.repository.DiscoveryRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LogicConfiguration {

    @Bean
    CategoryService categoryService(final CategoryRepository categoryRepository, final DiscoveryRepository discoveryRepository) {
        return new CategoryService(categoryRepository,discoveryRepository);
    }

}
