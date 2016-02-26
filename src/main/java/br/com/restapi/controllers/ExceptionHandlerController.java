package br.com.restapi.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import br.com.restapi.expceptions.ResourceNotFoundException;
import br.com.restapi.expceptions.ValidationException;
import br.com.restapi.helper.DefaultResponse;
import br.com.restapi.helper.FieldsValidationErrors;
import br.com.restapi.helper.ResponseValidationErrors;

@ControllerAdvice
public class ExceptionHandlerController {
	
	@ExceptionHandler(ResourceNotFoundException.class)
	@ResponseStatus(code=HttpStatus.NOT_FOUND)
	public @ResponseBody DefaultResponse resourceNotFound(ResourceNotFoundException e) {
		DefaultResponse responseEntity = new DefaultResponse();
		responseEntity.setCode(e.getCode());
		responseEntity.setResource(e.getResource());
		responseEntity.setMessage(e.getMessage());
		return responseEntity;
	}
	
	@ExceptionHandler(ValidationException.class)
	@ResponseStatus(code=HttpStatus.UNPROCESSABLE_ENTITY)
	public @ResponseBody DefaultResponse validationException(ValidationException e) {
		ResponseValidationErrors errors = new ResponseValidationErrors();
		List<FieldsValidationErrors> fieldErros = new ArrayList<FieldsValidationErrors>();
		for (FieldError error : e.getErrors()) {
			FieldsValidationErrors fieldError = new FieldsValidationErrors();
			fieldError.setField(error.getField());
			fieldError.setMessage(error.getDefaultMessage());
			fieldErros.add(fieldError);
		}
		errors.setCode(e.getCode());
		errors.setMessage(e.getMessage());
		errors.setResource(e.getResource());
		errors.setErrors(fieldErros);
		return errors;
		
	}
	
}
