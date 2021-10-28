package com.djv.bodesafio.djvbox.s3.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.djv.bodesafio.djvbox.s3.dtos.ClientDTO;
import com.djv.bodesafio.djvbox.s3.forms.ClientForms;
import com.djv.bodesafio.djvbox.s3.service.ClientService;
import com.djv.bodesafio.djvbox.s3.utils.ClientHateoasUtils;
import com.sendgrid.Response;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/clients")
@Api(value = "Client Endpoint", tags = { "Client" })
public class ClientController {

	@Autowired
	private ClientService clientService;

	@PostMapping
	@ApiOperation(value = "Create a client Proposal")
	public ResponseEntity<?> create(@RequestBody @Valid ClientForms clientForms) {

		ClientDTO client = this.clientService.create(clientForms);

		ClientHateoasUtils.create(clientForms);

		return ResponseEntity.created(URI.create("/clients/" + client.getId())).body(client);

	}

	@GetMapping
	@ApiOperation(value = "Finds All Clients Proposals")
	public List<ClientDTO> findAll() {
		List<ClientDTO> clients = this.clientService.findAll();
		clients.stream().forEach(c -> ClientHateoasUtils.create(c));
		return clients;
	}

	@GetMapping(value = "/{id}")
	@ApiOperation(value = "Find a client proposal by client id")
	public ClientDTO findByid(@PathVariable("id") Long id) {
		ClientDTO clientDTO = this.clientService.findById(id);
		ClientHateoasUtils.create(clientDTO);
		return clientDTO;
	}

	@PutMapping(value = "/{id}")
	@ApiOperation(value = "updates a client proposal by client id")
	public ClientDTO update(@PathVariable("id") Long id, @RequestBody @Valid ClientForms clientForms) {
		ClientDTO updatedClientDto = this.clientService.update(id, clientForms);
		ClientHateoasUtils.create(updatedClientDto);
		return updatedClientDto;
	}

	@DeleteMapping(value = "/{id}")
	@ApiOperation(value = "Deletes a client proposal by  client id")
	public ResponseEntity<?> delete(@PathVariable("id") Long id) {
		this.clientService.delete(id);
		return ResponseEntity.ok().build();
	}

	@GetMapping(value = "/{id}/validate")
	@ApiOperation(value = "Validates a client proposal by client id")
	public ClientDTO validade(@PathVariable("id") Long id) {
		ClientDTO dto = this.clientService.validade(id);
		ClientHateoasUtils.create(dto);
		return dto;
	}

	@PostMapping(value = "/{id}/confirm")
	@ApiOperation(value = "Confirms a client proposal by client id")
	public ResponseEntity<?> confirm(@PathVariable("id") Long id) {
		return ResponseEntity.ok().body(this.clientService.confirm(id));
	}

}

