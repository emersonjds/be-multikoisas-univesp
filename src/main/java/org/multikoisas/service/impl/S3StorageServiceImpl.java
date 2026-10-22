package org.multikoisas.service.impl;

import lombok.RequiredArgsConstructor;
import org.multikoisas.dto.ProdutoImagemResponse;
import org.multikoisas.service.S3StorageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class S3StorageServiceImpl implements S3StorageService {

    private final S3Client s3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    @Value("${cloud.aws.s3.endpoint}")
    private String endpoint;

    @Override
    public String uploadFile(MultipartFile file) {

        if (file.isEmpty()) {
            throw new IllegalArgumentException("O ficheiro não pode estar vazio.");
        }

        // Gera um identificador único com a extensão original do ficheiro
        String originalFilename = file.getOriginalFilename();
        String extension = originalFilename != null && originalFilename.contains(".")
                ? originalFilename.substring(originalFilename.lastIndexOf("."))
                : "";
        String uniqueFileName = UUID.randomUUID().toString() + extension;

        try {
            // Constrói o contrato de envio estipulando o bucket, o nome do ficheiro e o tipo MIME (ex: image/png)
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(uniqueFileName)
                    .contentType(file.getContentType())
                    .build();

            // Transmite os bytes de forma eficiente diretamente do fluxo da requisição HTTP
            s3Client.putObject(putObjectRequest,
                    RequestBody.fromInputStream(file.getInputStream(), file.getSize()));

            // Devolve a URL pública assumindo que o seu bucket no Supabase está configurado como público
            // O padrão da URL pública no Supabase foge do endpoint S3 padrão. É construído desta forma:
            return endpoint.replace("/s3", "") + "/public/" + bucketName + "/" + uniqueFileName;

        } catch (IOException e) {
            throw new RuntimeException("Falha ao processar o fluxo do ficheiro de imagem.", e);
        }
    }

    public ProdutoImagemResponse delete(Integer id, Integer productId){ return null;}

    private String getExtension(String filename) {
        return (filename != null && filename.contains(".")) ? filename.substring(filename.lastIndexOf(".")) : "";
    }
}
