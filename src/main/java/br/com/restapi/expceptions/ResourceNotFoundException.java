package br.com.restapi.expceptions;

public class ResourceNotFoundException extends SapBusinessException {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	public ResourceNotFoundException(String code, String message, String resource) {
		super(code, message, resource);
	}
	
}
