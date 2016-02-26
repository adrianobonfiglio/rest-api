package br.com.restapi.expceptions;

import java.util.List;

import org.springframework.validation.FieldError;

public class ValidationException extends SapBusinessException {
	
	private List<FieldError> errors;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ValidationException(String code, String message, String resource, List<FieldError> errors) {
		super(message);
		this.resource = resource;
		this.code = code;
		this.message = message;
		this.errors = errors;
	}
	
	public List<FieldError> getErrors() {
		return errors;
	}
}
