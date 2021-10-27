package com.djv.bodesafio.djvbox.s3.repositories;

import com.djv.bodesafio.djvbox.s3.models.ClientModel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ClientRepository extends JpaRepository<ClientModel, Long> {
	
	
	ClientModel findByEmail(String email);//CPF??

}
