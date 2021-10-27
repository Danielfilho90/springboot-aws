package com.djv.bodesafio.djvbox.s3.forms;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
public class FileStorageForms implements Serializable {

	private static final long serialVersionUID = 1817250501284831856L;

	@NotNull
	private String filename;

}
