package exceptions;

public class InvalidTargetException extends GameActionException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidTargetException() {
	}

	public InvalidTargetException(String message) {
		super(message);
	}

}
