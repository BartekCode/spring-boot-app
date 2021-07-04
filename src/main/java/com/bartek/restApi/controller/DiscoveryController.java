package com.bartek.restApi.controller;

import com.bartek.restApi.model.Discovery;
import com.bartek.restApi.repository.DiscoveryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
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

    @Transactional
    @PutMapping("/discoveries/{id}")//Requestbody to co dostaniemy zdeserializuj na obiekt javovy
    ResponseEntity<?> updateTask(@PathVariable("id") Long id, @RequestBody @Valid Discovery toUpdate){
        if (!discoveryRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        discoveryRepository.findById(id)
                .ifPresent(discovery -> discovery.updateFrom(toUpdate));
//            toUpdate.setId(id);//zapisujemy set id na podany i zapisujemy w repo
            discoveryRepository.save(toUpdate);// TAK MUSIMY ZAPISAC GDYBY NIE BYLO ADNOTACJI @Transactional
            return ResponseEntity.noContent().build();
        }
        //TO DO PO UPDATE ZAPISUJE 2 RAZY NOWY OBIEKT NAPRAWIC !


        @PostMapping("/discoveries")
    ResponseEntity<Discovery> createDiscovery(@RequestBody @Valid Discovery toCreate){
            Discovery save = discoveryRepository.save(toCreate);
            return ResponseEntity.created(URI.create("/"+save.getId())).body(save);
        }

        @Transactional //ta zmiana zostanie zacommitowana do bazy danych
        @PatchMapping("discoveries/{id}")
    public ResponseEntity<?> toggleDiscovery(@PathVariable long id){
        if (!discoveryRepository.existsById(id)){
            return ResponseEntity.notFound().build();
        }
        discoveryRepository.findById(id)
                .ifPresent(discovery -> discovery.setDone(!discovery.isDone())); //zmieniamy na przeciwny niz jest,
            // commit do bazy pójdzie juz ze zmienionym added
        return ResponseEntity.noContent().build();
        }
    }



