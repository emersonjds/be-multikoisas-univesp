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
        List<UserResponse> users = userService.findAll();
        return ResponseEntity.ok(users);
    }

    @GetMapping(params = "id")
    public ResponseEntity<UserResponse> getUserById(@RequestParam("id") Integer id){
        UserResponse response =  userService.findById(id);
        return response == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(response);
    }

    @GetMapping("/{userName}")
    public ResponseEntity<UserResponse> getUserByUserName(@PathVariable String userName){
        UserResponse response =  userService.findByUserName(userName);
        return response == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody UserRequest request){

        UserResponse response = userService.save(request);
        // return ResponseEntity.status(HttpStatus.CREATED).body(response);
        return ResponseEntity.status(HttpStatus.CREATED).build();// retorna o 201 Created com o usuário criado
    }

    @DeleteMapping("/{userName}")
    public ResponseEntity<Object> deleteUser(@PathVariable String userName){
        UserResponse response = userService.delete(userName);

        //return response == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(response); // retorna o 200 OK com o usuário deletado

        return response == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(Map.of("message", "Usuario " + userName + " deletado com sucesso."));
    }

    @PutMapping("/{userName}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable String userName, @RequestBody UserRequest request) {

        UserResponse response = userService.update(userName, request);

        // Se o Service devolveu null, o usuário não existe. Senão, devolve 200 OK com os dados novos.
        return response == null
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(response);
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
