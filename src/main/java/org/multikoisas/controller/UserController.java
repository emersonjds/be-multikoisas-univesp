package org.multikoisas.controller;

import org.multikoisas.dto.UserRequest;
import org.multikoisas.dto.UserResponse;
import org.multikoisas.repository.UserRepository;
import org.multikoisas.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")

public class UserController {

    private UserRepository userRepository;
    private UserRequest userRequest;
    private UserResponse userResponse;
    private UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    };

    @GetMapping
    public ResponseEntity<List<UserResponse>> getUser(){
        List<UserResponse> users = userService.findAll();
        return ResponseEntity.ok(users);
    }
}
