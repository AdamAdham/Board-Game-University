package exceptions;

public class NotEnoughActionsException extends GameActionException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NotEnoughActionsException() {
	}

	public NotEnoughActionsException(String message) {
		super(message);
	}

}
