package com.djv.bodesafio.djvbox.s3.service.api;
import com.sendgrid.Response;

public interface SendGridService {

	public Response sendMailConfirmation(String toEmail);

}
