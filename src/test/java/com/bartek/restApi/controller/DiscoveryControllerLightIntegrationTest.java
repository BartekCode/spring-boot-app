package com.bartek.restApi.controller;
import com.bartek.restApi.logic.DiscoveryService;
import com.bartek.restApi.model.Discovery;
import com.bartek.restApi.repository.DiscoveryRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@WebMvcTest(DiscoveryController.class)
public class DiscoveryControllerLightIntegrationTest {


    @MockBean
    private DiscoveryService discoveryService;

    @Autowired
    private MockMvc mockMvc; //klasa ułatwiająca testy pozwala wykonywac zapytania i asercje

    @MockBean //by mockowac Beana a nie korzstac tego z profilu integration
    private DiscoveryRepository discoveryRepository;


    @Test //test jest symulacją ze odpytujemy aplikacje czyli przechodzimy przez wszystkie warstwy controller service repo
    void httpGetReturnsGivenDiscovery() throws Exception {
        //given
        String description = "poo";
        when(discoveryService.findById(anyInt()))
                .thenReturn((new Discovery("foo", "poo", LocalDateTime.now())));
        final HttpHeaders headers = new HttpHeaders(); //tworzymy header
        headers.set("Authorization","Bearer eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJkdXBhQG9uZXQucGwiLCJhdXRob3JpdGVzIjpbeyJhdXRob3JpdHkiOiJBRE1JTiJ9XSwiaWF0IjoxNjI4MDEzNjA5LCJleHAiOjE2MjkxNTEyMDB9.8Bk_LW2yhVjEti952nBg3SPIIjL9S_pISFydEOQkIeBZ_6kjlTjdwqvdKdEbbD5R");
        //dodajemy nasz nagłówek z autoryzacją i  tokenem JWT


        //when + then
        mockMvc.perform(MockMvcRequestBuilders.get("/discoveries/123").headers(headers))    //mockMvc posiada kilka pomocnych mechanizmow
                .andDo(print()) //z webMvcResultHandlers
                .andExpect(content().string(containsString(description)));
    }
}
