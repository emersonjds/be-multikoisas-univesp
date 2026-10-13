package org.multikoisas.controller;

import org.multikoisas.dto.CanalVendaProdutoResponse;
import org.multikoisas.service.impl.CanalVendaProdutoServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/canalvendaproduto")
public class CanalVendaProdutoController {

    private final CanalVendaProdutoServiceImpl canalVendaProdutoServiceImpl;

    public CanalVendaProdutoController(
            CanalVendaProdutoServiceImpl canalVendaProdutoServiceImpl) {
        this.canalVendaProdutoServiceImpl = canalVendaProdutoServiceImpl;
    }

    @GetMapping
    public ResponseEntity<List<CanalVendaProdutoResponse>> getCanaisVendaProdutos() {
        List<CanalVendaProdutoResponse> canaisVendaProdutos =
                canalVendaProdutoServiceImpl.findAll();

        return ResponseEntity.ok(canaisVendaProdutos);
    }
}