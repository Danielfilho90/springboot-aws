package com.djv.bodesafio.djvbox.s3.controller;


import org.hibernate.cache.spi.Region;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import net.minidev.json.JSONArray;

import java.util.List;
import java.util.ListIterator;

import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.S3Object;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;
import com.djv.bodesafio.djvbox.s3.service.StorageService;

@RestController
@RequestMapping("/file")
public class ListController {

    @Autowired
    public StorageService service;
    @GetMapping("/list")
    public ResponseEntity<?> listItems() {

        try {

            // Seleciona a região onde o bucket existe
            Region region = Region.US_EAST_1;

            // Configura cliente para conexão
            S3Client s3 = S3Client.builder()
                    .region(region)
                    .build();

            // Cria uma requisição de buscar dados do bucket
            ListObjectsRequest listObjectsRequest = ListObjectsRequest
                    .builder()
                    .bucket(defaultBucketName)
                    .build();

            // Efetua recuperação dos dados do bucket
            ListObjectsResponse listObjectsResponse = s3.listObjects(listObjectsRequest);

            // Cria uma lista de S3Object para iterar depois
            List<S3Object> objectsList = listObjectsResponse.contents();
            JSONArray responseArrayContent = new JSONArray();

            // Itera sobre os objetos para incluir no array com o nome dos arquivos existentes
            for (ListIterator<S3Object> iterVals = objectsList.listIterator(); iterVals.hasNext(); ) {
                S3Object objectVal = (S3Object) iterVals.next();
                responseArrayContent.put(objectVal.key());
            }

            // Devolve resposta com o conteúdo em JSON
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .header(HttpHeaders.CONTENT_TYPE, "application/json")
                    .body("{ \"files\":"+responseArrayContent.toString()+" }");

        } catch (S3Exception s3e) {

                System.err.println(s3e.awsErrorDetails().errorMessage());

                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .header(HttpHeaders.CONTENT_TYPE, "application/json")
                        .body(" \"message\": ERROR" + s3e.awsErrorDetails().errorMessage());

        }

    }
}
