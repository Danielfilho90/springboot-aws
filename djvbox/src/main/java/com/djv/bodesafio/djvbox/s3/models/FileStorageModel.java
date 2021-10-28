package com.djv.bodesafio.djvbox.s3.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class FileStorageModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Long id;
	@OneToOne
	private ClientModel client;
	@Column(nullable = false)
	private String filename;
	public String getFilename() {
		// TODO Auto-generated method stub
		return null;
	}
	public Object getId() {
		// TODO Auto-generated method stub
		return null;
	}
	public void setClient(ClientModel client2) {
		// TODO Auto-generated method stub
		
	}
	public void setId(Object id2) {
		// TODO Auto-generated method stub
		
	}

}