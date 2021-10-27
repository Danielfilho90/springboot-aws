package com.djv.bodesafio.djvbox.s3.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.djv.bodesafio.djvbox.s3.enums.ClientStatus;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ClientModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Long id;

	@Column(nullable = false)
	private String firstName;
	@Column(nullable = false)
	private String lastName;
	@Column(nullable = false, unique = true)
	private String email;

	@Column(nullable = false)
	private String cpf;

	@OneToOne(mappedBy = "client")
	@Cascade(CascadeType.DELETE)
	private FileStorageModel file;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private ClientStatus status = ClientStatus.INACTIVE;

}

