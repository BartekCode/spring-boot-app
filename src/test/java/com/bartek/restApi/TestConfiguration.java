package com.bartek.restApi;

import com.bartek.restApi.model.Discovery;
import com.bartek.restApi.repository.DiscoveryRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.*;

@Configuration
public class TestConfiguration {

    @Bean
    @Profile("integration")
    DiscoveryRepository testRepo(){
        return new DiscoveryRepository() {

            private Map<Integer, Discovery> discoveries = new HashMap<>();
            @Override
            public List<Discovery> findAll() {
                return new ArrayList<>(discoveries.values());
            }
            @Override
            public Optional<Discovery> findById(Long id) {
                return Optional.ofNullable(discoveries.get(id));
            }
            @Override
            public Discovery save(Discovery entity) {
                return discoveries.put(discoveries.size()+1,entity);
            }
            @Override
            public boolean existsByDoneIsFalseAndCategory_Id(Long id) {
                return false;
            }
            @Override
            public Page<Discovery> findAll(Pageable pageable) {
                return null;
            }
            @Override
            public boolean existsById(Long id) {
                return discoveries.containsKey(id);
            }

            @Override
            public Optional<Discovery> findByTitle(String title) {
                return Optional.empty();
            }

        };
    }
}
