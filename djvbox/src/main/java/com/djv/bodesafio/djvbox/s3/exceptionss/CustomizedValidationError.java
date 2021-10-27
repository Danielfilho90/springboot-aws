package com.djv.bodesafio.djvbox.s3.exceptionss;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


@JsonInclude(Include.NON_NULL)
public class CustomizedValidationError {

	private String message;
	private String field;
	

	public CustomizedValidationError(String message, String field) {
		this.message = message;
		this.field = field;
	}

	public CustomizedValidationError(String message) {
		this.message = message;
	}

	public CustomizedValidationError() {

	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}
	

	public CustomizedValidationError serialize(Exception ex) {
		CustomizedValidationError DTO = new CustomizedValidationError();
		DTO.setMessage(ex.getMessage());
		return DTO;
	}

}