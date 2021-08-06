package com.bartek.restApi;

import com.bartek.restApi.model.Discovery;
import com.bartek.restApi.repository.DiscoveryRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.util.*;

@Configuration
public class TestConfiguration {


    @Bean
    @Primary
    @Profile("!integration")//profil nie integracyjny
    DataSource e2eTestDataSource(){
        //Łaczymy sie dzieki java database conectivity z instancją bazy H2 trzymaną w pamieci
        var result = new DriverManagerDataSource("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1", "sa","");
        result.setDriverClassName("org.h2.Driver");
        return result;
    }

    @Bean
    @Primary
    @Profile("integration")
    DiscoveryRepository testRepo(){
        return new DiscoveryRepository() {

            private Map<Integer, Discovery> discoveries = new HashMap<>();
            @Override
            public List<Discovery> findAll() {
                return new ArrayList<>(discoveries.values());
            }
            @Override
            public Optional<Discovery> findById(int id) {
                return Optional.ofNullable(discoveries.get(id));
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
            public boolean existsById(int id) {
                return discoveries.containsKey(id);
            }
            @Override
            public Optional<Discovery> findByTitle(String title) {
                return Optional.empty();
            }

            @Override
            public Discovery save(Discovery entity) {
                int key = discoveries.size()+1;
                try {
                    var filed = Discovery.class.getDeclaredField("id");
                    filed.setAccessible(true);
                    filed.set(entity, key);
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    throw  new RuntimeException(e);
                }
                discoveries.put(key, entity);
                return discoveries.get(key);
            }

        };
    }
}
