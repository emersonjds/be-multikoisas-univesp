package org.multikoisas.controller;

import lombok.RequiredArgsConstructor;
import org.multikoisas.dto.UserRequest;
import org.multikoisas.dto.UserResponse;
import org.multikoisas.repository.UserRepository;
import org.multikoisas.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor

public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserResponse>> getUser(){
        List<UserResponse> users = userService.findAll();
        return ResponseEntity.ok(users);
    }

    @GetMapping(params = "id")
    public ResponseEntity<UserResponse> getUserById(@RequestParam("id") Integer id){
        UserResponse response =  userService.findById(id);
        return response == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(response);
    }

//    @PostMapping
//    public ResponseEntity<UserResponse> updateUser(@RequestBody UserRequest userRequest){
//        UserResponse response = userService.save(userRequest);
//        return response == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(response);
//    }
//
//    @DeleteMapping(params = "userName")
//    public ResponseEntity<UserResponse> deleteUser(@RequestParam("userName") String userName){
//        UserResponse response = userService.delete(userName);
//        return response == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(response);
//    }
}
