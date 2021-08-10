package com.bartek.restApi.controller;

import com.bartek.restApi.logic.CommentService;
import com.bartek.restApi.logic.DiscoveryService;
import com.bartek.restApi.logic.UserService;
import com.bartek.restApi.model.Comment;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("comments")
public class CommentController {

    private DiscoveryService discoveryService;
    private CommentService commentService;
    private UserService userService;

    @GetMapping("/{id}")
    ResponseEntity<List<Comment>> readAllDiscoveryComments(@PathVariable ("id") int id) {
        try {
            List<Comment> allDiscoverComments = commentService.getAllDiscoverComments(id);
            return ResponseEntity.ok(allDiscoverComments);
        } catch (NullPointerException e) {
            System.out.println("No comments found");
        }
      return ResponseEntity.notFound().build();
    }

    @PostMapping("/{id}")
    @Transactional
        ResponseEntity<Comment> createComment (@PathVariable("id") int discoverId, @RequestBody Comment comment, Principal principal){
//        ResponseEntity<Comment> createComment (@PathVariable("id") int discoverId, @RequestBody Comment comment, Principal principal){
//        User user = userService.findByEmail(principal.getName()); wywalamy princiapala poki co bo nie ma logowanie
        try {
            Comment comment1 = commentService.creatComment(comment, discoverId);

            return ResponseEntity.created(URI.create("/"+comment1.getId())).body(comment1);
        }catch (NullPointerException e){
            System.out.println("WTFFFFFF");
        }
        return ResponseEntity.notFound().build();
    } //TO DO UTWORZENIE COMMENTA DO DISCOVERY NAJEPIERW LOGOWANIE BY MOZNA BYLO POBRAC PRINCIPALA
}
