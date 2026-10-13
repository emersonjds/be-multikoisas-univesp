package org.multikoisas.controller;

import org.multikoisas.dto.ProdutoResponse;
import org.multikoisas.service.impl.ProdutoServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/produto")
public class ProdutoController {

    private final ProdutoServiceImpl produtoServiceImpl;

    public ProdutoController(ProdutoServiceImpl produtoServiceImpl) {
        this.produtoServiceImpl = produtoServiceImpl;
    }

    @GetMapping
    public ResponseEntity<List<ProdutoResponse>> getProdutos() {
        List<ProdutoResponse> produtos = produtoServiceImpl.findAll();
        return ResponseEntity.ok(produtos);
    }
}
