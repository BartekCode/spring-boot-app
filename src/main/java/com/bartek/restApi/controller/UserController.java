package com.bartek.restApi.controller;

import com.bartek.restApi.logic.UserService;
import com.bartek.restApi.model.User;
import com.bartek.restApi.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("users")
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;

    public UserController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    //hasRole('ROLE_') hasAnyRole('ROLE_') hasAuthority() hasAnyAuthority

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    ResponseEntity<List<User>> readAllUsers(){
        return ResponseEntity.ok(userService.findAll());
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable("id")Integer id){
        return userService.findById(id);
    }

    @PutMapping("{id}")
    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<User> updateUser(@PathVariable int id, @RequestBody User userToUpdate){
        if (!userService.existById(id)){
            return ResponseEntity.notFound().build();
        }else {
            User user = userService.findById(id);
            user.updateFrom(userToUpdate);
            userRepository.save(user);
            return ResponseEntity.noContent().build();
        }
    }

    }


