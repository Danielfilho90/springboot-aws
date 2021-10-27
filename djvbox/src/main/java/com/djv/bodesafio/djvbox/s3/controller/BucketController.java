package com.djv.bodesafio.djvbox.s3.controller;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URI;

import com.djv.bodesafio.djvbox.s3.dtos.FileStorageDTO;
import com.djv.bodesafio.djvbox.s3.service.FileStoreService;
import com.djv.bodesafio.djvbox.s3.service.api.S3Services;
import com.djv.bodesafio.djvbox.s3.utils.ContentTypeUtils;
import com.djv.bodesafio.djvbox.s3.utils.FileStorageHateoasUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


@RestController
@Api(value = "File Endpoint", tags = { "Files"})
@RequestMapping("/file/{clientId}")
public class BucketController {

	@Autowired
	S3Services s3Services;

	@Autowired
	FileStoreService fileStoreService;

	@PostMapping
	@ApiOperation(value = "Upload a document photo from a client")
	public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file, @PathVariable Long clientId)
			throws IOException {

		FileStorageDTO dto = fileStoreService.save(clientId, file);
		return ResponseEntity.created(URI.create("/files/" + clientId + "/" + dto.getFilename())).body(dto);

	}

	@GetMapping("/{keyname}")
	@ApiOperation(value = "Download a document photo from a client")
	public ResponseEntity<byte[]> downloadFile(@PathVariable("clientId") Long clientId, @PathVariable String keyname) {

		ByteArrayOutputStream downloadInputStream = fileStoreService.findByFileName(clientId, keyname);

		return ResponseEntity.ok().contentType(ContentTypeUtils.contentType(keyname))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + keyname + "\"")
				.body(downloadInputStream.toByteArray());
	}

	@GetMapping
	@ApiOperation(value = "Find a document from a client")
	public FileStorageDTO find(@PathVariable("clientId") Long clientId) {
		FileStorageDTO dto = fileStoreService.findByClientId(clientId);
		FileStorageHateoasUtils.create(dto);
		return dto;
	}

}
