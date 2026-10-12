package org.multikoisas.controller;

import org.multikoisas.dto.CanalVendaResponse;
import org.multikoisas.service.impl.CanalVendaServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/canalvenda")
public class CanalVendaController {

    private final CanalVendaServiceImpl canalVendaServiceImpl;

    public CanalVendaController(CanalVendaServiceImpl canalVendaServiceImpl) {
        this.canalVendaServiceImpl = canalVendaServiceImpl;
    }

    @GetMapping
    public ResponseEntity<List<CanalVendaResponse>> getCanaisVenda() {
        List<CanalVendaResponse> canaisVenda = canalVendaServiceImpl.findAll();
        return ResponseEntity.ok(canaisVenda);
    }
}
