package com.djv.bodesafio.djvbox.s3.exceptionss.handlersExceptions;

import java.util.ArrayList;
import java.util.List;

import com.djv.bodesafio.djvbox.s3.exceptionss.CustomizedValidationError;
import com.djv.bodesafio.djvbox.s3.exceptionss.MyUnprocessableEntityException;

import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomizedExceptionHandlers {

	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public List<CustomizedValidationError> handleValidationExceptions(MethodArgumentNotValidException ex) {
		List<CustomizedValidationError> errors = new ArrayList<CustomizedValidationError>();
		ex.getBindingResult().getAllErrors().forEach((error) -> {
			CustomizedValidationError erro = new CustomizedValidationError(error.getDefaultMessage(),
					((FieldError) error).getField());
			errors.add(erro);
		});
		return errors;
	}

	@ResponseStatus(code = HttpStatus.NOT_FOUND)
	@ExceptionHandler(ResourceNotFoundException.class)
	public CustomizedValidationError handleResourcesNotFoundExceptions(Exception ex) {
		CustomizedValidationError customError = new CustomizedValidationError(ex.getMessage());
		return customError;
	}

	@ResponseStatus(code = HttpStatus.UNPROCESSABLE_ENTITY)
	@ExceptionHandler(MyUnprocessableEntityException.class)
	public CustomizedValidationError handleUnprocessableEntityExceptions(Exception ex) {
		CustomizedValidationError customError = new CustomizedValidationError(ex.getMessage());
		return customError;
	}

}
