package com.bartek.restApi.controller;

import com.bartek.restApi.model.Discovery;
import com.bartek.restApi.repository.DiscoveryRepository;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController //Controller + ResponseBody , kazda metoda zwraca Response body czyli kazdy wynik metody bedzie mapowane w JSON
public class DiscoveryController {

    private static final Logger logger = LoggerFactory.getLogger(DiscoveryController.class);
    private final DiscoveryRepository discoveryRepository;

    public DiscoveryController(DiscoveryRepository discoveryRepository) {
        this.discoveryRepository = discoveryRepository;
    }
    @GetMapping(value = "/discoveries", params = {"!sort", "!page", "!size"})
    ResponseEntity <List<Discovery>> readAllDiscoveries(){
        logger.warn("Exposing all discoveries");
        return ResponseEntity.ok(discoveryRepository.findAll());
    }
    @GetMapping(value = "/discoveries")
    ResponseEntity <List<Discovery>> readAllDiscoveries(Pageable pageable){
        logger.info("Custom pageable");
        return ResponseEntity.ok(discoveryRepository.findAll(pageable).getContent());
    }

    @PutMapping("/discoveries/{id}")//Requestbody to co dostaniemy zdeserializuj na obiekt javovy
    ResponseEntity<?> updateTask(@PathVariable("id") Long id, @RequestBody @Valid Discovery toUpdate){
        if (!discoveryRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
            toUpdate.setId(id);//zapisujemy set id na podany i zapisujemy w repo
            discoveryRepository.save(toUpdate);
            return ResponseEntity.noContent().build();
        }
    }



