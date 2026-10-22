
package org.multikoisas.controller;

import java.util.List;

import org.multikoisas.dto.ProdutoRequest;
import org.multikoisas.dto.ProdutoResponse;
import org.multikoisas.service.ProdutoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/produto")
public class ProdutoController {

    private final ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @GetMapping
    public ResponseEntity<List<ProdutoResponse>> getProdutos() {
        return ResponseEntity.ok(produtoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoResponse> getProdutoById(
            @PathVariable Integer id) {
        return ResponseEntity.ok(produtoService.findById(id));
    }

    @PostMapping
    public ResponseEntity<ProdutoResponse> createProduto(
            @RequestBody ProdutoRequest request) {
        ProdutoResponse response = produtoService.save(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateProduto(
            @PathVariable Integer id,
            @RequestBody ProdutoRequest request) {
        produtoService.update(id, request);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduto(
            @PathVariable Integer id) {
        produtoService.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
