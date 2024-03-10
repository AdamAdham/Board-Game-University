package exceptions;

public class MovementException extends GameActionException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MovementException() {
	}

	public MovementException(String message) {
		super(message);
	}

}
