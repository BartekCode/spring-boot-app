package com.bartek.restApi.controller;

import com.bartek.restApi.logic.UserService;
import com.bartek.restApi.model.User;
import com.bartek.restApi.model.projection.UserInfo;
import org.springframework.http.ResponseEntity;
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

    @PostMapping("/register")
    ResponseEntity<User> addUser (@RequestBody UserInfo userInfo){
        User user = userService.mapper(userInfo);
        userService.addUser(user);
        return ResponseEntity.created(URI.create("/"+user.getId())).body(user);
    }

    @GetMapping
    ResponseEntity<List<User>> readAllUsers(){
        return ResponseEntity.ok(userService.findAll());
    }


}
