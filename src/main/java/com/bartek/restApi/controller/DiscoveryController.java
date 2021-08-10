package com.bartek.restApi.controller;

import com.bartek.restApi.logic.DiscoveryService;
import com.bartek.restApi.model.Discovery;
import com.bartek.restApi.model.projection.CategoryDiscoveryReadModel;
import com.bartek.restApi.repository.DiscoveryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.net.URI;
import java.util.List;


@RestController //Controller + ResponseBody , kazda metoda zwraca Response body czyli kazdy wynik metody bedzie mapowane w JSON
@RequestMapping("/discoveries")
public class DiscoveryController {

    private static final Logger logger = LoggerFactory.getLogger(DiscoveryController.class);
    private final DiscoveryRepository discoveryRepository;
    private final DiscoveryService discoveryService;

    public DiscoveryController(@Lazy final DiscoveryRepository discoveryRepository, DiscoveryService discoveryService) {
        this.discoveryRepository = discoveryRepository;
        this.discoveryService = discoveryService;
    }

    @GetMapping(value = "/search/done", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<Discovery>> readDoneDiscoveries (@RequestParam(defaultValue = "true") boolean state) { //@RequestParam parametr zadania
        //jezeli nie podamy paramteru state to bedziemy szukacj deafultValue czyli true a np mozemy szukac false jak podamy
        return ResponseEntity.ok(discoveryRepository.findByDone(state));
    }

        @GetMapping("/test") //przed springiem jakbysmy chcieli sie odowlac do request i response dają rozne niskopoziomowe mechanizmy
                void oldFashionedWay(HttpServletRequest req, HttpServletResponse resp) throws IOException {
                   req.getUserPrincipal(); //pobranie zalogowanego usera
                   req.getParameter("foo"); //pobranie paramtetru
                   resp.getWriter().println("test old-fashioned way");
        }

//    @GetMapping(value = "/search/done", produces = MediaType.APPLICATION_JSON_VALUE)
//    String foo (){
//        return "";
     //działało by to w ten sposób jezEl ktos wysle pod ten adres rządanie http z nagłowkiem accept aaplication Json to trafi do foo
  //  jak accept  text Xml to do bar

    @GetMapping(value = "/search/done", produces = MediaType.TEXT_XML_VALUE)
    String bar (){
        return "";
    }

    @GetMapping( params = {"!sort", "!page", "!size"})
    ResponseEntity <List<Discovery>> readAllDiscoveries(){
        logger.warn("Exposing all discoveries");
        return ResponseEntity.ok(discoveryRepository.findAll());
    }
    @GetMapping
    ResponseEntity <List<Discovery>> readAllDiscoveries(Pageable pageable){
        logger.info("Custom pageable");
        return ResponseEntity.ok(discoveryRepository.findAll(pageable).getContent());
    }

    @GetMapping("/{id}")
    ResponseEntity<Discovery> readDiscById(@PathVariable("id")int id){
        return ResponseEntity.ok(discoveryRepository.findById(id).orElseThrow());
    }

    @Transactional
    @PutMapping("/{id}")//Requestbody to co dostaniemy zdeserializuj na obiekt javovy
    ResponseEntity<?> updateDiscovery(@PathVariable("id") int id, @RequestBody @Valid Discovery toUpdate){
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


        @PostMapping
    ResponseEntity<Discovery> createDiscovery(@RequestBody @Valid Discovery toCreate){
            Discovery save = discoveryRepository.save(toCreate);
            return ResponseEntity.created(URI.create("/"+save.getId())).body(save);
        }

        @Transactional //ta zmiana zostanie zacommitowana do bazy danych
        @PatchMapping("/{id}")
    public ResponseEntity<?> toggleDiscovery(@PathVariable int id){
        if (!discoveryRepository.existsById(id)){
            return ResponseEntity.notFound().build();
        }
        discoveryRepository.findById(id)
                .ifPresent(discovery -> discovery.setDone(!discovery.isDone())); //zmieniamy na przeciwny niz jest,
            // commit do bazy pójdzie juz ze zmienionym added
        return ResponseEntity.noContent().build();
        }

        @Transactional
        @PostMapping("/{id}")
        ResponseEntity<Discovery> createAndAddDiscoveryToSpecificCategory(@PathVariable long id, @RequestBody CategoryDiscoveryReadModel categoryDiscoveryReadModel) throws Exception {
            Discovery discovery = discoveryService.discoveryMapper(categoryDiscoveryReadModel);
            Discovery discovery1 = discoveryService.addDiscovery(discovery, id);
            discoveryRepository.save(discovery);
            return ResponseEntity.created(URI.create("/"+discovery1.getId())).body(discovery1);
        }
    }



