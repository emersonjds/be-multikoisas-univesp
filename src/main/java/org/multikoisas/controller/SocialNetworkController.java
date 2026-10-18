package org.multikoisas.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.multikoisas.dto.SocialNetworkRequest;
import org.multikoisas.dto.SocialNetworkResponse;
import org.multikoisas.service.SocialNetworkService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/socialnetwork")
@RequiredArgsConstructor

public class SocialNetworkController {

    private final SocialNetworkService socialService;

    @GetMapping
    public ResponseEntity<List<SocialNetworkResponse>> getSocialNetwork()
    {
        List<SocialNetworkResponse> response = socialService.findAll();
        return response != null? ResponseEntity.ok(response) : ResponseEntity.notFound().build();
    }

    @GetMapping(params = "description")
    public ResponseEntity<SocialNetworkResponse> getSocialNetworkByName(@RequestParam("description") String description)
    {
        SocialNetworkResponse response = socialService.findByName(description);
        return response != null ? ResponseEntity.ok(response) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<SocialNetworkResponse> createUser(@Valid @RequestBody SocialNetworkRequest request){
        // SocialNetworkResponse response = socialService.save(request); //caso queira o retorno do response
        // return ResponseEntity.status(HttpStatus.CREATED).body(response);

        SocialNetworkResponse response = socialService.save(request);
        return response != null ? ResponseEntity.status(HttpStatus.CREATED).build() : ResponseEntity.noContent().build();// retorna o 201 Created com o usuário criado
    }

    @DeleteMapping("/{description}")
    public ResponseEntity<Object> deleteUser(@PathVariable String description){
        SocialNetworkResponse response = socialService.delete(description);

        //return response == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(response); // retorna o 200 OK com o usuário deletado
        return response != null ? ResponseEntity.ok(Map.of("message", "Rede social " + description + " deletada com sucesso.")) : ResponseEntity.notFound().build();
    }

    @PutMapping("/{description}")
    public ResponseEntity<SocialNetworkResponse> updateSocialNetwork(@PathVariable String description, @RequestBody SocialNetworkRequest request) {
        SocialNetworkResponse response = socialService.update(description, request);
        return response != null ? ResponseEntity.ok(response) : ResponseEntity.notFound().build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage()));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }
}
