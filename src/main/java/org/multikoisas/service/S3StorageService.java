package org.multikoisas.service;

import org.springframework.web.multipart.MultipartFile;

public interface S3StorageService {

    String uploadFile(MultipartFile file);

}
