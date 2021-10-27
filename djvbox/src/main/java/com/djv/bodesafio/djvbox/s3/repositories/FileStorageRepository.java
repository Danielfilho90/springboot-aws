package com.djv.bodesafio.djvbox.s3.repositories;


import java.util.Optional;

import com.djv.bodesafio.djvbox.s3.models.FileStorageModel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface FileStorageRepository extends JpaRepository<FileStorageModel, Long> {

	Optional<FileStorageModel> findByClientId(Long clientId);

	Optional<FileStorageModel> findByFilename(String filename);

}
