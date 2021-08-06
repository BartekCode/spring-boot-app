package com.bartek.restApi.controller;

import com.bartek.restApi.model.Discovery;
import com.bartek.restApi.repository.DiscoveryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;

@SpringBootTest
@ActiveProfiles("integration")
@AutoConfigureMockMvc //by odpytywac warstwe webowa bez uruchamiana calego serwera
public class DiscoveryControllerIntegrationTest {



    @Autowired
    private MockMvc mockMvc; //klasa ułatwiająca testy pozwala wykonywac zapytania i asercje

    @Autowired
    private DiscoveryRepository discoveryRepository;

    @Test
    void shouldNotAllowAccessToUnauthenticatedDiscoveries() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/discoveries")).andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test //test jest symulacją ze odpytujemy aplikacje czyli przechodzimy przez wszystkie warstwy controller service repo
    void httpGetReturnsGivenDiscovery() throws Exception {
       //given
        final HttpHeaders headers = new HttpHeaders(); //tworzymy header
        headers.set("Authorization","Bearer eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJkdXBhQG9uZXQucGwiLCJhdXRob3JpdGVzIjpbeyJhdXRob3JpdHkiOiJBRE1JTiJ9XSwiaWF0IjoxNjI4MDEzNjA5LCJleHAiOjE2MjkxNTEyMDB9.8Bk_LW2yhVjEti952nBg3SPIIjL9S_pISFydEOQkIeBZ_6kjlTjdwqvdKdEbbD5R");
        //dodajemy nasz nagłówek z autoryzacją i  tokenem JWT
        Discovery save = discoveryRepository.save(new Discovery("foo", "poo", LocalDateTime.now()));
        int id = save.getId();

        //when + then
        mockMvc.perform(MockMvcRequestBuilders.get("/discoveries/"+id).headers(headers))    //mockMvc posiada kilka pomocnych mechanizmow
            .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
    }

}
