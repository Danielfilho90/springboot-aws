package com.djv.bodesafio.djvbox.s3.utils;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import javax.validation.Valid;

import com.djv.bodesafio.djvbox.s3.controller.BucketController;
import com.djv.bodesafio.djvbox.s3.controller.ClientController;
import com.djv.bodesafio.djvbox.s3.dtos.ClientDTO;
import com.djv.bodesafio.djvbox.s3.forms.ClientForms;

public class ClientHateoasUtils {

	public static void create(@Valid ClientForms clientForms) {
		clientForms.add(linkTo(methodOn(ClientController.class).findByid(clientForms.getId())).withSelfRel());
		if (clientForms.getFile() != null) {
			clientForms.add(
					linkTo(methodOn(BucketController.class).downloadFile(clientForms.getId(), clientForms.getFile().getFilename()))
							.withRel("file"));
		}
	}
}
