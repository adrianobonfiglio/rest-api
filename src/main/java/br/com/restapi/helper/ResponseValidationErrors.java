package br.com.restapi.helper;

import java.util.List;

public class ResponseValidationErrors extends DefaultResponse {
	
	private List<FieldsValidationErrors> errors;
	
	public void setErrors(List<FieldsValidationErrors> errors) {
		this.errors = errors;
	}
	
	public List<FieldsValidationErrors> getErrors() {
		return errors;
	}

}
