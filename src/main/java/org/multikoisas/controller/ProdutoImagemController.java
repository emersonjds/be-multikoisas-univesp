package org.multikoisas.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.multikoisas.dto.ProdutoImagemRequest;
import org.multikoisas.dto.ProdutoImagemResponse;
import org.multikoisas.service.ProdutoImagemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/imagem")
@RequiredArgsConstructor
public class ProdutoImagemController {

    private final ProdutoImagemService service;

    @PostMapping(consumes = {"multipart/form-data"})
    public Object upload(@Valid @ModelAttribute ProdutoImagemRequest request) {

        ProdutoImagemResponse response = service.save(request);

        return response == null ? ResponseEntity.badRequest().build() : ResponseEntity.status(HttpStatus.CREATED).body(request);
    }
}