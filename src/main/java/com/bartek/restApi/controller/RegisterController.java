package com.bartek.restApi.controller;

import com.bartek.restApi.logic.UserService;
import com.bartek.restApi.model.User;
import com.bartek.restApi.model.projection.UserInfo;
import com.bartek.restApi.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
public class RegisterController {

    private final UserService userService;
    private final UserRepository userRepository;

    public RegisterController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @PostMapping("/register")
    ResponseEntity<User> addUser(@RequestBody UserInfo userInfo) {
        User user = userService.mapper(userInfo);
        userService.addUser(user);
        return ResponseEntity.created(URI.create("/" + user.getId())).body(user);
    }

    @Transactional
    @DeleteMapping("delete/{id}")
    ResponseEntity<User> deleteUser(@PathVariable("id") Integer userId) {
        if (userService.existById(userId)) {
            userService.deleteUser(userId);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/register/admin")
    ResponseEntity<User> addAdmin(@RequestBody UserInfo userInfo) {
        User user = userService.mapper(userInfo);
        userService.addAdmin(user);
        return ResponseEntity.created(URI.create("/" + user.getId())).body(user);
    }
}
