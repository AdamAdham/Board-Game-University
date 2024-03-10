package exceptions;

public class NoAvailableResourcesException extends GameActionException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NoAvailableResourcesException() {
	}

	public NoAvailableResourcesException(String message) {
		super(message);
	}

}
