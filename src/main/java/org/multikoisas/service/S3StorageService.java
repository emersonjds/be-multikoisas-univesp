package org.multikoisas.service;

import org.multikoisas.dto.ProdutoImagemResponse;
import org.springframework.web.multipart.MultipartFile;

public interface S3StorageService {

    String uploadFile(MultipartFile file);

    ProdutoImagemResponse delete(Integer id, Integer productId);

}
