package br.com.restapi.expceptions;

/**
 * @author I852136
 *
 */
public class SapBusinessException extends RuntimeException {
	
	protected String code;
	protected String message;
	protected String resource;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * @param message
	 */
	public SapBusinessException(String message) {
		super(message);
	}
	
	public SapBusinessException(String code, String message, String resource) {
		this.code = code;
		this.message = message;
		this.resource = resource;
	}
	
	public String getCode() {
		return code;
	}
	
	public String getMessage() {
		return message;
	}
	
	public String getResource() {
		return resource;
	}

}
