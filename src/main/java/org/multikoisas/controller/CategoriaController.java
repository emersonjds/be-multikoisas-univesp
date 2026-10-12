package org.multikoisas.controller;

import org.multikoisas.dto.CategoriaResponse;
import org.multikoisas.service.impl.CategoriaServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/categoria")
public class CategoriaController {

    private final CategoriaServiceImpl categoriaServiceImpl;

    public CategoriaController(CategoriaServiceImpl categoriaServiceImpl) {
        this.categoriaServiceImpl = categoriaServiceImpl;
    }

    @GetMapping
    public ResponseEntity<List<CategoriaResponse>> getCategorias() {
        List<CategoriaResponse> categorias = categoriaServiceImpl.findAll();
        return ResponseEntity.ok(categorias);
    }
}