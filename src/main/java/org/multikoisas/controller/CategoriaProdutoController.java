package org.multikoisas.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.multikoisas.dto.CategoriaProdutoRequest;
import org.multikoisas.dto.CategoriaProdutoResponse;
import org.multikoisas.service.impl.CategoriaProdutoServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/categoriaproduto")
@RequiredArgsConstructor

public class CategoriaProdutoController {

    private final CategoriaProdutoServiceImpl service;

    @GetMapping
    public ResponseEntity<List<CategoriaProdutoResponse>> getCategoriasProdutos() {
        List<CategoriaProdutoResponse> response = service.findAll();

        return response.isEmpty()? ResponseEntity.notFound().build() : ResponseEntity.ok(response);
    }

    @GetMapping(params = "produto")
    public ResponseEntity<List<CategoriaProdutoResponse>> getProduto(@RequestParam("produto") Integer produtoId){
        List<CategoriaProdutoResponse> response = service.findByProdutoId(produtoId);
        return response.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(response);
    }

    @GetMapping(params = "categoria")
    public ResponseEntity<List<CategoriaProdutoResponse>> getCategoria(@RequestParam("categoria") Integer categoriaId){
        List<CategoriaProdutoResponse> response = service.findByCategoriaId(categoriaId);
        return response.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(response);
    }


    @PostMapping
    public ResponseEntity<Object> save(@Valid @RequestBody CategoriaProdutoRequest request) {

        service.save(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of("mensagem", "Categoria associada ao produto com sucesso."));
    }


    @DeleteMapping
    public ResponseEntity<Object> delete(@Valid @RequestBody CategoriaProdutoRequest request) {

        boolean response = service.delete(request.getProdutoId(), request.getCategoriaId());

        return response ? ResponseEntity.ok(Map.of("mensagem", "Categoria desassociada do produto com sucesso.")) : ResponseEntity.notFound().build();
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
