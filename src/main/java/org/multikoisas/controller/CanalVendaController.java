
package org.multikoisas.controller;

import java.util.List;

import org.multikoisas.dto.CanalVendaRequest;
import org.multikoisas.dto.CanalVendaResponse;
import org.multikoisas.service.CanalVendaService;
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
@RequestMapping("/canalvenda")
public class CanalVendaController {

    private final CanalVendaService canalVendaService;

    public CanalVendaController(CanalVendaService canalVendaService) {
        this.canalVendaService = canalVendaService;
    }

    @GetMapping
    public ResponseEntity<List<CanalVendaResponse>> getCanaisVenda() {
        return ResponseEntity.ok(canalVendaService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CanalVendaResponse> getCanalVendaById(
            @PathVariable Integer id) {
        return ResponseEntity.ok(canalVendaService.findById(id));
    }

    @PostMapping
    public ResponseEntity<CanalVendaResponse> createCanalVenda(
            @RequestBody CanalVendaRequest request) {
        CanalVendaResponse response = canalVendaService.save(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateCanalVenda(
            @PathVariable Integer id,
            @RequestBody CanalVendaRequest request) {

        canalVendaService.update(id, request);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCanalVenda(
            @PathVariable Integer id) {

        canalVendaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
