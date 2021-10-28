package com.djv.bodesafio.djvbox.s3.dtos;


import java.io.Serializable;

import com.djv.bodesafio.djvbox.s3.enums.ClientStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import org.springframework.hateoas.RepresentationModel;

import lombok.EqualsAndHashCode;

@JsonInclude(Include.NON_NULL)
@EqualsAndHashCode(callSuper = false)
public class ClientDTO extends RepresentationModel<ClientDTO> implements Serializable {

	private static final long serialVersionUID = 7758676040163998645L;

	@EqualsAndHashCode.Include
	private Long id;
	private String firstName;
	private String lastName;
	private String email;
	private String cpf;
	@JsonIgnore
	private FileStorageDTO file;

	private ClientStatus status;

	public String getId() {
		// TODO Auto-generated method stub
		return null;
	}

	public Object getFile() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getEmail() {
		// TODO Auto-generated method stub
		return null;
	}

}