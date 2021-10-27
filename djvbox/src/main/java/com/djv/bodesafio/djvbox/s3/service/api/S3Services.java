package com.djv.bodesafio.djvbox.s3.service.api;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public interface S3Services {

	public ByteArrayOutputStream downloadFile(String keyName);

	public String uploadFile(MultipartFile file) throws IOException;

	public void deleteFile(String file);

}
