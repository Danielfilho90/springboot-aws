package com.djv.bodesafio.djvbox.s3.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.djv.bodesafio.djvbox.s3.service.StorageService;

@RestController
@RequestMapping("/file")

public class DeleteController {
    @Autowired
    public StorageService service;

    @DeleteMapping("/delete/{fileName}")
    public ResponseEntity<String> deleteFile(@PathVariable String fileName) {
        return new ResponseEntity<>(service.deleteFile(fileName), HttpStatus.OK);
    }
}
