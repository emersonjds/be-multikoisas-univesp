
package org.multikoisas.controller;

import java.util.List;

import org.multikoisas.dto.CategoriaRequest;
import org.multikoisas.dto.CategoriaResponse;
import org.multikoisas.service.CategoriaService;
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
@RequestMapping("/categoria")
public class CategoriaController {

    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @GetMapping
    public ResponseEntity<List<CategoriaResponse>> getCategorias() {
        return ResponseEntity.ok(categoriaService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaResponse> getCategoriaById(
            @PathVariable Integer id) {
        return ResponseEntity.ok(categoriaService.findById(id));
    }

    @PostMapping
    public ResponseEntity<CategoriaResponse> createCategoria(
            @RequestBody CategoriaRequest request) {
        CategoriaResponse response = categoriaService.save(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateCategoria(
            @PathVariable Integer id,
            @RequestBody CategoriaRequest request) {
        categoriaService.update(id, request);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategoria(
            @PathVariable Integer id) {
        categoriaService.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
