package com.djv.bodesafio.djvbox.s3.service;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Optional;

import com.djv.bodesafio.djvbox.s3.dtos.FileStorageDTO;
import com.djv.bodesafio.djvbox.s3.models.ClientModel;
import com.djv.bodesafio.djvbox.s3.models.FileStorageModel;
import com.djv.bodesafio.djvbox.s3.repositories.ClientRepository;
import com.djv.bodesafio.djvbox.s3.repositories.FileStorageRepository;
import com.djv.bodesafio.djvbox.s3.service.api.S3Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileStoreService {

	@Autowired
	FileStorageRepository fileStorageRepository;

	@Autowired
	ClientRepository clientRepository;

	@Autowired
	S3Services s3Services;

	public FileStorageDTO save(Long clientId, MultipartFile file) throws IOException {

		ClientModel client = clientRepository.findById(clientId)
				.orElseThrow(() -> new ResourceNotFoundException("Cliente nao cadastro no banco de dados"));

		try {
			String filename = s3Services.uploadFile(file);

			FileStorageModel fs = new FileStorageModel();
			Optional<FileStorageModel> opt = this.fileStorageRepository.findByClientId(clientId);
			if (opt.isPresent()) {
				s3Services.deleteFile(opt.get().getFilename());
				fs.setId(opt.get().getId());

			}

			fs.setClient(client);
			fs.setFilename(filename);
			FileStorageDTO dto = ClassConverterBuilder.build(fileStorageRepository.save(fs), FileStorageDTO.class);
			return dto;

		} catch (IOException e) {
			throw new IOException(e);
		}

	}

	public FileStorageDTO findByClientId(Long clientId) {
		FileStorageModel file = this.fileStorageRepository.findByClientId(clientId)
				.orElseThrow(() -> new ResourceNotFoundException("Nenhum arquivo encontrado"));
		return ClassConverterBuilder.build(file, FileStorageDTO.class);
	}

	public ByteArrayOutputStream findByFileName(Long clientId, String filename) {

		clientRepository.findById(clientId)
				.orElseThrow(() -> new ResourceNotFoundException("Cliente nao cadastro no banco de dados"));

		this.fileStorageRepository.findByFilename(filename)
				.orElseThrow(() -> new ResourceNotFoundException("Nenhum arquivo encontrado"));

		ByteArrayOutputStream downloadInputStream = s3Services.downloadFile(filename);

		return downloadInputStream;
	}

}
