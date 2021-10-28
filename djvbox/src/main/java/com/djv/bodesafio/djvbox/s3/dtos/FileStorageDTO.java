package com.djv.bodesafio.djvbox.s3.dtos;

import java.io.Serializable;

import com.djv.bodesafio.djvbox.s3.models.ClientModel;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import org.springframework.hateoas.RepresentationModel;

import lombok.EqualsAndHashCode;

@JsonInclude(Include.NON_NULL)
@EqualsAndHashCode(callSuper = false)
public class FileStorageDTO extends RepresentationModel<FileStorageDTO> implements Serializable {

	private static final long serialVersionUID = -2076251076082242246L;

	@EqualsAndHashCode.Include
	private Long id;
	@JsonIgnore
	private ClientModel clientModel;
	private String filename;
	public String getFilename() {
		// TODO Auto-generated method stub
		return null;
	}
	public Long getId() {
		// TODO Auto-generated method stub
		return null;
	}
	public FileStorageDTO getClient() {
		// TODO Auto-generated method stub
		return null;
	}

}
