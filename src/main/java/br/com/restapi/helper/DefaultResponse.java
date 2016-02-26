package br.com.restapi.helper;

/**
 * @author I852136
 *
 */
public class DefaultResponse {
	
	public String code;
	public String message;
	public String resource;
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getResource() {
		return resource;
	}
	public void setResource(String resource) {
		this.resource = resource;
	}

	
}
