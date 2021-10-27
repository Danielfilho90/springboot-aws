package com.djv.bodesafio.djvbox.s3.controller;


import org.hibernate.cache.spi.Region;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import com.amazonaws.services.s3.model.PutObjectRequest;
import com.djv.bodesafio.djvbox.s3.service.StorageService;

@RestController
@RequestMapping("/file")


public class UploadController {
    @Autowired
    public StorageService service;
@PostMapping("/upload")
    public ResponseEntity<?> uploadItem(@RequestParam("file") MultipartFile file) {

        try {

        // Seleciona a região onde o bucket existe
            Region region = Region.US_EAST_1;

        // Configura cliente para conexão
            S3Client s3 = S3Client.builder()
                    .region(region)
                    .build();

        // Cria uma requisição de enviar dados para o bucket com o nome do arquivo no parâmetro .key() * Obrigatório
            PutObjectRequest putObjectRequest = PutObjectRequest
                    .builder()
                    .key(file.getOriginalFilename())
                    .bucket(defaultBucketName)
                    .build();

        // Pega resposta de upload enviando a requisição dos dados de arquivo e os bytes do arquivo enviado via API.
            PutObjectResponse putObjectResponse = s3.putObject(putObjectRequest, RequestBody.fromBytes(file.getBytes()));

        // Devolve como resposta o identificador único que a AWS provê.
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .header(HttpHeaders.CONTENT_TYPE, "application/json")
                    .body("{ \"file_identifier\":"+putObjectResponse.eTag()+" }");

        } catch (S3Exception s3e) {

            System.err.println(s3e.awsErrorDetails().errorMessage());

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .header(HttpHeaders.CONTENT_TYPE, "application/json")
                    .body(" \"message\": " + s3e.awsErrorDetails().errorMessage());

        } catch (IOException ioe) {

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .header(HttpHeaders.CONTENT_TYPE, "application/json")
                    .body(" \"message\": " + ioe.getMessage());
        }
    }
}