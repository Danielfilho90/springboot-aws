package com.djv.bodesafio.djvbox.s3.utils;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.net.URI;

import com.djv.bodesafio.djvbox.s3.controller.BucketController;
import com.djv.bodesafio.djvbox.s3.controller.ClientController;
import com.djv.bodesafio.djvbox.s3.dtos.FileStorageDTO;



public class FileStorageHateoasUtils {

	public static void create(FileStorageDTO model) {
		model.add(linkTo(methodOn(BucketController.class).find(model.getId())).withSelfRel())
				.add(linkTo(methodOn(BucketController.class, model.getClient().getId())
						.downloadFile(model.getClient().getId(), model.getFilename())).withRel("download"))
				.add(linkTo(methodOn(ClientController.class).findByid(model.getClient().getId())).withRel("client"));
	}
}
