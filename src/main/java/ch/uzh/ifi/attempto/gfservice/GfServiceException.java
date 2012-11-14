package ch.uzh.ifi.attempto.gfservice;

public class GfServiceException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public GfServiceException(String message) {
		super(message);
	}

	public GfServiceException(Exception e) {
		super(e);
	}

}
