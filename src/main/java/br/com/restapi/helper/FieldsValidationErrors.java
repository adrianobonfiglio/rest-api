package br.com.restapi.helper;

public class FieldsValidationErrors {

	private String field;
	private String message;
	
	public void setField(String field) {
		this.field = field;
	}
	
	public String getField() {
		return field;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public String getMessage() {
		return this.message;
	}
	

}
