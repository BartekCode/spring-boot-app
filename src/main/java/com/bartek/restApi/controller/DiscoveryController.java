package com.bartek.restApi.controller;

import com.bartek.restApi.model.Discovery;
import com.bartek.restApi.repository.DiscoveryRepository;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;


@RepositoryRestController
public class DiscoveryController {

    private static final Logger logger = LoggerFactory.getLogger(DiscoveryController.class);
    private final DiscoveryRepository discoveryRepository;

    public DiscoveryController(DiscoveryRepository discoveryRepository) {
        this.discoveryRepository = discoveryRepository;
    }
    @GetMapping("/discoveries")
    ResponseEntity <List<Discovery>> readAllDiscoveries(){
        logger.warn("Exposing all discoveries");
        return ResponseEntity.ok(discoveryRepository.findAll());
    }

}
