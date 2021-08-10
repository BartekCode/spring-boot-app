package com.bartek.restApi.controller;

import com.bartek.restApi.model.Discovery;
import com.bartek.restApi.repository.DiscoveryRepository;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DiscoveryControllerE2ETest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;


    @Autowired
    DiscoveryRepository discoveryRepository;

    @Test
    void httpGet_returnsAllDiscoveries() throws JsonProcessingException {

        //given
        final HttpHeaders headers = new HttpHeaders(); //tworzymy header
        headers.set("Authorization","Bearer eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJkdXBhQG9uZXQucGwiLCJhdXRob3JpdGVzIjpbeyJhdXRob3JpdHkiOiJBRE1JTiJ9XSwiaWF0IjoxNjI4MDEzNjA5LCJleHAiOjE2MjkxNTEyMDB9.8Bk_LW2yhVjEti952nBg3SPIIjL9S_pISFydEOQkIeBZ_6kjlTjdwqvdKdEbbD5R");
         //dodajemy nasz nagłówek z autoryzacją i  tokenem JWT
        final HttpEntity<String>jwt = new HttpEntity<String>(headers);
        int initial = discoveryRepository.findAll().size();
        discoveryRepository.save(new Discovery("foo", "bar"));
        discoveryRepository.save(new Discovery("too", "par"));

        //when dzieki restTemplate strzelamy pod konkretny adres
        ResponseEntity<List<Discovery>> rateResponse =
                testRestTemplate.exchange("http://localhost:"+port+"/discoveries",
                        HttpMethod.GET, jwt, new ParameterizedTypeReference<List<Discovery>>() {
                        }); //robimy nasze zapytanie z nagłówkiem
        ResponseEntity<Discovery[]> exchange = testRestTemplate.exchange("http://localhost:" + port + "/discoveries",
                HttpMethod.GET, jwt, Discovery[].class); //ponizej zapytanie z utworzeniem tablicy
        List<Discovery> discoveries = rateResponse.getBody(); //lista
        Discovery[] result = exchange.getBody(); //tablica

        //then
        assertEquals(discoveries.size(), initial+2);
        assertEquals(result.length, initial+2);
    }
}