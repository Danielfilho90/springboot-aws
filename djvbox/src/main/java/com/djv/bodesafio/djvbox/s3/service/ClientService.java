package com.djv.bodesafio.djvbox.s3.service;


import java.time.format.DateTimeParseException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import com.djv.bodesafio.djvbox.s3.dtos.ClientDTO;
import com.djv.bodesafio.djvbox.s3.enums.ClientStatus;
import com.djv.bodesafio.djvbox.s3.exceptionss.DuplicateConstraintException;
import com.djv.bodesafio.djvbox.s3.forms.ClientForms;
import com.djv.bodesafio.djvbox.s3.models.ClientModel;
import com.djv.bodesafio.djvbox.s3.repositories.ClientRepository;
import com.djv.bodesafio.djvbox.s3.service.api.SendGridService;
import com.djv.bodesafio.djvbox.s3.utils.ClassConverterBuilder;
import com.sendgrid.Response;


@Service
public class ClientService {

	@Autowired
	private ClientRepository clientRepository;

	@Autowired
	private SendGridService sgService;

	public ClientDTO create(@Valid ClientForms clientForms) {

		try {

			var client = ClassConverterBuilder.build(clientForms, ClientModel.class);

			var existingClient = clientRepository.findByEmail(clientForms.getEmail());

			if (existingClient != null) {
				throw new DuplicateConstraintException("Email existente no banco de dados!");
			}

			ClientModel newClientModel = clientRepository.save(ClientModel);
			return ClassConverterBuilder.build(newClientModel, ClientDTO.class);

		}

	}

	public List<ClientDTO> findAll() {
		List<ClientModel> clients = this.clientRepository.findAll();
		return ClassConverterBuilder.buildList(clients, ClientDTO.class);
	}

	public ClientDTO findById(Long id) {
		var clientModel = clientRepository.findById(id).orElseThrow(
				() -> new ResourceNotFoundException("Nenhum registro encontrado para o cliente informado"));

		return ClassConverterBuilder.build(clientModel, ClientDTO.class);
	}

	public ClientModel findEntityById(Long id) {
		var clientModel = clientRepository.findById(id).orElseThrow(
				() -> new ResourceNotFoundException("Nenhum registro encontrado para o cliente informado"));
		return clientModel;
	}

	public ClientDTO update(Long id, @Valid ClientForms clientForms) {
		var client = findEntityById(id);
		client.setCpf(clientForms.getCpf());
		client.setEmail(clientForms.getEmail());
		client.setFirstName(clientForms.getFirstName());
		client.setLastName(clientForms.getLastName());
		return ClassConverterBuilder.build(clientRepository.save(client), ClientDTO.class);
	}

	public void delete(Long id) {
		var client = findEntityById(id);
		clientRepository.deleteById(client.getId());
	}

	public String confirm(Long id) {
		ClientDTO dto = this.validade(id);
		sgService.sendMailConfirmation(dto.getEmail());
		ClientModel client = clientRepository.findById(id).orElseThrow(
				() -> new ResourceNotFoundException("Nenhum registro encontrado para o cliente informado"));
		client.setStatus(ClientStatus.ACTIVE);
		clientRepository.save(client);
		return client.getFirstName()
				+ ". Parabéns, dados registrados com sucesso, em breve voce receberá um e-mail com mais instrucoes";

	}

	public ClientDTO validade(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

}
