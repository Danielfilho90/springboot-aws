package com.djv.bodesafio.djvbox.s3.forms;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.br.CPF;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
public class ClientForms implements Serializable {

	private static final long serialVersionUID = 2012963711330474199L;

	@NotEmpty
	private String firstName;
	@NotEmpty
	private String lastName;
	@Email
	@NotEmpty
	private String email;
	@NotEmpty
	@CPF
	private String cpf;
	public Object getFile() {
		// TODO Auto-generated method stub
		return null;
	}
	public Long getId() {
		// TODO Auto-generated method stub
		return null;
	}
	public String getEmail() {
		// TODO Auto-generated method stub
		return null;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getCpf() {
		// TODO Auto-generated method stub
		return null;
	}
	
}