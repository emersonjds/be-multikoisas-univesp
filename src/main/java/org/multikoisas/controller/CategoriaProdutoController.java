package org.multikoisas.controller;

import org.multikoisas.dto.CategoriaProdutoResponse;
import org.multikoisas.service.impl.CategoriaProdutoServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/categoriaproduto")
public class CategoriaProdutoController {

    private final CategoriaProdutoServiceImpl categoriaProdutoServiceImpl;

    public CategoriaProdutoController(
            CategoriaProdutoServiceImpl categoriaProdutoServiceImpl) {
        this.categoriaProdutoServiceImpl = categoriaProdutoServiceImpl;
    }

    @GetMapping
    public ResponseEntity<List<CategoriaProdutoResponse>> getCategoriasProdutos() {
        List<CategoriaProdutoResponse> categoriasProdutos =
                categoriaProdutoServiceImpl.findAll();

        return ResponseEntity.ok(categoriasProdutos);
    }
}