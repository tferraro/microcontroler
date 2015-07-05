package exceptions;

public class FinishedExecutionException extends RuntimeException {
	
	public FinishedExecutionException(String message) {
		super(message);
	}
}
