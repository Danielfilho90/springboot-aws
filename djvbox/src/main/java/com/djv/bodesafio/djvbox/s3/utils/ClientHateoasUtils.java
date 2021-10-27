package com.djv.bodesafio.djvbox.s3.utils;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.djv.bodesafio.djvbox.s3.controller.BucketController;
import com.djv.bodesafio.djvbox.s3.controller.ClientController;
import com.djv.bodesafio.djvbox.s3.dtos.ClientDTO;

public class ClientHateoasUtils {

	public static void create(ClientDTO model) {
		model.add(linkTo(methodOn(ClientController.class).findByid(model.getId())).withSelfRel());
		if (model.getFile() != null) {
			model.add(
					linkTo(methodOn(BucketController.class).downloadFile(model.getId(), model.getFile().getFilename()))
							.withRel("file"));
		}
	}
}
