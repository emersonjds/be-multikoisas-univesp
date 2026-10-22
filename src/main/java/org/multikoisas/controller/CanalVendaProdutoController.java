package org.multikoisas.controller;

import jakarta.validation.Valid;
import org.multikoisas.dto.CanalVendaProdutoRequest;
import org.multikoisas.dto.CanalVendaProdutoResponse;
import org.multikoisas.service.impl.CanalVendaProdutoServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @GetMapping(params = "produto")
    public ResponseEntity<List<CanalVendaProdutoResponse>> getProduto(
            @RequestParam("produto") Integer produtoId) {

        List<CanalVendaProdutoResponse> response =
                canalVendaProdutoServiceImpl.findByProdutoId(produtoId);

        return response.isEmpty()
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(response);
    }

    @GetMapping(params = "canalVenda")
    public ResponseEntity<List<CanalVendaProdutoResponse>> getCanalVenda(
            @RequestParam("canalVenda") Integer canalVendaId) {

        List<CanalVendaProdutoResponse> response =
                canalVendaProdutoServiceImpl.findByCanalVendaId(canalVendaId);

        return response.isEmpty()
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<Object> save(
            @Valid @RequestBody CanalVendaProdutoRequest request) {

        canalVendaProdutoServiceImpl.save(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of(
                        "mensagem",
                        "Canal de venda associado ao produto com sucesso."
                ));
    }

    @DeleteMapping
    public ResponseEntity<Object> delete(
            @Valid @RequestBody CanalVendaProdutoRequest request) {

        boolean response = canalVendaProdutoServiceImpl.delete(
                request.getProdutoId(),
                request.getCanalVendaId()
        );

        return response
                ? ResponseEntity.ok(
                Map.of(
                        "mensagem",
                        "Canal de venda desassociado do produto com sucesso."
                )
        )
                : ResponseEntity.notFound().build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(
            MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult()
                .getFieldErrors()
                .forEach(error ->
                        errors.put(
                                error.getField(),
                                error.getDefaultMessage()
                        )
                );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errors);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(
            IllegalArgumentException ex) {

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(ex.getMessage());
    }


}