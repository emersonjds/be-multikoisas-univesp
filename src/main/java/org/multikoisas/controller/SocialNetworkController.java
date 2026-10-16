package org.multikoisas.controller;

import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.multikoisas.dto.SocialNetworkRequest;
import org.multikoisas.dto.SocialNetworkResponse;
import org.multikoisas.dto.UserRequest;
import org.multikoisas.dto.UserResponse;
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
        List<SocialNetworkResponse> socialNetworks = socialService.findAll();
        return ResponseEntity.ok(socialNetworks);
    }

    @GetMapping(params = "description")
    public ResponseEntity<SocialNetworkResponse> getSocialNetworkByName(@RequestParam("description") String description)
    {
        SocialNetworkResponse socialNetwork = socialService.findByName(description);
        return socialNetwork != null ? ResponseEntity.ok(socialNetwork) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<SocialNetworkResponse> createUser(@Valid @RequestBody SocialNetworkRequest request){

        // SocialNetworkResponse response = socialService.save(request); //caso queira o retorno do response
        // return ResponseEntity.status(HttpStatus.CREATED).body(response);

        socialService.save(request);

        return ResponseEntity.status(HttpStatus.CREATED).build();// retorna o 201 Created com o usuário criado
    }

    @DeleteMapping("/{description}")
    public ResponseEntity<Object> deleteUser(@PathVariable String description){
        SocialNetworkResponse response = socialService.delete(description);

        //return response == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(response); // retorna o 200 OK com o usuário deletado

        return response == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(Map.of("message", "Rede social " + description + " deletada com sucesso."));
    }

    @PutMapping("/{description}")
    public ResponseEntity<SocialNetworkResponse> updateSocialNetwork(@PathVariable String description, @RequestBody SocialNetworkRequest request) {

        SocialNetworkResponse response = socialService.update(description, request);

        // Se o Service devolveu null, a rede social não existe. Senão, devolve 200 com os dados novos.
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
