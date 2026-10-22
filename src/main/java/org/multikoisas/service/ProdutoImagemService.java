package org.multikoisas.service;


import org.multikoisas.dto.ProdutoImagemRequest;
import org.multikoisas.dto.ProdutoImagemResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

public interface ProdutoImagemService {

    ProdutoImagemResponse save(ProdutoImagemRequest request);

    ProdutoImagemResponse delete(Integer id, Integer productId);

}