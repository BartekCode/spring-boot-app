package com.bartek.restApi.controller;

import com.bartek.restApi.logic.UserService;
import com.bartek.restApi.model.User;
import com.bartek.restApi.model.projection.UserInfo;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping
//    @PreAuthorize("hasAuthority('ADMIN')")
    ResponseEntity<List<User>> readAllUsers(){
        return ResponseEntity.ok(userService.findAll());
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable("id")Integer id){
        return userService.findById(id);
    }


    }


