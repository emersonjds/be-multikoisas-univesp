package org.multikoisas.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.multikoisas.dto.UserRequest;
import org.multikoisas.dto.UserResponse;
import org.multikoisas.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor

public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserResponse>> getUser(){
        List<UserResponse> response = userService.findAll();
        return response != null ? ResponseEntity.ok(response) : ResponseEntity.notFound().build();
    }

    @GetMapping(params = "id")
    public ResponseEntity<UserResponse> getUserById(@RequestParam("id") Integer id){
        UserResponse response =  userService.findById(id);
        return response != null ? ResponseEntity.ok(response) : ResponseEntity.notFound().build();
    }

    @GetMapping("/{username}")
    public ResponseEntity<UserResponse> getUserByUsername(@PathVariable String username){
        UserResponse response =  userService.findByUsername(username);
        return response != null ? ResponseEntity.ok(response) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody UserRequest request){
        // UserResponse response = userService.save(request); //caso queira o retorno do response
        // return ResponseEntity.status(HttpStatus.CREATED).body(response);

        UserResponse response = userService.save(request);
        return response != null ? ResponseEntity.status(HttpStatus.CREATED).build() : ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<Object> deleteUser(@PathVariable String username){
        UserResponse response = userService.delete(username);
        //return response == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(response); // retorna o 200 OK com o usuário deletado
        return response != null ?ResponseEntity.ok(Map.of("message", "Usuario " + username + " deletado com sucesso.")) : ResponseEntity.notFound().build();
    }

    @PutMapping("/{username}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable String username, @RequestBody UserRequest request) {
        UserResponse response = userService.update(username, request);
        return response != null ? ResponseEntity.ok(response) : ResponseEntity.notFound().build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        // Varre todos os campos que falharam na validação do @Valid
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage()));

        // Devolve o 400 Bad Request juntamente com as mensagens (ex: {"password": "A senha é obrigatória."})
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
        // Interceta a exceção de negócio e devolve o status 409 com a mensagem exata
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }
}
